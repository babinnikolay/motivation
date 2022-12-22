package ru.hukola.motivationhttpclient.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hukola.motivationhttpclient.service.HttpclientService;

/**
 * @author Babin Nikolay
 */
@RestController
@RequestMapping("/httpclient")
@AllArgsConstructor
@Log4j2
public class MainController {
    private final HttpclientService httpclientService;
    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam String uri) {
        log.info("Get request ulr={}", uri);
        return ResponseEntity.ok(httpclientService.get(uri));
    }
}
