package com.hanyang.datastore.core.component;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Iterator;

@Component
public class ConvertFileType {

    public InputStream convertXlsxToCsv(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (Writer writer = new OutputStreamWriter(outputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 선택

            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.iterator();
                StringBuilder csvLine = new StringBuilder();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    // 셀 값이 문자열인 경우에 대비하여 String.valueOf() 사용
                    csvLine.append(cell).append(",");
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
