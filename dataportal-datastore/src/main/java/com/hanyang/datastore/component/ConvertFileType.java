package com.hanyang.datastore.component;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Iterator;

@Component
public class ConvertFileType {

    public InputStream convertXlsxToCsv(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataFormatter dataFormatter = new DataFormatter();

        try (Writer writer = new OutputStreamWriter(outputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 선택

            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.iterator();
                StringBuilder csvLine = new StringBuilder();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    csvLine.append(cellValue).append(",");
                }

                // 마지막 쉼표 제거
                csvLine.deleteCharAt(csvLine.length() - 1);

                // CSV 파일에 한 줄 추가
                writer.write(csvLine + "\n");
            }
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
