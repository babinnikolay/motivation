package ru.hukola.motivationproxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Babin Nikolay
 */
@AllArgsConstructor
@Getter
public class ProxyServer {
    private String address;
    private int port;
    @Setter
    private boolean isWorking;
}
