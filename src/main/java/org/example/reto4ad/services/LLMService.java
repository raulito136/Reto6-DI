package org.example.reto4ad.services;

import org.example.reto4ad.repository.LLMRepository;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

@Service
public class LLMService implements LLMRepository {
    private final OllamaChatModel chatModel;

    public LLMService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String generarRespuesta(String seed) {
        var response = chatModel.call(
                new Prompt(
                        "Actua como si fueras de atencion al cliente de una app de hoteles. el usuario ha dicho: " + seed,
                        OllamaChatOptions.builder()
                                .model("llama3")
                                .temperature(1.0)
                                .build()
                ));
        return response.getResult().getOutput().getText();
    }
}