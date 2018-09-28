package com.demo.main.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Plan implements Serializable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7156526077883281623L;

	@Id
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_USER", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	private Long id;
	private String name;
	private String desc;

	public Plan() {
	}

	public Plan(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", name=" + name + ", desc=" + desc + "]";
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
