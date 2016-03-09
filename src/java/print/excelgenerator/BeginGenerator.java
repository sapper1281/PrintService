/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.excelgenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Создает отчет об общем числе напечатанных документов
 * В большинстве случаев достаточнео end Generator
 * @author VVolgina
 */
public class BeginGenerator extends ExcelGenerator {

    public BeginGenerator(Date begDate, Date endDate, String title) {
        super(begDate, endDate, title);
    }

    public void generateDocument(HSSFWorkbook document) {
        HSSFSheet sheet = document.getSheetAt(0);

        //вставляем название отчета
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(1);
        cell.setCellValue(this.getTitle());

        //вставляем сроки
       /* row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(this.getDateInfo());
        CellRangeAddress region = new CellRangeAddress(1, 1, 0, 2);
        sheet.addMergedRegion(region);*/
        
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("");
    }
    
    public static class Tester {

        /**
         * @param args
         * @throws ParseException
         * @throws IOException
         * @throws IllegalArgumentException
         */
        public static void main(String[] args) {
            try {
                String filePath = "C:\\workbooks\\";
                String fileName = "workbook2.xls";

                File file = new File(filePath);
                file.mkdirs();

                FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
                BeginGenerator gen = new BeginGenerator(new Date(), new Date(), fileName);
                gen.generateDocument().write(fileOut);
                fileOut.close();
            } catch (Exception ex) {
                //ex.printStackTrace();
            }

        }
    }
}
