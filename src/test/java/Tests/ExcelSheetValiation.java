package Tests;

import config.ConfigReader;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.List;

public class ExcelSheetValiation {

    String excelFile = ExcelUtils.readExeclPath() + "Demo.xlsx";

    @Test
    public void clearData() throws IOException{
        String filePath = ExcelUtils.readExeclPath() + ConfigReader.getProperty("excel.file.bank");
        ExcelUtils.deleteAllData(filePath);
    }

    @Test
    public void excelVerify() throws IOException {

        ExcelUtils.openExcel(excelFile);
        List<String> tcs = ExcelUtils.getColumnData("Credentials", "testcase");
        for(int i=0;i<tcs.size();i++){

            System.out.println("TC No: " + tcs.get(i));
            int rowNum = i + 1; // because header is row 0
            System.out.println("rowNo: "+rowNum);
            // Read username and password from the same row
            String username = ExcelUtils.getCellData("Credentials", "username");
            String password = ExcelUtils.getCellData("Credentials", "password");

            System.out.println("Testcase: " + tcs.get(i) + " | Username: " + username + " | Password: " + password);

            boolean result =Boolean.parseBoolean(ConfigReader.getProperty("excel.condition"));
            String status = result ? "Pass" : "Fail";
            String remarks = result ? "Login successful" : "Login failed";
//        ExcelUtils.getColumnIndex("result","result");
            ExcelUtils.writeIntoExcel("result", rowNum, "testcase", tcs.get(i) );
            ExcelUtils.writeIntoExcel("result", rowNum, "Result", status);
            ExcelUtils.writeIntoExcel("result", rowNum, "Remarks", remarks);

//            ExcelUtils.getDataBySheetAndColumn("Credentials", "username")
        }

//        List<String> names = ExcelUtils.getColumnData("Credentials", "username");
//        for(String name:names){
//            System.out.println("Names from Excel: " + name);
//        }
//        List<String> password = ExcelUtils.getColumnData("Credentials", "password");
//        for(String pass:password){
//            System.out.println("Pass from Excel: " + pass);
//        }


        // Close Excel
        ExcelUtils.closeExcel();

    }
}
