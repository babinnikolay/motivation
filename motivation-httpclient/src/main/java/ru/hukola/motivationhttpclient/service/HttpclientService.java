package ru.hukola.motivationhttpclient.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.hukola.motivationhttpclient.model.ProxyServerDto;

import java.net.InetSocketAddress;
import java.net.Proxy;


/**
 * @author Babin Nikolay
 */
@Service
@AllArgsConstructor
public class HttpclientService {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers = new HttpHeaders();

    {
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public String get(String uri) {

        ProxyServerDto proxyServerDto = getNextProxyServerDto();

        // https://static-basket-01.wb.ru/vol0/data/main-menu-ru-ru.json
        String server = "https://static-basket-01.wb.ru";
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServerDto.getAddress(), proxyServerDto.getPort()));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        RestTemplate proxiesRestTemplate = new RestTemplate(requestFactory);

        ResponseEntity<String> responseEntity = proxiesRestTemplate.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getBody();
    }

    private ProxyServerDto getNextProxyServerDto() {
        return restTemplate.getForObject("http://localhost:8080/proxy/next", ProxyServerDto.class);
    }
}
