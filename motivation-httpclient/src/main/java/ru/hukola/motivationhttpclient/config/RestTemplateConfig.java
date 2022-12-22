package ru.hukola.motivationhttpclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * @author Babin Nikolay
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }
}
