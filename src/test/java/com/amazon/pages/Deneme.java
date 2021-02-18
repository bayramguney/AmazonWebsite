package com.amazon.pages;

import com.amazon.utils.ConfigsReader;
import com.amazon.utils.ExcelUtilities;

import java.util.Random;

public class Deneme {
    public static void main(String[] args) {
        ExcelUtilities excelUtilities=new ExcelUtilities("src/test/resources/testdata/product.xlsx","product");
        Random randomNumber=new Random();
        int rand=randomNumber.nextInt(excelUtilities.rowCount()-1)+1;
        String product=excelUtilities.getCellData(rand,0);
        System.out.println(product);
    }
}
