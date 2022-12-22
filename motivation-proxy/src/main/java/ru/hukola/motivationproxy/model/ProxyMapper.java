package ru.hukola.motivationproxy.model;

/**
 * @author Babin Nikolay
 */
public class ProxyMapper {
    public static ProxyServerDto toProxyServerDto(ProxyServer proxyServer) {
        return new ProxyServerDto(proxyServer.getAddress(), proxyServer.getPort());
    }
}
