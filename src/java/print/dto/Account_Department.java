/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.dto;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(schema="PRINT", name = "Account_Department")
public class Account_Department extends Data_Info {
    
    
    /*@Column(name = "id_account") private int id_account;*/
    @ManyToOne @JoinColumn(name = "id_department") private Department id_department;
    @ManyToOne @JoinColumn(name = "id_account") private Account id_account;
    
    public Account getId_account() {
        return id_account;
    }

    public void setId_account(Account id_account) {
        this.id_account = id_account;
    }

    public Department getId_department() {
        return id_department;
    }

    public void setId_department(Department id_department) {
        this.id_department = id_department;
    }

     public boolean equalsToDB(Account_Department other) {
        if (id_department == null) {
            if (other.id_department != null) {
                return false;
            }
        } else if (!id_department.equals(other.id_department)) {
            return false;
        }
        if (id_account == null) {
            if (other.id_account != null) {
                return false;
            }
        } else if (!id_account.equals(other.id_account)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account_Department [id_account=" + id_account.toString() + ", id_department="
                + id_department.toString() + "]";
    }

    /**
     * Переносит всю информацию из данного объекта в объект-параметр. За
     * исключением ключей и внешних ключей
     *
     * @param obj оюъект, в который коприуется информация
     */
    public void cloneForBD(Account_Department obj) {
        super.cloneForDB(obj);
        obj.setId_account(id_account);
        obj.setId_department(id_department);
    }

    
    
    
}
