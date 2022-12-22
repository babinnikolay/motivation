package ru.hukola.motivationproxy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Babin Nikolay
 */
@AllArgsConstructor
@Getter
public class ProxyServerDto {
    private String address;
    private int port;
}
