package com.filipecrodrigues.backend_grupos.service;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class GerarResultadoExcelService {

    private static final String NOME_ABA = "Resultado";
    private static final String CABECALHO = "GRUPOS A ADICIONAR";

    public byte[] gerar(String usuarioComparado, Set<String> grupos) {

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet(NOME_ABA);

            // ===== estilos =====
            CellStyle headerStyle = criarEstiloCabecalho(wb);
            CellStyle dataStyle = criarEstiloDados(wb);

            int rowIndex = 0;

            // ===== cabeçalho =====
            Row header = sheet.createRow(rowIndex++);
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue(CABECALHO);
            headerCell.setCellStyle(headerStyle);

            // ===== dados =====
            for (String grupo : grupos) {
                Row row = sheet.createRow(rowIndex++);
                Cell cell = row.createCell(0);
                cell.setCellValue(grupo);
                cell.setCellStyle(dataStyle);
            }

            // ===== layout =====
            sheet.setColumnWidth(0, 24000);
            sheet.createFreezePane(0, 1); // congela cabeçalho

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar Excel", e);
        }
    }

    // =========================
    // ESTILOS
    // =========================

    private CellStyle criarEstiloCabecalho(Workbook wb) {
        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        aplicarBordas(style);
        return style;
    }

    private CellStyle criarEstiloDados(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        aplicarBordas(style);
        return style;
    }

    private void aplicarBordas(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}
