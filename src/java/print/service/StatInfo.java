package print.service;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Информация о том, сколько напечатано документов и сколько в них страниц
 * @author VVolgina
 */
public class StatInfo implements Serializable{
    
    private long pageCount=0;
    private long docCount=0;

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getDocCount() {
        return docCount;
    }

    public void setDocCount(long docCount) {
        this.docCount = docCount;
    }
    
    /**
     * Увеличивает к-во страниц на указанную величину
     * @param pageCount соколько страниц прибавить
     */
    public void addToPageCount(long pageCount){
        this.pageCount+=pageCount;
    }
    
     /**
     * Увеличивает к-во документов на указанную величину
     * @param docCount соколько документов прибавить
     */
    public void addToDocCount(long docCount) {
        this.docCount += docCount;
    }
}
