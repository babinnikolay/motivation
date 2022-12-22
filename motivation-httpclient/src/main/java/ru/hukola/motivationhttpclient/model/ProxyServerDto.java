package ru.hukola.motivationhttpclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Babin Nikolay
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProxyServerDto {

    private String address;
    private int port;
}
