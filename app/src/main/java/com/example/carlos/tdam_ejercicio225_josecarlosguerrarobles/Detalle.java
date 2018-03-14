package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

/**
 * Created by carlos on 13/03/18.
 */

public class Detalle {
    int idCliente;
    int idTrabajador;
    String fechaInicio;
    String fechaFin;
    String cantidad;
    String paga;

    public Detalle(){}

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPaga() {
        return paga;
    }

    public void setPaga(String paga) {
        this.paga = paga;
    }
}
