package org.example.reto4ad.controllers;

import org.example.reto4ad.exceptions.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones para la aplicación web.
 * Ahora renderiza una vista HTML de error en lugar de devolver JSON.
 */
@ControllerAdvice
public class HotelControllerAdvice {

    /**
     * Maneja el caso cuando un recurso de tipo Hotel no es encontrado.
     */
    @ExceptionHandler(HotelNotFoundException.class)
    public String handleHotelNotFound(HotelNotFoundException e, Model model) {
        model.addAttribute("tituloError", "Hotel no encontrado");
        model.addAttribute("mensajeError", e.getMessage());
        return "error"; // Te redirigirá a templates/error.html
    }

    /**
     * Maneja errores de peticiones incorrectas (formatos, reservas, faltan datos).
     */
    @ExceptionHandler({
            InvalidParameterFormatException.class,
            InvalidBookingRequestException.class,
            MissingRequiredParameterException.class
    })
    public String handleBadRequests(RuntimeException e, Model model) {
        model.addAttribute("tituloError", "Error en la petición");
        model.addAttribute("mensajeError", e.getMessage());
        return "error";
    }

    /**
     * Maneja fallos en la conexión con el servidor de base de datos.
     */
    @ExceptionHandler(DatabaseConnectionException.class)
    public String handleDatabaseError(DatabaseConnectionException e, Model model) {
        model.addAttribute("tituloError", "Servicio no disponible");
        model.addAttribute("mensajeError", "Error al conectar con la base de datos. Inténtelo más tarde.");
        return "error";
    }

    /**
     * Captura cualquier otro error no contemplado.
     */
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception e, Model model) {
        model.addAttribute("tituloError", "Error inesperado");
        model.addAttribute("mensajeError", "Ha ocurrido un error en la aplicación.");
        return "error";
    }
}