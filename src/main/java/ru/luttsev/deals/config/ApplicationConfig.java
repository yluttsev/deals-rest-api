package ru.luttsev.deals.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Основная конфигурация приложения
 *
 * @author Yuri Luttsev
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"ru.luttsev.deals.repository"})
public class ApplicationConfig {

    /**
     * Маппер сущностей и DTO
     *
     * @return объект маппера
     */
    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

}
