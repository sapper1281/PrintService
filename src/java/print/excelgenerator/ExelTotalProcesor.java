/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.excelgenerator;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@NoneScoped
public class ExelTotalProcesor {

    public void postProcess(Object document, Date begDate, Date endDate, long totalCount, long totalPage) {
        EndGenerator gen=new EndGenerator(totalPage, totalCount, begDate, endDate);
        gen.generateDocument((HSSFWorkbook)document);
    }

    public void preProcess(Object document, Date begDate, Date endDate, String title) {
       BeginGenerator gen=new BeginGenerator(begDate, endDate, title);
       gen.generateDocument((HSSFWorkbook)document);

    }
}
