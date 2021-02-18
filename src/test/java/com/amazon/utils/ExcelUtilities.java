package com.amazon.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

// We can use this excel utilities class to read and write excel files (for xls, xlsx files).
// These are the reusable custom methods we can use in our test classes
public class ExcelUtilities {

    public Workbook workBook;
    public Sheet workSheet;
    private String path;

    /**
     * eger workbook icinde birden fazla sheet uzerinde calisilacaksa diye bu methdu ekledim.
     * ayni zamanda getter ve setter'lar ile ugrasmmamak icin public yaptim.
     * eger const icinde verdiginden farkli sheet uzerinde calisacaksan bu method ile once
     * calisacagin sheet'i set etmelisin.
     *
     * @param sheetName
     */
    public void setWorkSheet(String sheetName) {
        this.workSheet = workBook.getSheet(sheetName);
    }

    /**
     * const cagrildiginda dosya acilip erisime sunuluyor, tekrardan  acmaya gerek yok
     *
     * @param path
     * @param sheetName
     */
    public ExcelUtilities(String path, String sheetName) {//This Constructor is to open and access the excel file
        this.path = path;
        try {
            // Opening the Excel file
            FileInputStream fileInputStream = new FileInputStream(path);
            // accessing the workbook
            workBook = WorkbookFactory.create(fileInputStream);
            //getting the worksheet
            workSheet = workBook.getSheet(sheetName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * o anda aktif olan (set edilmis olan) sheet kolon sayisini doner. Ilk satirdan hesaplama
     * yapar.
     *
     * @return
     */
    //===============Getting the number of columns in a specific single row=================
    public int columnCount() {
        //getting how many numbers in row 1
        return workSheet.getRow(0).getLastCellNum();
    }

    //===============how do you get the last row number?Index start at 0.====================
    public int rowCount() {
        return workSheet.getLastRowNum() + 1;
    }//adding 1 to get the actual count


    public Map<String, String> getLastRow(){
        List<String> columns = getColumnsNames();
        int lastRow = rowCount();
        Row row = workSheet.getRow(lastRow - 1);
        // creating map of the row using the column and value
        // key=column, value=cell
        Map<String, String> rowMap = new HashMap<String, String>();
        for (Cell cell : row) {
            int columnIndex = cell.getColumnIndex();
            rowMap.put(columns.get(columnIndex), cell.toString());
        }
        return rowMap;
    }

    /**
     *
     * @param rowIndex
     * @return verilen satirdaki biligyi verir. Header dahil olara 0-based say.
     */
    public Map<String, String> getSpecifiedRow(int rowIndex){
        List<String> columns = getColumnsNames();
        Row row = workSheet.getRow(rowIndex);
        // creating map of the row using the column and value
        // key=column, value=cell
        Map<String, String> rowMap = new HashMap<String, String>();
        for (Cell cell : row) {
            int columnIndex = cell.getColumnIndex();
            rowMap.put(columns.get(columnIndex), cell.toString());
        }
        return rowMap;
    }




    //==============When you enter row and column number, then you get the data==========
    public String getCellData(int rowNum, int colNum) {
        Cell cell;
        try {
            cell = workSheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.toString();
            return cellData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //============getting all data into two dimentional array and returning the data===
    // okudugumuz tüm verileri iki boyutlu diziye alma ve verileri döndürme
    public String[][] getDataArray() {
        String[][] data = new String[rowCount()][columnCount()];
        for (int i = 0; i < rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i][j] = value;
            }
        }
        return data;
    }

    //This will get the list of the data in the excel file
    //This is a list of map. This takes the data as string and will return the data as a Map of String
    public List<Map<String, String>> getDataList() {
        // getting all columns
        List<String> columns = getColumnsNames();
        // method will return this
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Row row = workSheet.getRow(i);
            // creating map of the row using the column and value
            // key=column, value=cell
            Map<String, String> rowMap = new HashMap<String, String>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                rowMap.put(columns.get(columnIndex), cell.toString());
            }
            data.add(rowMap);
        }
        return data;
    }

    public List<Map<String, String>> getDataListForSpecifiedColumns(List<String> wantedColumnList) {
        // getting all columns
        List<String> columns = getColumnsNames();
        // method will return this
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Row row = workSheet.getRow(i);
            // creating map of the row using the column and value
            // key=column, value=cell
            Map<String, String> rowMap = new HashMap<String, String>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                if (wantedColumnList.contains(columns.get(columnIndex))){
                    rowMap.put(columns.get(columnIndex), cell.toString());
                }
            }
            data.add(rowMap);
        }
        return data;
    }


    /**
     * check whether there exist any row except for header
     * @return
     */
    public boolean doesExistAnyRowExceptForHeader(){
        if (rowCount() == 1){
            System.out.println(rowCount());
            return false;
        }
        System.out.println(rowCount());
        return true;
    }



    //==============going to the first row and reading each row one by one==================//
    public List<String> getColumnsNames() {
        List<String> columns = new ArrayList<>();
        for (Cell cell : workSheet.getRow(0)) {
            columns.add(cell.toString());
        }
        return columns;
    }

    //=========When you enter the row and column number, returning the value===============//
    public void setCellData(String value, int rowNum, int colNum) {
        Cell cell;
        Row row;
        try {
            row = workSheet.getRow(rowNum);
            cell = row.getCell(colNum);
            if (cell == null) {//if there is no value, create a cell.
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCellData(String value, String columnName, int row) {
        int column = getColumnsNames().indexOf(columnName);
        setCellData(value, row, column);
    }



    public void removeLastRow() {
        int lastRowNumIndex = rowCount() - 1;
        if (lastRowNumIndex != 0) {
            Row row = workSheet.getRow(lastRowNumIndex);
            workSheet.removeRow(row);
        } else {
            System.out.println("just headers exist!!");
        }

    }

    public void removeLastRowAndSave(){
        removeLastRow();
        saveWorkBook();
    }

    /**
     * dosya uzerinde okuma manipulasyn yaptiktan sonra kaydetmen lazim. Aksi halde degisiklik
     * geri alinir.
     */
    public void saveWorkBook() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workBook.write(fileOutputStream);
            fileOutputStream.close();

            System.out.println(path + " i[enter link description here][1]s successfully written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void editInformationInRow(String referenceColumnName, String referenceValue, String editColumnName, String newValue){
        int rowIndex = -1;
        List <Map<String, String>> listOfRow = getDataList();
        for (int i = 0; i < listOfRow.size(); i++) {
             if(listOfRow.get(i).get(referenceColumnName).equals(referenceValue)){
                 rowIndex = i + 1;
                 break;
             }
        }
        setCellData(newValue,editColumnName,rowIndex);
    }

    /**
     *
     * @param columnName
     * @return header dahil etmeden verilen column ismine gore datayi doner.
     */
    public List<String> getGivenColumnValues(String columnName){
        List<String> columnValueList = new ArrayList<>();
        int colNum = getColumnsNames().indexOf(columnName);
        int rowNum = workSheet.getLastRowNum();
        for (int i = 0; i < rowNum; i++) {
             columnValueList.add(getCellData(i + 1,colNum));
        }
        return columnValueList;
    }

    /**
     *
     * @param columnName
     * @param value
     * @return sheet icindeki row index'i 0'dan basliyor. Buradan alinacak sayi sayfanin
     *          tamami icin gecerli olacak sekilde row index numarasidir. Yani header'i dahil
     *          eder sayarken. Header 0 index'ine karsilik gelir.
     */
    public int getRowIndexNumForGivenValue(String columnName ,String value){
        List<String> columnValueList = getGivenColumnValues(columnName);
        for (int i = 0; i < columnValueList.size(); i++) {
             if (columnValueList.get(i).equals(value)){
                 return i + 1;
             }
        }
        return -1;
    }

    /**
     *
     * @param columnValue
     * @return verilen kolon icindeki data sayisina gore random sayi ureterek o index'teki degeri doner
     */
    public String getRandomValueFromGivenColumn(String columnValue){
        List<String> valueList = getGivenColumnValues(columnValue);
        int max = valueList.size();
        Random random = new Random();
        int rndNum = random.nextInt(max);
        return valueList.get(rndNum);
    }

    /**
     *
     * @param profile
     * @return verilen profil tipindeki satirlar arasindan rastgele secim yaparak
     *         secilen satirin satir index numarasini doner. Dondugu numara icinde
     *         header satiri da vardir. 0-based
     */
    public int getRandomRowNumWithinSameProfile(String profile){
        List<String> allValues = getGivenColumnValues("profiles");
        List<Integer> wantedValuesIndexNumberList = new ArrayList<>();
        for (int i = 0; i < allValues.size(); i++) {
             if (allValues.get(i).equals(profile)){
                 wantedValuesIndexNumberList.add(i);
             }
        }
        int max = wantedValuesIndexNumberList.size();
        Random random = new Random();
        int rndNum = random.nextInt(max);
        return wantedValuesIndexNumberList.get(rndNum) + 1;
    }

}
