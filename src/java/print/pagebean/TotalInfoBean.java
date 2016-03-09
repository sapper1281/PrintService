/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.pagebean;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import print.excelgenerator.ExelTotalProcesor;
import print.repository.LoggerRepository;
import print.service.StatInfo;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class TotalInfoBean {

    @ManagedProperty(value = "#{dateIntervalBean.begDate}")
    private Date begDate;
    @ManagedProperty(value = "#{dateIntervalBean.endDate}")
    private Date endDate;
    private String output = "";
    private String output1 = "";

    public String action() {
        output = "Страниц: ";
        output1 = "Документов: ";
        loadResult();
        return null;
    }

    public String getOutput() {
        if (!output.equalsIgnoreCase("Страниц: ")) {
            return output;
        } else {
            loadResult();
            return output;
        }
    }

    public void setOutput(String output) {
        this.output = output;

    }

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

    public String getOutput1() {
        if (!output1.equalsIgnoreCase("Документов: ")) {
            return output1;
        } else {
            loadResult();
            return output1;
        }
    }

    public void setOutput1(String output1) {
        this.output1 = output1;
    }

    public TotalInfoBean() {
        output = "Страниц: ";
        output1 = "Документов: ";
    }

    private void loadResult() {
        if (endDate != null && begDate != null && begDate.before(endDate)) {
            LoggerRepository rep = new LoggerRepository();
            StatInfo inf;
            try {
                inf = rep.getAllStatInfo(begDate, endDate);
                output += inf.getPageCount();
                output1 += inf.getDocCount();
            } catch (Exception exc1) {
                output += 0;
                output1 += 0;
            }
        } else {
            output += 0;
            output1 += 0;
        }
    }
    
    public void preProcessXLS(Object document){
        ExelTotalProcesor proc=new ExelTotalProcesor();
        proc.preProcess(document, begDate, endDate, "Отчет по ВЦ");
    }
    
    public void postProcessXLS(Object document){
        ExelTotalProcesor proc=new ExelTotalProcesor();
        loadResult();
        proc.postProcess(document, begDate, endDate,  new Integer(output.substring(output.lastIndexOf(" ")+1)), new Integer(output1.substring(output1.lastIndexOf(" ")+1)));
    }
}
