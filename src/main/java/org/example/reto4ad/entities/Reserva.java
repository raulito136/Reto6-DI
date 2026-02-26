package org.example.reto4ad.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo para representar una reserva en la base de datos MongoDB.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reservas") // Esto le dice a Mongo que cree una colección
public class Reserva {

    @Id
    private String id;

    /** Identificador del hotel al que pertenece la reserva. */
    private String hotelId;

    /** Nombre completo del cliente que realiza la reserva. */
    private String nombreCliente;

    /** Fecha de inicio de la estancia (formato String). */
    private String fechaEntrada;

    /** Cantidad total de noches de la estancia. */
    private Integer noches;
}