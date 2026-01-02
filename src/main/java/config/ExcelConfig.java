package config;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelConfig {
    private Workbook workbook;
    private String filePath;

    String excelFile = ExcelUtils.readExeclPath() + "Demo.xlsx";

    public ExcelConfig(String filePath) {
        this.filePath = filePath;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load Excel file");
        }
    }
    public Sheet createSheet(String sheetName){
        if(workbook.getSheet(sheetName)==null) workbook.createSheet(sheetName);
        return workbook.getSheet(sheetName);
    }

    public Sheet getSheet(String sheetName) {
        if (workbook.getSheet(sheetName) == null ) {
            System.out.println("Sheet not found: " + sheetName);
            return null;
        }
        return workbook.getSheet(sheetName);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save Excel file");
        }
    }

    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
