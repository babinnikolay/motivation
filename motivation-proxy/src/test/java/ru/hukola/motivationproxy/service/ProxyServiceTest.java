package ru.hukola.motivationproxy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hukola.motivationproxy.model.ProxyServer;
import ru.hukola.motivationproxy.model.ProxyServerDto;
import ru.hukola.motivationproxy.repository.ProxyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Babin Nikolay
 */
@ExtendWith(MockitoExtension.class)
class ProxyServiceTest {

    private ProxyService proxyService;

    @Mock
    private ProxyRepository proxyRepositoryStub;

    @Mock
    private LinkedBlockingQueue<ProxyServer> linkedBlockingQueueStub;

    @Mock
    private ProxyServer proxyServerStub1;
    @Mock
    private ProxyServer proxyServerStub2;

    @Test
    void whenCreateProxyServiceThenRenewProxies() {
        List<ProxyServer> proxyList = new ArrayList<>();
        proxyServerStub1 = new ProxyServer("127.0.0.1", 8080, false);
        proxyServerStub2 = new ProxyServer("127.0.0.1", 65000, true);
        proxyList.add(proxyServerStub1);
        proxyList.add(proxyServerStub2);

        when(proxyRepositoryStub.findAll()).thenReturn(proxyList);
        when(linkedBlockingQueueStub.poll()).thenReturn(proxyServerStub2);

        proxyService = new ProxyService(proxyRepositoryStub, linkedBlockingQueueStub, "");

        assertEquals(65000, proxyService.next().getPort());
        verify(linkedBlockingQueueStub, times(1)).addAll(any());
        verify(linkedBlockingQueueStub, times(1)).size();
    }

    @Test
    void whenCallNextThenGetProxyServerDto() {
        proxyServerStub1 = new ProxyServer("addr", 9091, false);

        proxyService = new ProxyService(proxyRepositoryStub, linkedBlockingQueueStub, "");

        when(linkedBlockingQueueStub.poll()).thenReturn(proxyServerStub1);

        ProxyServerDto proxyServerDto = proxyService.next();
        assertEquals("addr", proxyServerDto.getAddress());
        assertEquals(9091, proxyServerDto.getPort());
    }
}