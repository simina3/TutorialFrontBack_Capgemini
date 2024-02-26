package com.ccsw.tutorial.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ccsw
 *
 * Config para poder hacer transformaciones entre objetos de manera sencilla.
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {

        return new ModelMapper();
    }
}
