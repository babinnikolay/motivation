package ru.hukola.motivationproxy.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hukola.motivationproxy.service.ProxyService;

/**
 * @author Babin Nikolay
 */
@RestController
@RequestMapping("/proxy")
@AllArgsConstructor
@Log4j2
public class MainController {
    private final ProxyService proxyService;

    @GetMapping("/next")
    public ResponseEntity<Object> next() {
        log.info("Access /next");
        return ResponseEntity.ok(proxyService.next());
    }

    @GetMapping("/renew")
    public ResponseEntity<Object> renew() {
        log.info("Access /renew");
        return ResponseEntity.ok(proxyService.renew());
    }
}
