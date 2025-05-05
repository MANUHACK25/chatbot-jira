package com.lodgingApplication.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_tickets")
public class EstadoTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    @Column(name = "NOMBRE_ESTADO")
    private String nombreEstado;

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
