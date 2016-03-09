/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.excelgenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * Класс для создания Excel отчетов
 *
 * @author VVolgina
 */
public abstract class ExcelGenerator {

    private Date begDate;
    private Date endDate;
    private String title;
    private CellStyle thickStyle;
    private CellStyle thinStyle;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExcelGenerator(Date begDate, Date endDate, String title) {
        this.begDate = begDate;
        this.endDate = endDate;
        this.title = title;
    }

    /**
     * Генерирует строку о периоде, за который создан отчет
     *
     * @return строку о периоде, за который создан отчет
     */
    protected String getDateInfo() {
        StringBuilder res = new StringBuilder("");
        if (begDate != null) {
            res.append("c ").append(format.format(begDate)).append(" ");
        }
        if (endDate != null) {
            res.append("по ").append(format.format(endDate));
        }
        return res.toString();
    }

    /**
     * Создает объект для документа excel на основе данных класса.
     *
     * @return объект для документа excel
     */
    public HSSFWorkbook generateDocument() {
        HSSFWorkbook wb = new HSSFWorkbook();

        thickStyle = wb.createCellStyle();
        thickStyle.setBorderBottom(CellStyle.BORDER_THICK);
        thickStyle.setBorderLeft(CellStyle.BORDER_THICK);
        thickStyle.setBorderRight(CellStyle.BORDER_THICK);
        thickStyle.setBorderTop(CellStyle.BORDER_THICK);

        thinStyle = wb.createCellStyle();
        thinStyle.setBorderBottom(CellStyle.BORDER_THIN);
        thinStyle.setBorderLeft(CellStyle.BORDER_THIN);
        thinStyle.setBorderRight(CellStyle.BORDER_THIN);
        thinStyle.setBorderTop(CellStyle.BORDER_THIN);

        return wb;
    }

    /**
     * Возвращает стиль для ячейки с толстыми границами
     *
     * @return стиль для ячейки с толстыми границами
     */
    protected CellStyle getThickBorderStyle() {
        return thickStyle;
    }

    /**
     * Возвращает стиль для ячейки с тонкими границами
     *
     * @return стиль для ячейки с тонкими границами
     */
    protected CellStyle getThinStyle() {
        return thinStyle;
    }

    /**
     * Создает толстую рамку вокруг группы ячеек
     * @param region группа ячеек
     * @param sheet страница
     * @param workBook документ
     */
    protected void createThickBorderAroundRegion(CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook workBook) {
        RegionUtil.setBorderTop(CellStyle.BORDER_THICK, region, sheet, workBook);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THICK, region, sheet, workBook);
        RegionUtil.setBorderRight(CellStyle.BORDER_THICK, region, sheet, workBook);
        RegionUtil.setBorderBottom(CellStyle.BORDER_THICK, region, sheet, workBook);
    }
    
     /**
     * Создает толстую рамку вокруг группы ячеек
     * @param region группа ячеек
     * @param sheet страница
     * @param workBook документ
     */
    protected void createThinBorderAroundRegion(CellRangeAddress region, HSSFSheet sheet, HSSFWorkbook workBook) {
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, workBook);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, workBook);
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, workBook);
        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, workBook);
    }
}
