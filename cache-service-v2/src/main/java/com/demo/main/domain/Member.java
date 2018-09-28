package com.demo.main.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Member implements Serializable {
	 @SuppressWarnings("unused")
	private static final long serialVersionUID = 7156526077883281623L;

	    @Id
	    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_USER", allocationSize = 1)
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	    private Long id;
	    private String name;
	    private String type;

	    public Member() {
	    }

	    public Member(String name, String type) {
	        this.name = name;
	        this.type = type;
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

		@Override
		public String toString() {
			return "Member [id=" + id + ", name=" + name + ", type=" + type + "]";
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		
}
