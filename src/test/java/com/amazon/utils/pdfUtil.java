package com.amazon.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class pdfUtil {
    public  static void createPdfOneColumn(String filename, String title, int entry, String imagepath, List<String> list, String header) {

        int len = 1;

        String[] headers = new String[len];
        headers[0] = header;

        String[] rows = new String[entry];
        if(entry>list.size()){
            entry= list.size();
        }
        for (int i = 0; i < entry; i++) {
            rows[i]= list.get(i);
            }

        Document document = new Document(PageSize.LETTER.rotate());

        try {

            PdfWriter.getInstance(document,
                    new FileOutputStream(new File(filename)));
            document.open();
            Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20);
            document.add(Image.getInstance(imagepath));
            document.add(new Paragraph("                                               " + title.toUpperCase(), fontTitle));
            document.add(new Paragraph("          "));
            document.add(new LineSeparator());
            document.add(new Paragraph("          "));

            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fontRow = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

            PdfPTable table = new PdfPTable(headers.length);
            for (String head : headers) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(0.9f);
                cell.setPhrase(new Phrase("                                                  " +
                        "                                "+head.toUpperCase(), fontHeader));
                table.addCell(cell);
            }
            table.completeRow();

            for (String  row : rows) {

                    Phrase phrase = new Phrase(row, fontRow);
                    table.addCell(new PdfPCell(phrase));

                table.completeRow();
            }

            document.addTitle("PDF Table ");
            document.add(table);
            document.add(new Paragraph("          "));
            document.add(new LineSeparator());

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public  static void createPdf(String filename, String title, int entry, String imagepath, List<List<Object>> list, List<String> header) {

        int len = header.size();

        String[] headers = new String[len];
        for (int i = 0; i < len; i++) {
            headers[i] = header.get(i);
        }
        String[][] rows = new String[entry][len];
        if(entry>list.size()){
            entry= list.size();
        }
        for (int i = 0; i < entry; i++) {
            for (int j = 0; j <len ; j++) {
                rows[i][j] = list.get(i).get(j).toString();
            }
        }
        Document document = new Document(PageSize.LETTER.rotate());

        try {

            PdfWriter.getInstance(document,
                    new FileOutputStream(new File(filename)));
            document.open();
            Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20);
            document.add(Image.getInstance(imagepath));
            document.add(new Paragraph("                                               " + title.toUpperCase(), fontTitle));
            document.add(new Paragraph("          "));
            document.add(new LineSeparator());
            document.add(new Paragraph("          "));

            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fontRow = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

            PdfPTable table = new PdfPTable(headers.length);
            for (String head : headers) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(0.9f);
                cell.setPhrase(new Phrase(head.toUpperCase(), fontHeader));
                table.addCell(cell);
            }
            table.completeRow();

            for (String[] row : rows) {
                for (String data : row) {
                    Phrase phrase = new Phrase(data, fontRow);
                    table.addCell(new PdfPCell(phrase));
                }
                table.completeRow();
            }

            document.addTitle("PDF Table ");
            document.add(table);
            document.add(new Paragraph("          "));
            document.add(new LineSeparator());

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
