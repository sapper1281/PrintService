/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(schema="PRINT", name = "Department")
public class Department extends Data_Info{
    @Column(name = "department_sn",length=200) private String department_sn;
    @Column(name = "department_fn",length=500) private String department_fn;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "id_department")
    private List<Account_Department> all_id_department= new ArrayList<Account_Department>();

    public List<Account_Department> getAll_id_department() {
        return all_id_department;
    }

    public void setAll_id_department(List<Account_Department> all_id_department) {
        this.all_id_department = all_id_department;
    }

    
    
    public String getDepartment_sn() {
        return department_sn;
    }

    public void setDepartment_sn(String department_sn) {
        this.department_sn = department_sn;
    }

    public String getDepartment_fn() {
        return department_fn;
    }

    public void setDepartment_fn(String department_fn) {
        this.department_fn = department_fn;
    }
    public boolean equalsToDB(Department other) {
        if (department_sn == null) {
            if (other.department_sn != null) {
                return false;
            }
        } else if (!department_sn.equals(other.department_sn)) {
            return false;
        }
        if (department_fn == null) {
            if (other.department_fn != null) {
                return false;
            }
        } else if (!department_fn.equals(other.department_fn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Department [department_sn=" + department_sn + ", department_fn="
                + department_fn + "]";
    }

    /**
     * Переносит всю информацию из данного объекта в объект-параметр. За
     * исключением ключей и внешних ключей
     *
     * @param obj оюъект, в который коприуется информация
     */
    public void cloneForBD(Department obj) {
        super.cloneForDB(obj);
        obj.setDepartment_sn(department_sn);
        obj.setDepartment_fn(department_fn);
    }
}
