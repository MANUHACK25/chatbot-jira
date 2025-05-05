package com.lodgingApplication.domain.services;

import com.google.gson.Gson;
import com.lodgingApplication.domain.domainRepository.LodgingService;
import com.lodgingApplication.dto.TiccketDTO;
import com.lodgingApplication.model.Place;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatService implements LodgingService {

    private static final SystemMessage SYSTEM_MESSAGE = new SystemMessage(
            """
                    
                    You are a technical assistant specialized in solving Jira tickets.
                    You will receive the details of a past ticket that matches a current user's problem:
                       - Title
                       - Description
                       - Comment
                       - Label
                       - Solution
                       - userName
                       - userMail
                       
                    Your task is:
                    - Welcoming at a friendly way to the user at the beginning of every response.
                    - Reformulate the solution in a clear, user-friendly way.
                    - Present only that solution, explain it and summarize it if needed, but keeping all important steps.
                    - At the end of the message, indicate that a person who had this same issue is userName,
                      and they can be contacted via userMail for further help.
                    - wish to the User good luck after every Reponse.
                     
                                        
                    Output format:
                    - Text only
                    - Do not include nulls or any placeholder values.
                    - No additional commentary, headers, or formatting.
                    - No line breaks or special symbols.
                    
                    
                    Do not add any labels, tags, formatting symbols, or unnecessary commentary.
                    Keep the response concise and actionable.
                    """
    );


    private static final SystemMessage SYSTEM_MESSAGE_SHORT = new SystemMessage(
            """
            You are a helpful technical Jira assistant.
            Given a previously solved ticket, You will receive the details of a past ticket that matches a current user's problem:
                - Solution
                - userName
                - userMail
                       
            your task is:
            - Greet the user briefly.
            - Reformulate the given Solution in a clear and user-friendly tone.
            - Mention that another user (userName) had the same issue and can be contacted at userMail.
            - Wish the user good luck at the end.
        
            Output:
            - Plain text only
            - No labels, no code, no formatting symbols
            - No unnecessary commentary or explanations
            """
    );

    private final ChatClient aiClient;

    @Autowired
    private TicketService ticketService;

    @Autowired
    public ChatService(@Qualifier("ollamaChatModel") ChatModel chatModel) {
        this.aiClient = ChatClient.builder(chatModel).build();
    }

    public ChatService(ChatClient.Builder builder) {
        this.aiClient = builder.defaultAdvisors(new MessageChatMemoryAdvisor(
                new InMemoryChatMemory())).build();
    }

    public ChatResponse generateAnswer(String question) {
        return aiClient.prompt(question).call().chatResponse();
    }

    @Override
    public List<Place> searchPlaces(String prompt) {
        Prompt chatPrompt = new Prompt(List.of(SYSTEM_MESSAGE, new UserMessage(prompt)));
        ChatResponse response = aiClient.prompt(chatPrompt).call().chatResponse();
        assert response != null;
        String rawResponse = response.getResults().get(0).toString();
        System.out.println("Raw Response: " + rawResponse);
        String validResponseJson = extractJsonFromResponse(rawResponse);
        Place[] places = new Gson().fromJson(validResponseJson, Place[].class);
        return List.of(places);
    }

    public String sendResponse(String prompt){
        Prompt chatPrompt = new Prompt(List.of(SYSTEM_MESSAGE, new UserMessage(prompt)));
        ChatResponse respuesta = aiClient.prompt(chatPrompt).call().chatResponse();
        assert respuesta != null;
        String rohResponse = respuesta.getResults().get(0).toString();
        return rohResponse;
    }

    /**
     * TODO: cambiar este metodo, ya que getMostSimilarObjectTicket del ticketService ya no se usa
     * @Status: ya esta listo y refactorizado para su uso
     * despues de las pruiebas a nivel de TicketService entonces verificar el CHatService y el Prompt
     * de acuerdo a lo que se planifico
     * */
    public String sendResponseTicket(String prompt){
        //obtenemos el ticket que se encontro en el TicketService
        List<TiccketDTO> mostSimilarTicket = ticketService.getMOstSimilarObejectTicket2(prompt);
        if(mostSimilarTicket.isEmpty()){
            return "no se encontro un Ticket similar";
        }

        //Contexto dado por cada Ticket encontrado generado por TicketService
        StringBuilder context = new StringBuilder("Ticket Description: ");
        for (TiccketDTO t: mostSimilarTicket) {
            context.append("Solution: ").append(t.getSolucion()).append("\n");
            context.append("userName: ").append(t.getUserName()).append("\n");
            context.append("userMail: ").append(t.getUserMail()).append("\n");
        }
        context.append("\nbased on that similar Ticket, response to the user request:\n");
        //prompt aniadido del Usuario
        context.append(prompt);

        //Prompt default del sistema y prompt del Usuario
        Prompt chatPrompt = new Prompt(List.of(SYSTEM_MESSAGE_SHORT, new UserMessage(context.toString())));
        ChatResponse respuesta = aiClient.prompt(chatPrompt).call().chatResponse();
        assert respuesta != null;
        return respuesta.getResults().get(0).toString();
    }



    /*
    @Deprecated
    public List<Place> searchPlaces1(String prompt) {
        Prompt chatPrompt = new Prompt(List.of(SYSTEM_MESSAGE, new UserMessage(prompt)));
        ChatResponse response = aiClient.prompt(chatPrompt).call().chatResponse();
        assert response != null;
        String rawResponse = response.getResults().get(0).toString();

        System.out.println("Raw Response: " + rawResponse);
        String validResponseJson = extractJsonFromResponse(rawResponse);
        if(validResponseJson != null){
            try {
                Place[] places = new Gson().fromJson(validResponseJson, Place[].class);
                return List.of(places);
            } catch (JsonSyntaxException ex) {
                ex.printStackTrace();
                System.err.println("❌ Error al parsear JSON: " + ex.getMessage());
            }
        }else {
            System.out.println("no se encontro JSON valido en la respuesta!");
        }
        return List.of();
    }
    */


    public static String extractJsonFromResponse(String response) {
        Pattern pattern = Pattern.compile("\\[\\s*\\{.*?\\}\\s*\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }


}
