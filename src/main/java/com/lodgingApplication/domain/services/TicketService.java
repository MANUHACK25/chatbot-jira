package com.lodgingApplication.domain.services;

import com.lodgingApplication.dto.TiccketDTO;
import com.lodgingApplication.persistence.TicketRepository;
import com.lodgingApplication.persistence.entity.Ticket;
import com.lodgingApplication.persistence.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    public List<Ticket> getAll(){
        return ticketRepository.getAllTickets();
    }
    public Optional<List<Ticket>> getTicketByTicketTitle(String titleTicket){
        return ticketRepository.findTicketByTitleTicket(titleTicket);
    }

    /**
     * TODO: usar este metodo al chat Service para indicar quien
     * es el usuario del  ticket encontrado y mostrar el nombre
     * */
    public List<Usuario> getUserTicketbyTicketId(Integer ticketId){
        return ticketRepository.getTicketUserbyTicketId(ticketId);
    }

    public List<Ticket> getMostSimilarTicket(String prompt){
        try {
            Integer similar = ticketRepository.buscarTicketMasSimilar(prompt);
            return ticketRepository.findTicketById(similar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * metodo para solucion de getMostSimilarObjectTicket, dado que ahora
     * es mas limpio y incluye a User
     *
     * */
    public List<TiccketDTO> getMOstSimilarObejectTicket2(String prompt){
        try {
            Integer similar = ticketRepository.buscarTicketMasSimilar(prompt);
            return ticketRepository.getTicketDTObyTicketId(similar);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * TODO: reemplazarlo por una llamada directa donde incluya el usuario
     *
     * devuelve el TIcket mas similar de acuerdo al prompt
     * usa embedding para encontrarlo y devuelve los datos
     * relevantes en un TicketDTO con los atributos relevantes como:
     * - Titulo
     * - Descripcion
     * - Comentario
     * - Label

    public List<TiccketDTO> getMostSimilarObjectTicket(String prompt){
        try {
            Integer similar = ticketRepository.buscarTicketMasSimilar(prompt);

            List<Object[]> rawResults = ticketRepository.findTicketDueTicketId(similar);

            List<TiccketDTO> results = rawResults.stream()
                    .map(obj -> new TiccketDTO(
                            (String)obj[0],
                            (String)obj[1],
                            (String)obj[2],
                            (String)obj[3],
                            (Usuario)obj[4]
                            )).collect(Collectors.toList());

            //return ticketRepository.findTicketDueTicketId(similar);
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     * */



}
