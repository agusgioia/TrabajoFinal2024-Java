package com.PersonalizaTuViaje.PersonalizaTuViaje.LogicaDeNegocio;

import lombok.Data;

import java.util.Date;

@Data
public class Vuelo {
    private Date originLocationDate;
    private Date destinationLocationDate;
    private String cityName;
    private Date departureDate;
    private Date returnDate;
    private int adults;
}
