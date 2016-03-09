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
@SuppressWarnings("serial")
@Entity
@Table( schema="PRINT", name = "Print")
public class Print extends Data_Info {
	@Column(name = "name", length = 100)
	private String name;
	@Column(name = "model", length = 100)
	private String model;
	@Column(name = "invent_num", length = 100)
	private String invent_num;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id_print")
	private List<Logger> id_print = new ArrayList<Logger>();
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id_print")
	private List<Service> id_print_service = new ArrayList<Service>();

	/**
	 * Возвращает 
	 * @return
	 */
	public List<Service> getId_print_service() {
		return id_print_service;
	}

	public void setId_print_service(List<Service> id_print_service) {
		this.id_print_service = id_print_service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getInvent_num() {
		return invent_num;
	}

	public void setInvent_num(String invent_num) {
		this.invent_num = invent_num;
	}

	public List<Logger> getId_print() {
		return id_print;
	}

	public void setId_print(List<Logger> id_print) {
		this.id_print = id_print;
	}

	/**
	 * Переносит всю информацию из данного объекта в объект-параметр. За
	 * исключением ключей и внешних ключей
	 * 
	 * @param obj
	 *            оюъект, в который коприуется информация
	 */
	public void cloneForBD(Print obj) {
		super.cloneForDB(obj);
		obj.setInvent_num(invent_num);
		obj.setModel(model);
		obj.setName(name);
	}

	@Override
	public String toString() {
		return "Print [name=" + name + ", model=" + model + ", invent_num="
				+ invent_num + "]";
	}

	public boolean equalsToDB(Print other) {
		
		if (invent_num == null) {
			if (other.invent_num != null)
				return false;
		} else if (!invent_num.equals(other.invent_num))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
