package com.ashokit.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Entity
@Data
@Table(name="studentEnq_table")
public class StudentEnquiriesEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer enqId;
	private String studName;
	private Integer contactNo;
	private String classMode;
	private String courseName;
	private String enqStatus;
	
	@CreationTimestamp
	@Column(name="createdDate", updatable=false)
	private LocalDate createdDate;
	
	@UpdateTimestamp
	@Column(name="updatedDate", insertable=false)
	private LocalDate updatedDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetailsEntity user;

}
