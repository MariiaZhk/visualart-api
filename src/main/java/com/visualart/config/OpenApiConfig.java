package com.visualart.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI visualArtOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VisualArt API 🎨")
                        .version("1.0.0")
                        .description("""
                                Welcome to the VisualArt API!  

                                This RESTful service helps you manage artworks, artists, and related metadata.  

                                **Available operations:**  
                                • Add, update, or delete artworks  
                                • Manage genres and media associated with artworks  
                                • Explore artists and their collections  

                                **Getting started:**  
                                To quickly test the API, upload a JSON file with artworks:  
                                `POST http://localhost:8080/api/artworks/upload`  

                                Once uploaded, you can browse and manipulate artworks and artists using the endpoints documented here.  

                                Use this interface to explore the API and interact with the system directly.  
                                Happy painting! 🖌️
                                """)
                );
    }
}
