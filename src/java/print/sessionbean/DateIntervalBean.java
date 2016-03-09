/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.sessionbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@SessionScoped
public class DateIntervalBean implements Serializable{
    
    private Date begDate;
    private Date endDate;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private long id;
    
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

   /* public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/
    
    

    /**
     * Creates a new instance of DateIntervalBean
     */
    public DateIntervalBean() {
    }
}
