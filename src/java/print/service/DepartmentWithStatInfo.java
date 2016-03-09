/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.service;

import java.io.Serializable;
import print.dto.Data_Info;
import print.dto.Department;

/**
 * Отдел/работник и.т.д + информация о напечатанных им документах
 *
 * @author VVolgina
 */
public class DepartmentWithStatInfo implements Serializable{

    Department item;
    StatInfo info;

    public Department getItem() {
        return item;
    }

    public void setItem(Department item) {
        this.item = item;
    }

    public StatInfo getInfo() {
        return info;
    }

    public void setInfo(StatInfo info) {
        this.info = info;
    }

    public String getDepartment_fn() {
        return item.getDepartment_fn();
    }

    public void setDepartment_fn(String department_fn) {
        this.item.setDepartment_fn(department_fn);
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

    public DepartmentWithStatInfo(String name, String shortname, long id, long page, long doc) {
        item = new Department();
        info = new StatInfo();

        item.setDepartment_fn(name);
        item.setDepartment_sn(shortname);
        item.setId(id);

        info.setDocCount(doc);
        info.setPageCount(page);
    }

    public DepartmentWithStatInfo() {
        item = new Department();
        info = new StatInfo();
    }
}
