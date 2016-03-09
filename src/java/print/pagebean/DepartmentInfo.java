/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.pagebean;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import print.excelgenerator.ExelTotalProcesor;
import print.repository.Department_Repository;
import print.repository.LoggerRepository;
import print.service.DepartmentWithStatInfo;
import print.service.StatInfo;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class DepartmentInfo {

    @ManagedProperty(value = "#{dateIntervalBean.begDate}")
    private Date begDate;
    @ManagedProperty(value = "#{dateIntervalBean.endDate}")
    private Date endDate;
    private List<DepartmentWithStatInfo> list;
    private List<DepartmentWithStatInfo> filteredBeanList;
    private String output = "";
    private String output1 = "";

    /**
     * Вызывается по нажатии кнопки ОК. Перезагружает список строк при изменении
     * даты
     *
     * @return null, так как переход на новую страницу не нужен
     */
    public String action() {
        output = "";
        output1 = "";
        list=null;
        loadResult();
       // String end = FacesContext.getCurrentInstance().g
        return "departmentinfo?faces-redirect=true";
    }
    
    public String getOutput() {
        if (!output.equalsIgnoreCase("")) {
            return output;
        } else {
            loadResult();
            return output;
        }
    }

    public void setOutput(String output) {
        this.output = output;

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

    
    /**
     * Возвращает отсортированный список строк таблицы
     *
     * @return отсортированный список строк таблицы
     */
    public List<DepartmentWithStatInfo> getFilteredBeanList() {
        return filteredBeanList;
    }

    /**
     * Назначает отсортированный список строк таблицы
     *
     * @param filteredBeanList отсортированный список строк таблицы
     */
    public void setFilteredBeanList(List<DepartmentWithStatInfo> filteredBeanList) {
        this.filteredBeanList = filteredBeanList;
    }

    /**
     * Возвращает список строк таблицы. Если он пуст, лезет в БД
     *
     * @return список строк таблицы
     */
    public List<DepartmentWithStatInfo> getList() {
        loadResult();
        return list;
    }

    /**
     * Назначает список строк таблицы
     *
     * @param list
     */
    public void setList(List<DepartmentWithStatInfo> list) {
        this.list = list;
    }

    /**
     * Возвращает начальную дату отчета
     *
     * @return начальную дату отчета
     */
    public Date getBegDate() {
        return begDate;
    }

    /**
     * Назначает начальную дату отчета
     *
     * @param begDate начальную дату отчета
     */
    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    /**
     * Возвращает конечную дату отчета
     *
     * @return конечную дату отчета
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Назначает конечную дату отчета
     *
     * @param endDate конечную дату отчета
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    

    public DepartmentInfo() {
        list=null;
        output = "";
        output1 = "";
    }

    private void loadResult() {
        if (endDate != null && begDate != null && begDate.before(endDate) && list == null) {
            Department_Repository rep = new Department_Repository();
            list = rep.getStatInfoByDepartment(begDate, endDate);
            LoggerRepository rep1 = new LoggerRepository();
            StatInfo inf;
            try {
                inf = rep1.getAllStatInfo(begDate, endDate);
                output += inf.getPageCount();
                output1 += inf.getDocCount();
            } catch (Exception exc1) {
                output += 0;
                output1 += 0;
            }
        }
    }

    /**
     * Вставляет в документ заголовок и строки
     *
     * @param document документ
     */
    public void preProcessXLS(Object document) {
        ExelTotalProcesor proc = new ExelTotalProcesor();
        proc.preProcess(document, begDate, endDate, "Отчет по отделам");
    }

    /**
     * Вставляет в документ общее к-во распечатанных страиц и документов
     *
     * @param document документ
     */
    public void postProcessXLS(Object document) {
        ExelTotalProcesor proc = new ExelTotalProcesor();
        loadResult();
        StatInfo stat = countSums();
        proc.postProcess(document, begDate, endDate, stat.getDocCount(), stat.getPageCount());
    }

    private StatInfo countSums() {
        StatInfo res = new StatInfo();
        for (DepartmentWithStatInfo departmentWithStatInfo : list) {
            res.addToDocCount(departmentWithStatInfo.getDocCount());
            res.addToPageCount(departmentWithStatInfo.getPageCount());
        }
        return res;
    }
}
