package com.ashokit.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="userDetails_table")
public class UserDetailsEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	private Integer phNo;
	private String email;
	private String pwd;
	private String accStatus;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<StudentEnquiriesEntity> studEnquries;
	

}
