package utils;

import config.ConfigReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;
    private static String excelFilePath;
    private static FileInputStream fis;

    private static final DataFormatter formatter = new DataFormatter();


    public static String readExeclPath(){
        return System.getProperty("user.dir") + File.separator + ConfigReader.getProperty("excel.path") + File.separator;
    }


    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return formatter.formatCellValue(cell);
    }




    // Open Excel file
    public static void openExcel(String filePath) throws IOException {
        excelFilePath = filePath;
        fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
    }

    public static String getCellData(String sheetName, String columnName, int rowNumber) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            System.out.println("Sheet not found: " + sheetName);
            return null;
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            System.out.println("Header row is missing in sheet: " + sheetName);
            return null;
        }

        int colIndex = -1;
        // Find the column index by name
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                colIndex = cell.getColumnIndex();
                break;
            }
        }
        if (colIndex == -1) {return null;}

        Row row = sheet.getRow(rowNumber);
        if (row == null) {return null;}

        Cell cell = row.getCell(colIndex);
        if (cell == null) {return null;}

        return getCellValue(cell); // Assuming you already have this method
    }

    public static String getDataBySheetAndColumn(String sheetName, int rowNumber,String columnName) {
        Sheet sheet = workbook.getSheet(sheetName);
        int colIndex = getColumnIndex(sheetName, columnName);
        Row row = sheet.getRow(rowNumber);
        return getCellValue(row.getCell(colIndex));
    }
    // Read multiple data from a column in a sheet
    public static List<String> getColumnData(String sheetName, String columnName) {
        List<String> dataList = new ArrayList<>();
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            System.out.println("Sheet not found: " + sheetName);
            return dataList;
        }
        Row headerRow = sheet.getRow(0);
        int colIndex = -1;
        // Find the column index by name
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                colIndex = cell.getColumnIndex();
                break;
            }
        }
        if (colIndex == -1) {
            System.out.println("Column not found: " + columnName);
            return dataList;
        }
        // Read data from all rows in that column
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {  // skip header row
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    dataList.add(getCellValue(cell));
                }
            }
        }
        return dataList;
    }

    // Read all rows from a sheet as List of Maps (columnName -> value)
    public static List<Map<String, String>> getMultipleColumnData(String sheetName) {
        List<Map<String, String>> sheetData = new ArrayList<>();
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) return sheetData;

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return sheetData;

        // Iterate over all rows (skip header)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Map<String, String> rowData = new HashMap<>();
            for (Cell headerCell : headerRow) {
                int colIndex = headerCell.getColumnIndex();
                Cell cell = row.getCell(colIndex);
                rowData.put(headerCell.getStringCellValue(), (cell == null) ? "" : getCellValue(cell));
            }
            sheetData.add(rowData);
        }

        return sheetData;
    }

    // Write data to a specific cell
    public static void writeData(String sheetName, int rowNum, int colNum, String value) throws IOException {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) sheet = workbook.createSheet(sheetName);

        Row row = sheet.getRow(rowNum);
        if (row == null) row = sheet.createRow(rowNum);

        Cell cell = row.getCell(colNum);
        if (cell == null) cell = row.createCell(colNum);

        cell.setCellValue(value);

        FileOutputStream fos = new FileOutputStream(excelFilePath);
        workbook.write(fos);
        fos.close();
    }

    //get column index to update in result sheet
    public static int getColumnIndex(String sheetName, String columnName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) return -1;

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return -1;

        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1; // Column not found

    }
    //reading all data from excel row by row and column by column
    public static void getData(){
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            // Skip null or empty row
            if (row == null || isRowEmpty(row)) {continue;}
            for (Cell cell : row) {
                // Skip null or empty cell
                if (cell == null || cell.getCellType() == CellType.BLANK) {continue;}
                String value = getCellValue(cell);
                // Skip empty values after trim
                if (value == null || value.trim().isEmpty()) {continue;}
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    // Check if entire row is empty
    private static boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                if (!getCellValue(cell).trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void writeIntoExcel(String sheetName, int rowNum, String columnName, String value) throws IOException {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            // Create sheet if it doesn't exist
            sheet = workbook.createSheet(sheetName);
            System.out.println("Sheet not found: " + sheetName + " | Created new sheet.");
        }
        // Get or create header row
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {headerRow = sheet.createRow(0);}

        // Find column index
        int colIndex = getColumnIndex(sheetName,columnName);

        // If column doesn't exist, create it
        if (colIndex == -1) {
            colIndex = headerRow.getLastCellNum(); // next empty column
            if (colIndex < 0) colIndex = 0; // handle empty header row
            Cell newHeaderCell = headerRow.createCell(colIndex);
            newHeaderCell.setCellValue(columnName);
            System.out.println("Column not found: " + columnName + " | Created new column at index " + colIndex);
        }

        // Get or create the row for data
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        // Write the value in the cell
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        cell.setCellValue(value);

        // Save changes to the Excel file
        fileOutPut();
    }

    // write under specific column name
    public static void writeResult(String sheetName, int rowNum, String columnName, String value) throws IOException {
        int colIndex = getColumnIndex(sheetName, columnName);
        sheet = workbook.getSheet(sheetName);
        if (colIndex == -1) {

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }
            int newColumnIndex = headerRow.getLastCellNum(); // Next empty column
            Cell newHeaderCell = headerRow.createCell(newColumnIndex);
            newHeaderCell.setCellValue(columnName);
            System.out.println("Column not found: " + columnName+" now its created");
        }

        // Add some values in the new column
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            Cell cell = row.createCell(rowNum);
            cell.setCellValue(value);
        }
        fileOutPut();
    }

    public int getRowCount(String sheetName) {
        return workbook.getSheet(sheetName).getLastRowNum();
    }
    public int getColumnCount(String sheetName,int i) {
        return workbook.getSheet(sheetName).getRow(i).getLastCellNum();
    }

    public static void fileOutPut() throws IOException {
        FileOutputStream fos = new FileOutputStream(excelFilePath);
        workbook.write(fos);
        fos.close();
    }
    // Close Excel file
    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
