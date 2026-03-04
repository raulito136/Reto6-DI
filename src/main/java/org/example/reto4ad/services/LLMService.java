package org.example.reto4ad.services;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions; // <--- ESTE ES EL IMPORT CLAVE
import org.springframework.stereotype.Service;

@Service
public class LLMService {
    private final OllamaChatModel chatModel;

    public LLMService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateHistory(String seed){
        var response = chatModel.call(
                new Prompt(
                        "Dime la historia del equipo " + seed + " de la NBA. Menos de 60 palabras.",
                        OllamaChatOptions.builder()
                                .withModel("llama3") // Usa 'withModel' en lugar de 'model'
                                .withTemperature(1.0)
                                .build()
                ));
        return response.getResult().getOutput().getContent(); // Usa 'getContent()' en las versiones más nuevas
    }
}