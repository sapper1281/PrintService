/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(schema="PRINT", name = "LOGGER")
public class Logger extends Data_Info{
    @ManyToOne @JoinColumn(name = "id_account") private Account id_account;
    @Column(name = "code", length=10) private String code;
    @Column(name = "Document", length=500) private String Document;
    @Column(name = "Count_Page") private int Count_Page;
    @Column(name = "dt_print") @Temporal(javax.persistence.TemporalType.DATE) private Date dt_print;
    @ManyToOne @JoinColumn(name = "id_print") private Print id_print;

    public Print getId_print() {
        return id_print;
    }

    public void setId_print(Print id_print) {
        this.id_print = id_print;
    }
    
    public Account getId_account() {
        return id_account;
    }

    public void setId_account(Account id_account) {
        this.id_account = id_account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDocument() {
        return Document;
    }

    public void setDocument(String Document) {
        this.Document = Document;
    }

    public int getCount_Page() {
        return Count_Page;
    }

    public void setCount_Page(int Count_Page) {
        this.Count_Page = Count_Page;
    }

    public Date getDt_print() {
        return dt_print;
    }

    public void setDt_print(Date dt_print) {
        this.dt_print = dt_print;
    }
    
}
