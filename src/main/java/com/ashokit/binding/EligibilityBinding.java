package com.ashokit.binding;

import java.time.LocalDate;

import lombok.Data;
@Data
public class EligibilityBinding {

	private Integer citizenAppid;
	private String planName;
	private String planStatus;
	private String holderName;
	private Double holderssn;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Integer benifitAmnt;
	private String denialReaon;
	private Integer kidAge;
}
