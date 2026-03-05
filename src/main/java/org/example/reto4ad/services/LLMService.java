package org.example.reto4ad.services;

import org.example.reto4ad.entities.Hotel;
import org.example.reto4ad.repository.LLMRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LLMService implements LLMRepository {
    private final OllamaChatModel chatModel;
    private final HotelService hotelService;

    public LLMService(OllamaChatModel chatModel, HotelService hotelService) {
        this.chatModel = chatModel;
        this.hotelService = hotelService;
    }

    @Override
    public String generarRespuesta(String seed) {
        // Obtenemos la lista (que ya vendrá de caché gracias al paso anterior)
        String infoHoteles = hotelService.findAll().stream()
                .map(h -> h.getNombre() + " en " + h.getUbicacion() + " (" + h.getPrecioPorNoche() + "€)")
                .collect(Collectors.joining(", "));

        String instrucciones = "Eres el asistente de 'CESUR Viajes'. Responde corto y humano. " +
                "Disponemos de estos hoteles: " + infoHoteles + ". " +
                "Usuario dice: " + seed;

        // Bajamos la temperatura a 0.7 para que no invente hoteles que no existen
        OllamaChatOptions options = OllamaChatOptions.builder()
                .model("llama3")
                .temperature(0.7)
                .build();

        var response = chatModel.call(new Prompt(new UserMessage(instrucciones), options));
        return response.getResult().getOutput().getText();
    }
}