package com.filipecrodrigues.backend_grupos.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.filipecrodrigues.backend_grupos.service.ComparacaoGrupoService;
import com.filipecrodrigues.backend_grupos.service.GerarResultadoExcelService;
import com.filipecrodrigues.backend_grupos.service.LeituraExcelService;

@RestController
@RequestMapping("/excel")
public class ComparacaoGrupoController {

    private final LeituraExcelService leitura;
    private final ComparacaoGrupoService comparacao;
    private final GerarResultadoExcelService gerador;

    public ComparacaoGrupoController(
            LeituraExcelService leitura,
            ComparacaoGrupoService comparacao,
            GerarResultadoExcelService gerador) {
        this.leitura = leitura;
        this.comparacao = comparacao;
        this.gerador = gerador;
    }

    @PostMapping(
        value = "/comparar",
        consumes = "multipart/form-data"
    )
    public ResponseEntity<byte[]> comparar(
            @RequestParam("file") MultipartFile file) {

        var linhas = leitura.ler(file);
        var grupos = comparacao.comparar(linhas);

        var excel = gerador.gerar(null, grupos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=resultado_grupos.xlsx")
                .body(excel);
    }
}
