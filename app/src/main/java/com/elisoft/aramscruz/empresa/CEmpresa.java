package com.elisoft.aramscruz.empresa;

public class CEmpresa {
    private String id="";
    private String razon_social="";
    private String direccion="";
    private String telefono="";
    private String whatsapp="";
    private String direccion_logo_corporativo="";

    public CEmpresa()
    {
    }

    public CEmpresa( String id,
             String razon_social,
             String direccion,
             String telefono,
             String whatsapp,
             String direccion_logo_corporativo){

        this.setId(id);
        this.setRazon_social(razon_social);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setWhatsapp(whatsapp);
        this.setDireccion_logo_corporativo(direccion_logo_corporativo);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getDireccion_logo_corporativo() {
        return direccion_logo_corporativo;
    }

    public void setDireccion_logo_corporativo(String direccion_logo_corporativo) {
        this.direccion_logo_corporativo = direccion_logo_corporativo;
    }
}
