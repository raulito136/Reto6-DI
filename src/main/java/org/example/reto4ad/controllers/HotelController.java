package org.example.reto4ad.controllers;

import org.example.reto4ad.entities.Hotel;
import org.example.reto4ad.entities.Reserva;
import org.example.reto4ad.exceptions.*;
import org.example.reto4ad.services.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador MVC para la gestión de hoteles y reservas mediante vistas HTML.
 */
@Controller
@RequestMapping("/hoteles")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // ==========================================
    // 1. GESTIÓN BÁSICA (Lectura)
    // ==========================================

    @GetMapping
    public String getAllHotels(Model model) {
        model.addAttribute("hoteles", hotelService.findAll());
        return "lista-hoteles"; // Reutilizamos esta vista para todos los listados
    }

    @GetMapping("/{id}")
    public String getHotelById(@PathVariable String id, Model model) {
        Hotel hotel = hotelService.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        model.addAttribute("hotel", hotel);
        return "detalle-hotel";
    }

    // ==========================================
    // 2. OPERACIONES CRUD (Crear, Editar, Borrar)
    // ==========================================

    // Muestra el formulario vacío para crear un hotel
    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "formulario-hotel";
    }

    // Recibe los datos del formulario y crea el hotel
    @PostMapping("/guardar")
    public String createHotel(@ModelAttribute Hotel hotel) {
        hotelService.save(hotel);
        return "redirect:/hoteles"; // Redirige a la lista tras guardar
    }

    // Muestra el formulario relleno para editar un hotel
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable String id, Model model) {
        Hotel hotel = hotelService.findById(id).orElseThrow(() -> new HotelNotFoundException(id));
        model.addAttribute("hotel", hotel);
        return "formulario-hotel";
    }

    // Borra el hotel (Usamos GET para facilitar el borrado desde un enlace HTML simple)
    @GetMapping("/eliminar/{id}")
    public String deleteHotel(@PathVariable String id) {
        hotelService.findById(id).orElseThrow(() -> new HotelNotFoundException(id));
        hotelService.deleteById(id);
        return "redirect:/hoteles";
    }

    // ==========================================
    // 3. FILTROS Y BÚSQUEDAS
    // Todas devuelven la vista "lista-hoteles" pero con los datos filtrados
    // ==========================================

    @GetMapping("/busqueda")
    public String getHotelsByLocation(@RequestParam(required = false) String ubicacion, Model model) {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new MissingRequiredParameterException("ubicacion");
        }
        List<Hotel> filtrados = hotelService.findAll().stream()
                .filter(h -> h.getUbicacion() != null &&
                        h.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("hoteles", filtrados);
        return "lista-hoteles";
    }

    @GetMapping("/calificacion/{calificacion}")
    public String getHotelsByCalificacion(@PathVariable Double calificacion, Model model) {
        model.addAttribute("hoteles", hotelService.findHotelesByCalificacion(calificacion));
        return "lista-hoteles";
    }

    @GetMapping("/precio/{precio}")
    public String getHotelsByPrecioNoche(@PathVariable Double precio, Model model) {
        model.addAttribute("hoteles", hotelService.findHotelesByPrecioNoche(precio));
        return "lista-hoteles";
    }

    @GetMapping("/precio/superior/{precio}")
    public String getHotelsbyPrecioNocheSuperiorA(@PathVariable Double precio, Model model) {
        model.addAttribute("hoteles", hotelService.findHotelesByPrecioNocheSuperiorA(precio));
        return "lista-hoteles";
    }

    @GetMapping("/precio/inferior/{precio}")
    public String getHotelsbyPrecioNocheInferiorA(@PathVariable Double precio, Model model) {
        model.addAttribute("hoteles", hotelService.findHotelesByPrecioNocheInferiorA(precio));
        return "lista-hoteles";
    }

    @GetMapping("/calificacion/superior/{calificacion}")
    public String getHotelsByCalificacionSuperiorA(@PathVariable Double calificacion, Model model) {
        model.addAttribute("hoteles", hotelService.findHotelesByCalificacionSuperiorA(calificacion));
        return "lista-hoteles";
    }

    @GetMapping("/calificacion/inferior/{calificacion}")
    public String getHotelsByCalificacionInferiorA(@PathVariable Double calificacion, Model model) {
        model.addAttribute("hoteles", hotelService.findHotelesByCalificacionInferiorA(calificacion));
        return "lista-hoteles";
    }

    @GetMapping("/nombre/{nombre}")
    public String getHotelsByNombre(@PathVariable String nombre, Model model) {
        Hotel hotel = hotelService.findHotelesByNombre(nombre).orElseThrow(() -> new HotelNotFoundException(nombre));
        model.addAttribute("hotel", hotel);
        return "detalle-hotel"; // Como es uno solo, mostramos la vista de detalle
    }

    // ==========================================
    // 4. RESERVAS
    // ==========================================

    @PostMapping("/reservas")
    public String createReserva(@ModelAttribute Reserva reserva) {
        if (reserva.getHotelId() == null) {
            throw new MissingRequiredParameterException("hotelId");
        }
        if (reserva.getNoches() == null || reserva.getNoches() <= 0) {
            throw new InvalidBookingRequestException("Numeros de noches deben ser mayores a 0");
        }
        hotelService.findById(reserva.getHotelId()).orElseThrow(() -> new HotelNotFoundException(reserva.getHotelId()));

        hotelService.saveReserva(reserva);

        // Redirige al detalle del hotel con un parámetro de éxito para mostrar un mensaje en el HTML
        return "redirect:/hoteles/" + reserva.getHotelId() + "?reservaExito=true";
    }

    @GetMapping("/filtrar")
    public String procesarFiltroFormulario(@RequestParam String tipo, @RequestParam String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "redirect:/hoteles";
        }

        // Redirige a tus métodos @GetMapping ya existentes dependiendo de la opción elegida
        switch (tipo) {
            case "ubicacion":
                return "redirect:/hoteles/busqueda?ubicacion=" + valor;
            case "nombre":
                return "redirect:/hoteles/nombre/" + valor;
            case "precio_superior":
                return "redirect:/hoteles/precio/superior/" + valor;
            case "precio_inferior":
                return "redirect:/hoteles/precio/inferior/" + valor;
            case "calificacion_superior":
                return "redirect:/hoteles/calificacion/superior/" + valor;
            case "calificacion_inferior":
                return "redirect:/hoteles/calificacion/inferior/" + valor;
            default:
                return "redirect:/hoteles";
        }
    }
}