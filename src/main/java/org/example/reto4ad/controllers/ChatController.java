package org.example.reto4ad.controllers;

import org.example.reto4ad.services.LLMService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final LLMService llmService;

    public ChatController(LLMService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/preguntar")
    @ResponseBody
    public String preguntar(@RequestParam String mensaje) {
        return llmService.generarRespuesta(mensaje);
    }
}
