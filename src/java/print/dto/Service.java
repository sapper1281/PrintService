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
@Table(schema="PRINT", name = "Service")
public class Service extends Data_Info{
    @ManyToOne @JoinColumn(name = "id_print") private Print id_print;
    @Column(name = "model_katreg", length=100) private String model_katreg;
    @Column(name = "cost") private float cost;

    public Print getId_print() {
        return id_print;
    }

    public void setId_print(Print id_print) {
        this.id_print = id_print;
    }

    public String getModel_katreg() {
        return model_katreg;
    }

    public void setModel_katreg(String model_katreg) {
        this.model_katreg = model_katreg;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    

}
