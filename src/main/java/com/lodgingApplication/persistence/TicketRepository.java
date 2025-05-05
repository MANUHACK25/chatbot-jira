package com.lodgingApplication.persistence;

import com.lodgingApplication.dto.TiccketDTO;
import com.lodgingApplication.persistence.crud.TicketCrudRepository;
import com.lodgingApplication.persistence.entity.Ticket;
import com.lodgingApplication.persistence.entity.Usuario;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.lodgingApplication.persistence.logic.embeddingLogic.*;

@Repository
public class TicketRepository {
    @Autowired
    private TicketCrudRepository ticketCrudRepository;
    public List<Ticket> getAllTickets(){
        List<Ticket> tickets = (List<Ticket>) ticketCrudRepository.findAll();
        return tickets;
    }

    public Optional<List<Ticket>> findTicketByTitleTicket(String titulo){
        return ticketCrudRepository.findByTitleTicketOrderByIdTicket(titulo);
    }


    public Map<Integer, float[]> findTicketsAndEmbeddings(){
        List<Ticket> results = getAllTickets();
        /**lo guardaremos en un Hashmap*/
        Map<Integer, float[]> embeddingsMap = new HashMap<>();
        /**Recorrer el Raw DATA */
        for (Ticket projection: results) {
            Integer ticketId = projection.getIdTicket();
            String embeddingStr = projection.getVectorEmbedding();

            /**convierte a Float osea a vector*/
            float[] embeddingArray = convertirStringAFloatArray(embeddingStr);
            embeddingsMap.put(ticketId, embeddingArray);
        }

        return embeddingsMap;
    }

    public Integer buscarTicketMasSimilar(String consulta) throws Exception {
        // Obtener los embeddings desde la DB
        Map<Integer, float[]> embeddingsDB = findTicketsAndEmbeddings();
        // Generar el embedding para la consulta
        float[] embeddingConsulta = generateEmbedingFloat(consulta);

        // Variable para el mejor resultado
        int mejorTicketId = -1;
        double mejorSimilitud = -1;

        // Comparar con todos los embeddings almacenados
        for (Map.Entry<Integer, float[]> entry : embeddingsDB.entrySet()) {
            int ticketId = entry.getKey();
            float[] embeddingDB = entry.getValue();

            // Calcular similitud coseno
            double similitud = cosineSimilarity(embeddingConsulta, embeddingDB);

            System.out.println("Similitud con Ticket ID " + ticketId + ": " + similitud);
            if (similitud > mejorSimilitud) {
                mejorSimilitud = similitud;
                mejorTicketId = ticketId;
            }
        }
        return mejorTicketId;
    }

    /**
     * llamada solo por JPA
     * encuentra solo el Ticket completo por ticketId
     * */
    public List<Ticket> findTicketById(Integer ticketId){
        return ticketCrudRepository.findByIdTicket(ticketId);
    }

    /**
     * devuelve el usuario dado el ticketId
     * se pasa de objeto Ticket a objeto Usuario
     * */
    public List<Usuario> getTicketUserbyTicketId(Integer ticketId){
        List<Ticket> tickets = ticketCrudRepository.findByIdTicket(ticketId);
        List<Usuario> userList = new ArrayList<>();
        for (Ticket ticket: tickets) {
             Usuario user = ticket.getUser();
             userList.add(user);
        }
        return userList;

    }


    /**
     * devuelve un DTO completo incluyendo al usuario dado un TicketId
     * que trae todo el objeto de Ticket, solo que ahora lo encapsulamos en un DTOTicket
     * */
    public List<TiccketDTO> getTicketDTObyTicketId(Integer ticketId){
        List<Ticket> tickets = ticketCrudRepository.findByIdTicket(ticketId);
        List<TiccketDTO> dtoList = new ArrayList<>();
        for (Ticket t: tickets) {
            TiccketDTO dtoObject = new TiccketDTO(t.getTitleTicket(), t.getDescriptioTicket(), t.getCommentaryTicket(),
                    t.getLabel(), t.getSolucion() ,t.getUser().getNOMBRE(), t.getUser().getEMAIL());

            dtoList.add(dtoObject);

        }
        return dtoList;

    }




    /**
     * usa una nativeQuery en este caso pero no llama a todos
     * los elementos de la clase Ticket
     * */
    public List<Object[]> findTicketDueTicketId(Integer ticketId){
        return ticketCrudRepository.findTicketDueId(ticketId);
    }












}
