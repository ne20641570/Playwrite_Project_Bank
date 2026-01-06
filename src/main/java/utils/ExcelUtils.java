package utils;
import config.ConfigReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
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
    public static String getCellData(String sheetName, String columnName) {
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
        Random random=new Random();
        int rowNumber = random.nextInt(0,sheet.getLastRowNum());
        if( rowNumber==0) rowNumber=rowNumber+1;
        Row row = sheet.getRow(rowNumber);
        if (row == null) {return null;}

        Cell cell = row.getCell(colIndex);
        if (cell == null) {return null;}

        return getCellValue(cell); // Assuming you already have this method
    }
    public static String getCellDataAtRow(String sheetName, int rowNumber,String columnName) {
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
    //Read non empty Data
    public static String getRowData(String sheetName, String columnName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new RuntimeException("Header row is missing in sheet: " + sheetName);
        }

        int colIndex = -1;

        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName.trim())) {
                colIndex = cell.getColumnIndex();
                break;
            }
        }

        if (colIndex == -1) {
            throw new RuntimeException("Column not found: " + columnName);
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(colIndex);
                if (cell != null && !getCellValue(cell).isEmpty()) {
                    return getCellValue(cell);
                }
            }
        }

        return "";
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
    public static int getColumnIndex(String sheetName, String columnName) {

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }

        // Assuming header is always first row
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new IllegalStateException("Header row is missing in sheet: " + sheetName);
        }

        for (Cell cell : headerRow) {
            String cellValue = cell.getStringCellValue().trim();

            if (cellValue.equalsIgnoreCase(columnName.trim())) {
                return cell.getColumnIndex();
            }
        }

        throw new IllegalArgumentException(
                "Column '" + columnName + "' not found in sheet: " + sheetName);
    }
    // write under specific column name if you want to write this data in to excel call fileoutput method at the end this method
    public static void writeResult(String sheetName,  String columnName, String value) throws IOException {
        int colIndex = getColumnIndex(sheetName, columnName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        sheet = workbook.getSheet(sheetName);
        if (colIndex == -1) {

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                headerRow = sheet.createRow(0);
            }
            int newColumnIndex = headerRow.getLastCellNum(); // Next empty column
            if (newColumnIndex == -1) {
                newColumnIndex = 0; // sheet was empty
            }
            Cell newHeaderCell = headerRow.createCell(newColumnIndex);
            newHeaderCell.setCellValue(columnName);
            colIndex = newColumnIndex;
            System.out.println("Column not found: " + columnName+" now its created");
        }

        int nextRowNum = sheet.getLastRowNum() + 1;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(colIndex) == null) {
                nextRowNum = i;
                break;
            }
        }
// Get or create row
        Row row = sheet.getRow(nextRowNum);
        if (row == null) {
            row = sheet.createRow(nextRowNum);
        }

        // 5. Write value
        Cell cell = row.createCell(colIndex);
        cell.setCellValue(value);

    }
    public static void fileOutPut() throws IOException {
        FileOutputStream fos = new FileOutputStream(excelFilePath);
        workbook.write(fos);
        fos.close();
    }

    public int getRowCount(String sheetName) {
        return workbook.getSheet(sheetName).getLastRowNum();
    }
    public int getColumnCount(String sheetName,int i) {
        return workbook.getSheet(sheetName).getRow(i).getLastCellNum();
    }
    public static void deleteAllData(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);

        int sheetCount = workbook.getNumberOfSheets();
        for (int i = sheetCount - 1; i >= 0; i--) {
            workbook.removeSheetAt(i);
        }

        fis.close();

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    // Read multiple data from a column in a sheet
    public static List<String> getColumnData(String sheetName, String columnName) {
        List<String> dataList = new ArrayList<>();
        sheet = workbook.getSheet(sheetName);
        if (ExcelUtils.sheet == null) {
            System.out.println("Sheet not found: " + sheetName);
            return dataList;
        }
        Row headerRow = ExcelUtils.sheet.getRow(0);
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
    public static int getTotalRowCount(String sheetName){
       try{
           Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            for (int i = sheet.getLastRowNum(); i > 0; i--) {
                Row row = sheet.getRow(i);

                // Row exists AND has at least one cell
                if (row != null && row.getPhysicalNumberOfCells() > 0) {
                    return i; // zero-based index
                }
            }

            return -1; // only header or empty sheet

        } catch (Exception e) {
            throw new RuntimeException("Failed to get last data row", e);
        }
    }
    // Close Excel file
    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

}
