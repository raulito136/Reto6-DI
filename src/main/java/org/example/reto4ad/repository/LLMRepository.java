package org.example.reto4ad.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LLMRepository {
    public String generarRespuesta(String seed);
}
