/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.pagebean;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class MainPageBean {

   private Date begDate;
    private Date endDate;
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

    public String getStrBegin() {
        return begDate==null?"":format.format(begDate);
    }

    public String getStrEnd() {
        return endDate==null?"":format.format(endDate);
    }
    
    public String department() {
        String str="departmentinfo.xhtml?beg="+getStrBegin();
        return str;

    }

    public MainPageBean() {
        
    }
    
    
    
}
