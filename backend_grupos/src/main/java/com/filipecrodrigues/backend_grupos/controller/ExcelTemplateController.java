package com.filipecrodrigues.backend_grupos.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/excel")
@CrossOrigin(origins = "http://localhost:4200")
public class ExcelTemplateController {

    @GetMapping("/template")
    public ResponseEntity<byte[]> baixarTemplate() throws IOException {

        ClassPathResource resource =
                new ClassPathResource("templates/template_comparacao_grupos.xlsx");

        byte[] bytes = resource.getInputStream().readAllBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=template_comparacao_grupos.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}
