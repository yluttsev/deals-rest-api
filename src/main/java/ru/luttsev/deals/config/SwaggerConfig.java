package ru.luttsev.deals.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API lля работы со сделками",
                contact = @Contact(
                        name = "Yuri Luttsev",
                        email = "floomyz@vk.com"
                )
        )
)
public class SwaggerConfig {
}
