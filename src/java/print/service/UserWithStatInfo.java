/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.service;

import java.io.Serializable;
import print.dto.Account;
import print.dto.Department;

/**
 *
 * @author Администратор
 */
public class UserWithStatInfo implements Serializable {

    Account item;
    StatInfo info;

    public Account getItem() {
        return item;
    }

    public void setItem(Account item) {
        this.item = item;
    }

    public StatInfo getInfo() {
        return info;
    }

    public void setInfo(StatInfo info) {
        this.info = info;
    }

    public long getPageCount() {
        return info.getPageCount();
    }

    public void setPageCount(long pageCount) {
        info.setPageCount(pageCount);
    }

    public long getDocCount() {
        return info.getDocCount();
    }

    public void setDocCount(long docCount) {
        info.setDocCount(docCount);
    }

    public UserWithStatInfo() {
        item = new Account();
        info = new StatInfo();
    }
    
    public UserWithStatInfo(long id, String name, String surname, String patronomicname, long page, long doc){
        item = new Account();
        info = new StatInfo();
        item.setId(id);
        item.setAccount_name(name);
        item.setAccount_surname(surname);
        item.setAccount_patronymic(patronomicname);
        info.setDocCount(doc);
        info.setPageCount(page);
    }
}
