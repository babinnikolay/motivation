package ru.hukola.motivationproxy.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hukola.motivationproxy.model.ProxyMapper;
import ru.hukola.motivationproxy.model.ProxyServer;
import ru.hukola.motivationproxy.model.ProxyServerDto;
import ru.hukola.motivationproxy.model.RenewStatusDto;
import ru.hukola.motivationproxy.repository.ProxyRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Babin Nikolay
 */
@Service
@Log4j2
public class ProxyService {
    private final BlockingQueue<ProxyServer> proxyQueue;
    private final ProxyRepository proxyRepository;

    private String inetAddr;

    @Autowired
    public ProxyService(ProxyRepository proxyRepository,
                        LinkedBlockingQueue<ProxyServer> linkedBlockingQueue,
                        @Value("${proxy.inet.addr}") String inetAddr) {
        this.proxyQueue = linkedBlockingQueue;
        this.proxyRepository = proxyRepository;
        this.inetAddr = inetAddr;
        String result = renewProxies();
        log.info("Proxies {}", result);

    }

    public ProxyServerDto next() {
        ProxyServer proxy = proxyQueue.poll();
        proxyQueue.offer(proxy);
        return ProxyMapper.toProxyServerDto(proxy);
    }

    public RenewStatusDto renew() {
        return new RenewStatusDto(renewProxies());
    }

    private String renewProxies() {
        List<ProxyServer> all = proxyRepository.findAll();
        setWorkingState(all);
        proxyQueue.addAll(all.stream().filter(ProxyServer::isWorking).toList());
        return String.format("Renewed. %d available", proxyQueue.size());
    }

    private void setWorkingState(List<ProxyServer> servers) {

        for (ProxyServer proxyServer : servers) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(proxyServer.getAddress(), proxyServer.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddress);
            URLConnection connection;
            URL url;
            try {
                url = new URL(inetAddr);
                connection = url.openConnection(proxy);
                connection.setConnectTimeout(100);
                log.info("Checking proxy {}:{}", proxyServer.getAddress(), proxyServer.getPort());
                connection.getContent();
                proxyServer.setWorking(true);
            } catch (IOException e) {
                log.info("Proxy is not working reason: {}", e.getMessage());
            }
        }
    }
}
