# Reto 6 - Desarrollo de Interfaces (DI) - Aplicación Web de Gestión Hotelera

Este proyecto es una **Aplicación Web** desarrollada con **Spring Boot** que integra **MongoDB** para la persistencia de datos y **Spring AI (Ollama)** para proporcionar capacidades de Inteligencia Artificial (LLM). Está diseñada para gestionar información sobre hoteles, permitir interacciones mediante un chat inteligente y ofrecer una interfaz de usuario completa utilizando Thymeleaf.

## 🚀 Tecnologías Utilizadas

*   **Java 17**
*   **Spring Boot** (Web, Security, Data MongoDB)
*   **Spring AI** (Integración con Ollama)
*   **Thymeleaf** (Motor de plantillas para la interfaz web, con integración de seguridad)
*   **SpringDoc OpenAPI** (Documentación Swagger)
*   **Lombok** (Reducción de código repetitivo)
*   **Docker & Docker Compose** (Despliegue de contenedores)

## 🌐 Características de la Web

La aplicación cuenta con varias vistas desarrolladas en HTML y Thymeleaf:
*   **Login**: Autenticación de usuarios.
*   **Listado de Hoteles**: Vista general de los hoteles disponibles.
*   **Detalle de Hotel**: Información detallada de un hotel específico.
*   **Formulario de Hotel**: Creación y edición de hoteles.
*   **Chat Inteligente**: Interfaz para interactuar con el asistente de IA.

## 📋 Requisitos Previos

Para ejecutar este proyecto localmente o construirlo, necesitarás:

*   JDK 17
*   Maven
*   Docker Desktop (para ejecutar Ollama y la aplicación en contenedores)

## 🛠️ Configuración y Ejecución

### 🐳 Ejecución con Docker Compose (Recomendado)

El proyecto incluye un archivo `docker-compose.yml` que orquesta la aplicación y el servicio de Ollama.

1.  Asegúrate de tener Docker corriendo.
2.  En la raíz del proyecto, ejecuta:

    ```bash
    docker-compose up --build
    ```

Esto levantará los siguientes servicios:
*   **ollama**: Servicio de IA (LLM) en el puerto `11434`.
*   **app**: La aplicación web Spring Boot ("reto6di") accesible en `http://localhost:8080`.

> **Nota:** La aplicación está configurada para conectarse a un cluster de MongoDB Atlas definido en las variables de entorno del `docker-compose.yml`.

### ⚙️ Variables de Entorno

Las principales variables configuradas en `docker-compose.yml` son:

*   `SPRING_DATA_MONGODB_URI`: Cadena de conexión a MongoDB Atlas.
*   `SPRING_DATA_MONGODB_DATABASE`: Nombre de la base de datos (`hoteles`).
*   `SPRING_AI_OLLAMA_BASE_URL`: URL de conexión con el servicio Ollama (`http://ollama:11434`).

## 📚 Documentación de la API (Swagger)

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva de la API en:

*   http://localhost:8080/swagger-ui/index.html

## 🗂️ Estructura del Proyecto

### Entidades
*   **Hotel**: Mapeada a la colección `hoteles_espana2`. Contiene información como nombre, ubicación, precio, estrellas, latitud y longitud.

### Controladores Principales
*   **ChatController**: Maneja las interacciones con el LLM (`/chat/preguntar`).
*   **HotelController**: Gestiona las operaciones CRUD y vistas de los hoteles.
*   **LoginController**: Administra el acceso a la aplicación.

### Vistas (Templates)
*   `login.html`
*   `lista-hoteles.html`
*   `detalle-hotel.html`
*   `formulario-hotel.html`
*   `error.html`

### Archivos de Datos
El proyecto incluye archivos JSON en la raíz que pueden servir para poblar la base de datos o como referencia:
*   `BDHoteles.json`
*   `BDUsuarios.json`

## 📦 Compilación Manual

Si deseas compilar el proyecto manualmente sin Docker:

```bash
./mvnw clean package
```

Luego puedes ejecutar el JAR generado en la carpeta `target`.
