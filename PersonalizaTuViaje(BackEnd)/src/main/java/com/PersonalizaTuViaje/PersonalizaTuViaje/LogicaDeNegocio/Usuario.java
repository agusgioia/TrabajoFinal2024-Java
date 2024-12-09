package com.PersonalizaTuViaje.PersonalizaTuViaje.LogicaDeNegocio;

import lombok.Data;

import java.util.List;

@Data
public class Usuario {
    private String id;
    private String email;
    private String nombreUsuario;
    private String genero;
    private int edad;
    private float presupuesto;
    private List<Viaje> listaViajes;
}
