/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.pagebean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import print.dto.Logger;
import print.excelgenerator.ExelTotalProcesor;
import print.repository.Account_Repository;
import print.repository.LoggerRepository;
import print.service.StatInfo;
import print.service.UserWithStatInfo;

/**
 *
 * @author Администратор
 */
@ManagedBean
@RequestScoped
public class UserDetailsBean {

    @ManagedProperty(value = "#{dateIntervalBean.begDate}")
    private Date begDate;
    @ManagedProperty(value = "#{dateIntervalBean.endDate}")
    private Date endDate;
    private long accId;
    private List<Logger> list;

    public String action() {
        list = null;
        loadResult();
        return "documentinfo?faces-redirect=true&idAcc="+accId;
    }

    public long getAccId() {
        return accId;
    }

    public void setAccId(long accId) {
        this.accId = accId;
        loadResult();
    }

    public List<Logger> getList() {
        loadResult();
        return list;
    }

    public void setList(List<Logger> list) {
        this.list = list;
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

    public UserDetailsBean() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("idAcc")) {
            String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAcc").toString();
            accId = new Long(idStr);
        }
    }

    private void loadResult() {
        try {
            if (begDate != null && endDate != null && begDate.before(endDate) && accId != 0 && (list == null || list.isEmpty())) {
                LoggerRepository rep = new LoggerRepository();
                list = rep.getLogsForAccount(accId, begDate, endDate);
            }
        } catch (Exception e) {
            list = null;
        }
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

    /**
     * Вставляет в документ заголовок и строки
     *
     * @param document документ
     */
    public void preProcessXLS(Object document) {
        ExelTotalProcesor proc = new ExelTotalProcesor();
        proc.preProcess(document, begDate, endDate, "Отчет по документам");
    }

    private StatInfo countSums() {
        StatInfo res = new StatInfo();
        for (Logger departmentWithStatInfo : list) {
            res.addToDocCount(1);
            res.addToPageCount(departmentWithStatInfo.getCount_Page());
        }
        return res;
    }
}
