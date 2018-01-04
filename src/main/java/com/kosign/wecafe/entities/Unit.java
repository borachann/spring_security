package com.kosign.wecafe.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Unit")
public class Unit {
	
	@Id
	@SequenceGenerator(allocationSize=1, initialValue=2, sequenceName="unit_unit_id_seq", name="unit_id")
	@GeneratedValue(generator="unit_id", strategy=GenerationType.SEQUENCE)
	@Column(name="unit_id")
	private Long unitId;

	
	@Column(name="qty")
	private Long qty;

	@Column(name="unit_name")
	private String unitName;
	
	@Column(name="convert_to")
	private String to;

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	@Override
	public String toString(){
		return ( "unitname = " + unitName + " catId = " + unitId + " convert to = " + to);
	}

}
