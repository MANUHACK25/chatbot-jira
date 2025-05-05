package com.lodgingApplication.dto;

import com.lodgingApplication.persistence.entity.Usuario;

public class TiccketDTO {
    private String titulo;
    private String descripcion;
    private String comentario;
    private String label;
    private String solucion;
    private String userName;
    private String userMail;
    @Deprecated

    public TiccketDTO(String titulo, String descripcion, String comentario, String label) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.comentario = comentario;
        this.label = label;

    }

    public TiccketDTO(String titulo, String descripcion, String comentario, String label, String solucion, String userName, String userMail) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.comentario = comentario;
        this.label = label;
        this.solucion = solucion;
        this.userName = userName;
        this.userMail = userMail;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }
}
