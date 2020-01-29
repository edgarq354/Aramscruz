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

        this.id=id;
        this.razon_social=razon_social;
        this.direccion=direccion;
        this.telefono=telefono;
        this.whatsapp=whatsapp;
        this.direccion_logo_corporativo=direccion_logo_corporativo;

    }
}
