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
@Table(schema="PRINT", name = "ACCOUNTS")
public class Account extends Data_Info {

    @Column(name = "account_sn", length = 200)
    private String account_sn;
    @Column(name = "account_surname", length = 500)
    private String account_surname;
    @Column(name = "account_name", length = 500)
    private String account_name;
    @Column(name = "account_patronymic", length = 500)
    private String account_patronymic;
    @Column(name = "e_mail", length = 500)
    private String e_mail;
   @Column(name = "phone", length = 500)
    private String phone;
   /*  @Column(name = "ip", length = 500)
    private String ip;
    @Column(name = "job", length = 500)
    private String job;
    @Column(name = "tab_num", length = 500)
    private String tab_num;*/
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "id_account")
    private List<Account_Department> all_id_account = new ArrayList<Account_Department>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "id_account")
    private List<Autorization> all_id_account_autorization = new ArrayList<Autorization>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "id_account")
    private List<Logger> all_id_account_logger = new ArrayList<Logger>();

    public List<Autorization> getAll_id_account_autorization() {
        return all_id_account_autorization;
    }

    public void setAll_id_account_autorization(List<Autorization> all_id_account_autorization) {
        this.all_id_account_autorization = all_id_account_autorization;
    }

    public List<Logger> getAll_id_account_logger() {
        return all_id_account_logger;
    }

    public void setAll_id_account_logger(List<Logger> all_id_account_logger) {
        this.all_id_account_logger = all_id_account_logger;
    }

    public List<Account_Department> getAll_id_account() {
        return all_id_account;
    }

    public void setAll_id_account(List<Account_Department> all_id_account) {
        this.all_id_account = all_id_account;
    }

    /* @OneToMany(cascade = CascadeType.ALL, mappedBy = "id_account")
     private List<Persons> allPersons = new ArrayList<Persons>();
    
     */
    public String getAccount_sn() {
        return account_sn;
    }

    public void setAccount_sn(String account_sn) {
        this.account_sn = account_sn;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_surname() {
        return account_surname;
    }

    public void setAccount_surname(String account_surname) {
        this.account_surname = account_surname;
    }

    public String getAccount_patronymic() {
        return account_patronymic;
    }

    public void setAccount_patronymic(String account_patronymic) {
        this.account_patronymic = account_patronymic;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTab_num() {
        return tab_num;
    }

    public void setTab_num(String tab_num) {
        this.tab_num = tab_num;
    }*/
    
    public String getFIO(){
        return account_surname+" "+account_name+" "+account_patronymic;
    }
   
    
    

    public boolean equalsToDB(Account other) {
        if (account_sn == null) {
            if (other.account_sn != null) {
                return false;
            }
        } else if (!account_sn.equals(other.account_sn)) {
            return false;
        }
        
        if (account_surname == null) {
            if (other.account_surname != null) {
                return false;
            }
        } else if (!account_surname.equals(other.account_surname)) {
            return false;
        }
        
        if (account_name == null) {
            if (other.account_name != null) {
                return false;
            }
        } else if (!account_name.equals(other.account_name)) {
            return false;
        }
        if (account_patronymic == null) {
            if (other.account_patronymic != null) {
                return false;
            }
        } else if (!account_patronymic.equals(other.account_patronymic)) {
            return false;
        }
      /*  if (e_mail == null) {
            if (other.e_mail != null) {
                return false;
            }
        } else if (!e_mail.equals(other.e_mail)) {
            return false;
        }
        if (phone == null) {
            if (other.phone != null) {
                return false;
            }
        } else if (!phone.equals(other.phone)) {
            return false;
        }
        if (ip == null) {
            if (other.ip != null) {
                return false;
            }
        } else if (!ip.equals(other.ip)) {
            return false;
        }
         if (tab_num == null) {
            if (other.tab_num != null) {
                return false;
            }
        } else if (!tab_num.equals(other.tab_num)) {
            return false;
        }
          if (job == null) {
            if (other.job != null) {
                return false;
            }
        } else if (!job.equals(other.job)) {
            return false;
        }*/
        return true;
    }

    @Override
    public String toString() {
        return "Account [account_sn=" + account_sn + ""
                + ", account_surname=" + account_surname
        + ", account_name=" + account_name 
        + ", account_patronymic=" + account_patronymic 
       /* + ", e_mail=" + e_mail 
        + ", phone=" + phone 
        + ", ip=" + ip  
                + ", job=" + job
                + ", tab_num=" + tab_num*/
        + "]";
    }

    /**
     * Переносит всю информацию из данного объекта в объект-параметр. За
     * исключением ключей и внешних ключей
     *
     * @param obj оюъект, в который коприуется информация
     */
    public void cloneForBD(Account obj) {
        super.cloneForDB(obj);
        obj.setAccount_sn(account_sn);
        obj.setAccount_surname(account_surname);
        obj.setAccount_name(account_name);
        obj.setAccount_patronymic(account_patronymic);
       /* obj.setE_mail(e_mail);
        obj.setPhone(phone);
        obj.setIp(ip);
        obj.setJob(job);
        obj.setTab_num(tab_num);*/
    }
}
