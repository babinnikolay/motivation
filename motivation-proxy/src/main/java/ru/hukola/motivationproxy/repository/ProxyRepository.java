package ru.hukola.motivationproxy.repository;

import ru.hukola.motivationproxy.model.ProxyServer;

import java.util.List;

/**
 * @author Babin Nikolay
 */
public interface ProxyRepository {
    List<ProxyServer> findAll();
}
