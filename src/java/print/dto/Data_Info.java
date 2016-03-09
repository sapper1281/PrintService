/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;


/**
 *
 * @author apopovkin
 */
@MappedSuperclass
public class Data_Info implements Serializable{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id") private long id;
        @Column(name = "dt_beg", nullable = false) @Temporal(javax.persistence.TemporalType.DATE) private Date dt_beg= new Date();
        @Column(name = "dt_end") @Temporal(javax.persistence.TemporalType.DATE) private Date dt_end;
        @Column(name = "dt_create", nullable = false) @Temporal(javax.persistence.TemporalType.TIMESTAMP) private Date dt_create= new Date();
        @Column(name = "delete_flag", nullable = false) private boolean delete_flag=false;

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    /**
       * Извлекает дату начала актуальности записи
       * 
       * @return Извлекает дату начала актуальности записи
       */
    public Date getDt_beg() {
        return dt_beg;
    }

    /**
       * Добавляет дату начала актуальности записи
       * 
       * @return Добавляет дату начала актуальности записи
       */
    public void setDt_beg(Date dt_beg) {
        this.dt_beg = dt_beg;
    }

    /**
       * Извлекает дату окончания актуальности записи
       * 
       * @return Извлекает дату окончания актуальности записи
       */
    public Date getDt_end() {
        return dt_end;
    }

    /**
       * Добавляет дату окончания актуальности записи
       * 
       * @return Добавляет дату окончания актуальности записи
       */
    public void setDt_end(Date dt_end) {
        this.dt_end = dt_end;
    }

    /**
       * Извлекает дату создания
       * 
       * @return Извлекает дату создания
       */
    public Date getDt_create() {
        return dt_create;
    }

    /**
       * Добавить дату создания
       * 
       * @return Добавить дату создания
       */
    public void setDt_create(Date dt_create) {
        this.dt_create = dt_create;
    }

    /**
       * Проверяет, является ли объект удаленным
       * 
       * @return true- если удален, false-иначе
       */

    public boolean isDelete_flag() {
        return delete_flag;
    }

    /**
       * Добавить флаг удаления
       * 
       * @return Добавить флаг удаления
       */

    public void setDelete_flag(boolean delete_flag) {
        this.delete_flag = delete_flag;
    }
        
          
    /**
     * Переносит информацию из данного объекта в объект-параметр
     * @param obj объект-параметр
     */
    public void cloneForDB(Data_Info obj){
    	obj.setDelete_flag(delete_flag);
    	obj.setDt_beg(dt_beg);
    	obj.setDt_create(dt_create);
    	obj.setDt_end(dt_end);
    }    
        
        
}
