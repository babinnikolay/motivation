package ru.hukola.motivationproxy.repository.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hukola.motivationproxy.model.ProxyServer;
import ru.hukola.motivationproxy.repository.ProxyRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Babin Nikolay
 */
@Repository
public class FileProxyRepository implements ProxyRepository {
    private final List<ProxyServer> proxies = new ArrayList<>();

    public FileProxyRepository(@Value("${proxy.list}") String proxyPath) {
        try (BufferedReader bf = new BufferedReader(new FileReader(proxyPath))) {
            String ipAddr;
            while ((ipAddr = bf.readLine()) != null) {
                String[] split = ipAddr.split(":");
                proxies.add(new ProxyServer(split[0], Integer.parseInt(split[1]), false));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProxyServer> findAll() {
        return proxies;
    }
}
