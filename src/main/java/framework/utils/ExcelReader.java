package framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    private final Workbook workbook;
    private final FormulaEvaluator evaluator;
    private final DataFormatter formatter;

    public ExcelReader(String resourcePath) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (is == null) {
                throw new RuntimeException("Khong tim thay file Excel: " + resourcePath);
            }

            this.workbook = new XSSFWorkbook(is);
            this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            this.formatter = new DataFormatter();
        } catch (Exception e) {
            throw new RuntimeException("Khong the mo file Excel: " + resourcePath, e);
        }
    }

    public Sheet getSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Khong tim thay sheet: " + sheetName);
        }
        return sheet;
    }

    /**
     * Đọc cell an toàn, xử lý:
     * - null
     * - STRING
     * - NUMERIC
     * - BOOLEAN
     * - FORMULA
     */
    public String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();

        switch (cellType) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                return formatter.formatCellValue(cell).trim();

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();

            case FORMULA:
                return formatter.formatCellValue(cell, evaluator).trim();

            case BLANK:
                return "";

            default:
                return formatter.formatCellValue(cell).trim();
        }
    }

    public int getColumnIndex(Row headerRow, String columnName) {
        Iterator<Cell> cellIterator = headerRow.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String value = getCellValue(cell);
            if (value.equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }

        throw new RuntimeException("Khong tim thay cot: " + columnName);
    }

    public List<String[]> readSheet(String sheetName, String... requiredColumns) {
        Sheet sheet = getSheet(sheetName);
        List<String[]> data = new ArrayList<>();

        if (sheet.getPhysicalNumberOfRows() <= 1) {
            return data;
        }

        Row headerRow = sheet.getRow(0);
        int[] indexes = new int[requiredColumns.length];

        for (int i = 0; i < requiredColumns.length; i++) {
            indexes[i] = getColumnIndex(headerRow, requiredColumns[i]);
        }

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            String[] rowData = new String[requiredColumns.length];
            boolean allBlank = true;

            for (int i = 0; i < indexes.length; i++) {
                String value = getCellValue(row.getCell(indexes[i]));
                rowData[i] = value;

                if (!value.isBlank()) {
                    allBlank = false;
                }
            }

            if (!allBlank) {
                data.add(rowData);
            }
        }

        return data;
    }

    public void close() {
        try {
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Khong the dong workbook", e);
        }
    }
}