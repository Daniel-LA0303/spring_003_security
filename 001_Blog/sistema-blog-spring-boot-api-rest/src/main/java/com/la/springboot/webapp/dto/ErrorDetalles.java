package com.la.springboot.webapp.dto;

import java.util.Date;

public class ErrorDetalles {

    private Date marcaTiempo;
    private String mensaje;
    private String detalles;

    public ErrorDetalles(Date marcaTiempo, String mensaje, String detalles) {
        super();
        this.marcaTiempo = marcaTiempo;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }

    public Date getMarcaTiempo() {
        return marcaTiempo;
    }

    public void setMarcaTiempo(Date marcaTiempo) {
        this.marcaTiempo = marcaTiempo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
