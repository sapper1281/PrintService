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
import print.repository.Account_Repository;
import print.service.StatInfo;
import print.service.UserWithStatInfo;

/**
 *
 * @author Администратор
 */
@ManagedBean
@RequestScoped
public class UserInfoBean {

    @ManagedProperty(value = "#{dateIntervalBean.begDate}")
    private Date begDate;
    @ManagedProperty(value = "#{dateIntervalBean.endDate}")
    private Date endDate;
    private long idDep;
    private List<UserWithStatInfo> list;
    private List<UserWithStatInfo> filteredBeanList;

    public String action() {
        list = null;
        loadResult();
        return "userinfo?faces-redirect=true&id="+idDep;
    }

    public long getIdDep() {
        return idDep;
    }

    public void setIdDep(long idDep) {
            this.idDep = idDep;
            loadResult();
    }

    public List<UserWithStatInfo> getList() {
        loadResult();
        return list;
    }

    public void setList(List<UserWithStatInfo> list) {
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

    public List<UserWithStatInfo> getFilteredBeanList() {
        return filteredBeanList;
    }

    public void setFilteredBeanList(List<UserWithStatInfo> filteredBeanList) {
        this.filteredBeanList = filteredBeanList;
    }

    public UserInfoBean() {

        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id")) {
            String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString();
            idDep = new Long(idStr);
        }
        
        //form:j_idt12
        list = null;
        /* String beg = "-1";
         try {
         beg = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("beg").toString();
         String end = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("end").toString();
         String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString();
         idDep = new Long(idStr);
         endDate = format.parse(end);
         begDate = format.parse(beg);

         } catch (Exception exc) {
         try {

         endDate = ((MainPageBean) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("mainPageBean")).getEndDate();
         begDate = ((MainPageBean) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("mainPageBean")).getBegDate();
         beg = endDate == null || begDate == null ? "-1" : "1";
         idDep = endDate == null || begDate == null ? 0 : -100;
         } catch (Exception exc1) {
         beg = "-1";
         idDep = 0;
         }
         }
         if (!beg.equalsIgnoreCase("-1") && !beg.isEmpty() && idDep != 0) {
         Account_Repository rep = new Account_Repository();

         list = rep.getStatInfoByAccount(idDep, begDate, endDate);
         } else {
         list = new ArrayList<UserWithStatInfo>();
         }*/
    }

    private void loadResult() {
        try {
            if (endDate != null && begDate != null && begDate.before(endDate) && idDep!=0 && (list == null||list.isEmpty())) {
                Account_Repository rep = new Account_Repository();
                list = rep.getStatInfoByAccount(idDep, begDate, endDate);
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
        proc.preProcess(document, begDate, endDate, "Отчет по пользователям");
    }

    private StatInfo countSums() {
        StatInfo res = new StatInfo();
        for (UserWithStatInfo departmentWithStatInfo : list) {
            res.addToDocCount(departmentWithStatInfo.getDocCount());
            res.addToPageCount(departmentWithStatInfo.getPageCount());
        }
        return res;
    }
}
