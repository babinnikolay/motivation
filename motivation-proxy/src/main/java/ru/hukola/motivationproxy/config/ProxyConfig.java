package ru.hukola.motivationproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hukola.motivationproxy.model.ProxyServer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Babin Nikolay
 */
@Configuration
public class ProxyConfig {
    @Bean
    LinkedBlockingQueue<ProxyServer> blockingQueue() {
        return new LinkedBlockingQueue<>();
    }
}
