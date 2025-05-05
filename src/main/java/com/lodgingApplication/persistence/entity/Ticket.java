package com.lodgingApplication.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TICKETS")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TICKET")
    private Integer idTicket;

    @Column(name = "TITULO_TICKET")
    private String titleTicket;

    @Column(name = "DESCRIPCION_TICKET")
    private String descriptioTicket;

    @Column(name = "COMENTARIO_TICKET")
    private String commentaryTicket;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "SOLUCION")
    private String solucion;

    //Many to one por que varias tickets pertencen a un usuario (o puedne pertenecer a un usuario)
    //como en el anterior hacer el false insertable y updatable para que no permite registrar nuevos usuarios para esta relacion
    @ManyToOne(fetch = FetchType.EAGER) //Eager sirve para cargar el user junto al User
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private Usuario user;
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", insertable = false, updatable = false)
    private EstadoTicket estadoTicket;
    @Column(name = "EMBEDDING_NEW")
    private String vectorEmbedding;

    public Usuario getUser() {
        return user;
    }

    public EstadoTicket getEstadoTicket() {
        return estadoTicket;
    }

    public void setEstadoTicket(EstadoTicket estadoTicket) {
        this.estadoTicket = estadoTicket;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitleTicket() {
        return titleTicket;
    }

    public void setTitleTicket(String titleTicket) {
        this.titleTicket = titleTicket;
    }

    public String getDescriptioTicket() {
        return descriptioTicket;
    }

    public void setDescriptioTicket(String descriptioTicket) {
        this.descriptioTicket = descriptioTicket;
    }

    public String getCommentaryTicket() {
        return commentaryTicket;
    }

    public void setCommentaryTicket(String commentaryTicket) {
        this.commentaryTicket = commentaryTicket;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVectorEmbedding() {
        return vectorEmbedding;
    }

    public void setVectorEmbedding(String vectorEmbedding) {
        this.vectorEmbedding = vectorEmbedding;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }
}
