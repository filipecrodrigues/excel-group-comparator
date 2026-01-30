package com.filipecrodrigues.backend_grupos.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filipecrodrigues.backend_grupos.model.LinhaComparacao;

@Service
public class LeituraExcelService {

    public List<LinhaComparacao> ler(MultipartFile file) {

        List<LinhaComparacao> linhas = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                LinhaComparacao l = new LinhaComparacao();
                l.setUsuarioReferencia(get(row, 0));
                l.setGrupoReferencia(get(row, 1));
                l.setUsuarioComparado(get(row, 2));
                l.setGrupoComparado(get(row, 3));

                linhas.add(l);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler Excel", e);
        }

        return linhas;
    }

    private String get(Row row, int idx) {
        Cell cell = row.getCell(idx);
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim().toUpperCase();
    }
}
