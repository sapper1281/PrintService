/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.excelgenerator;

import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Создает отчет об общем числе напечатанных документов
 *
 * @author VVolgina
 */
public class EndGenerator extends ExcelGenerator {

    private long pageCount;
    private long totalCount;

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public EndGenerator(long pageCount, long totalCount, Date begDate, Date endDate) {
        super(begDate, endDate, "");
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    public void generateDocument(HSSFWorkbook document) {
        document.setSheetName(0, "Лист1");
       HSSFSheet sheet = document.getSheetAt(0);
       
        //добавляем строки о к-ве напечатанного
        HSSFRow row = sheet.createRow(sheet.getLastRowNum()+2);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Всего напечатано "+this.getDateInfo()+":");
        CellRangeAddress region = new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 2);
        sheet.addMergedRegion(region);

        row = sheet.createRow(sheet.getLastRowNum()+1);
        cell = row.createCell(1);
        cell.setCellValue("Страниц:");
        cell = row.createCell(2);
        cell.setCellValue(pageCount);

        row = sheet.createRow(sheet.getLastRowNum()+1);
        cell = row.createCell(1);
        cell.setCellValue("Документов:");
        cell = row.createCell(2);
        cell.setCellValue(totalCount);
        
        sheet.setColumnWidth(0, 16000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
    }
}
