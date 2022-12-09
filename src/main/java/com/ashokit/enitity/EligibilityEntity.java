package com.ashokit.enitity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "ELIGIBILITY_ENTITY")
public class EligibilityEntity {

	@Id
	@GeneratedValue
	private Integer eligId;
	private Integer citizenAppid;
	private String planName;
	private String planStatus;
	private Date planStartDate;
	private Date planEndDate;
	private Integer benifitAmnt;
	private String denialReaon;
	
}
