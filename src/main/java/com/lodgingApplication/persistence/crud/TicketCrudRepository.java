package com.lodgingApplication.persistence.crud;

import com.lodgingApplication.persistence.entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface TicketCrudRepository extends CrudRepository<Ticket, Integer> {

    /**
     *find Ticket by titleTicket
     * */
    Optional<List<Ticket>>  findByTitleTicketOrderByIdTicket(String tituloTicket);

    @Query(value = "SELECT ID_TICKET, EMBEDDING_NEW FROM TICKETS", nativeQuery = true)
    List<Object[]> findEmbedding();

    List<Ticket> findByIdTicket(Integer ticketId);

    @Query(value = "SELECT TITULO_TICKET, TO_CHAR(DESCRIPCION_TICKET), TO_CHAR(COMENTARIO_TICKET), LABEL FROM TICKETS WHERE ID_TICKET = ?1", nativeQuery = true)
    List<Object[]> findTicketDueId(Integer ticketId);



    // @Query(value = "SELECT ID_TICKET AS idTicket, EMBEDDING_NEW AS embeddingNew FROM TICKETS", nativeQuery = true)
  //  List<TicketEmbeddingProjection> findIdTicketAndVectorEmbedding();




}
