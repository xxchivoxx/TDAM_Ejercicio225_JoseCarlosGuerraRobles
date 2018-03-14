package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

/**
 * Created by carlos on 10/03/18.
 */

public class Build {
    int idCliente;
    String nombreCliente = "";
    String direccionCliente = "";
    String celularCliente = "";
    String mailCliente = "";
    String descripcionObraCliente = "";
    String montoCliente = "";
    int estado;

    public Build() {}

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCelularCliente() {
        return celularCliente;
    }

    public void setCelularCliente(String celularCliente) {
        this.celularCliente = celularCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public String getDescripcionObraCliente() {
        return descripcionObraCliente;
    }

    public void setDescripcionObraCliente(String descripcionObraCliente) {
        this.descripcionObraCliente = descripcionObraCliente;
    }

    public String getMontoCliente() {
        return montoCliente;
    }

    public void setMontoCliente(String montoCliente) {
        this.montoCliente = montoCliente;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
