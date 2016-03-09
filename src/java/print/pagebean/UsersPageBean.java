/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.pagebean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import print.dto.Account;
import print.repository.Account_Repository;

/**
 *
 * @author VVolgina
 */
@ManagedBean
@RequestScoped
public class UsersPageBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="поля">
    private List<Account> beanList;
    private List<Account> filteredBeanList;
    private String errorMessage;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get-set">
    /**
     * Возвращает список пользователей
     *
     * @return список секций пользователей
     */
    public List<Account> getBeanList() {
        return beanList;
    }

    /**
     * Назначает список пользователей
     *
     * @param beanList список пользователей
     */
    public void setBeanList(List<Account> beanList) {
        this.beanList = beanList;
    }

    /**
     * Возвращает отфильтрованный список пользователей
     *
     * @return отфильтрованный список пользователей
     */
    public List<Account> getFilteredBeanList() {
        return filteredBeanList;
    }

    /**
     * Назначает отфильтрованный список пользователей
     *
     * @param filteredBeanList отфильтрованный список пользователей
     */
    public void setFilteredBeanList(List<Account> filteredBeanList) {
        this.filteredBeanList = filteredBeanList;
    }

    /**
     * Получает сообщение об ошибке, произшедшей на странице
     *
     * @return сообщение об ошибке, произшедшей на странице
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Назначает сообщение об ошибке, произшедшей на странице
     *
     * @param errorMessage сообщение об ошибке, произшедшей на странице
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    //</editor-fold>

    /**
     * Creates a new instance of SectionsPageBean
     */
    public UsersPageBean() {
        if (beanList == null) {
            //Извлекает из БД весь спиcок пользователей
            Account_Repository rep = new Account_Repository();
            beanList = rep.getAllAccount();
        }
    }

    /**
     * Переход на страницу данных логина
     *
     * @return "edit"
     */
    public String editItem() {
        return "edit";
    }
}
