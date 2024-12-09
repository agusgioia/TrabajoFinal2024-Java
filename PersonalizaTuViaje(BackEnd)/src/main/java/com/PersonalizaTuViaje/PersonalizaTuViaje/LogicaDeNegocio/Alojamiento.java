package com.PersonalizaTuViaje.PersonalizaTuViaje.LogicaDeNegocio;

import lombok.Data;

import java.util.Date;

@Data
public class Alojamiento {
    private String cityName;
    private String hotelId;
    private Date checkInDate;
    private Date checkOutDate;
    private int roomQuantity;
    private int adults;
}
