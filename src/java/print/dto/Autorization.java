/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author apopovkin
 */
@Entity
@Table(schema="PRINT", name = "Autorization")
public class Autorization extends Data_Info{
    @ManyToOne @JoinColumn(name = "id_account") private Account id_account;
    @Column(name = "login", nullable = false,length=200) private String login;
    @Column(name = "password", nullable = false,length=200) private String password;
    @Column(name = "rol") private boolean rol; 

    public boolean isRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }
    
    
    public Account getId_account() {
        return id_account;
    }

    public void setId_account(Account id_account) {
        this.id_account = id_account;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
