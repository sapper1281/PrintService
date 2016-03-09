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
 *
 * @author VVolgina
 */
public class TotalInfoExelGenerator extends ExcelGenerator {

    private int pageCount;
    private int totalCount;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public TotalInfoExelGenerator(int pageCount, int totalCount, Date begDate, Date endDate, String title) {
        super(begDate, endDate, title);
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    @Override
    public HSSFWorkbook generateDocument() {
        //создаем документ
        HSSFWorkbook workBook = super.generateDocument();
        HSSFSheet sheet = workBook.createSheet("Лист 1");

        //вставляем название отчета
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(this.getTitle());
        CellRangeAddress region = new CellRangeAddress(0, 1, 0, 5);
        sheet.addMergedRegion(region);
      //  createThickBorderAroundRegion(region, sheet, workBook);

      /*  for (int i = 2; i < 4; i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < 6; j++) {
                cell = row.createCell(j);
                //cell.setCellValue("");
                cell.setCellStyle(getThinStyle());
            }
        }*/

        //вставляем сроки
        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue(this.getDateInfo());
        region = new CellRangeAddress(4, 4, 0, 2);
        sheet.addMergedRegion(region);

        //добавляем строки о к-ве напечатанного
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Всего напечатано:");
        region = new CellRangeAddress(6, 6, 0, 1);
        sheet.addMergedRegion(region);

        row = sheet.createRow(7);
        cell = row.createCell(1);
        cell.setCellValue("Страниц:");
        cell = row.createCell(3);
        cell.setCellValue(pageCount);
        region = new CellRangeAddress(7, 7, 1, 2);
        sheet.addMergedRegion(region);

        row = sheet.createRow(8);
        cell = row.createCell(1);
        cell.setCellValue("Документов:");
        cell = row.createCell(3);
        cell.setCellValue(totalCount);
        region = new CellRangeAddress(8, 8, 1, 2);
        sheet.addMergedRegion(region);

        return workBook;

    }

    /* @Override
     protected String getResultInfo() {
     StringBuilder res = new StringBuilder(pageCount).append(" страниц, ").append(totalCount).append(" документов.");
     return res.toString();
     }*/
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
                TotalInfoExelGenerator gen = new TotalInfoExelGenerator(3, 1, new Date(), new Date(), fileName);
                gen.generateDocument().write(fileOut);
                fileOut.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
