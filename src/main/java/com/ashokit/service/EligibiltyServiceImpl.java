package com.ashokit.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.el.parser.AstPlus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.EligibilityBinding;
import com.ashokit.enitity.AdminPlansEntity;
import com.ashokit.enitity.ChildEntity;
import com.ashokit.enitity.CitienPlansEntity;
import com.ashokit.enitity.CitizenappEntity;
import com.ashokit.enitity.EducationEntity;
import com.ashokit.enitity.EligibilityEntity;
import com.ashokit.enitity.IncomeEntity;
import com.ashokit.kids.KidsAge;
import com.ashokit.repo.AdminPlansRepo;
import com.ashokit.repo.ChildRepo;
import com.ashokit.repo.CitienPlansRepo;
import com.ashokit.repo.CitizenAppRepo;
import com.ashokit.repo.EducationRepo;
import com.ashokit.repo.IncomeRepo;

@Service
public class EligibiltyServiceImpl implements EligibilityService{

	@Autowired
	private CitienPlansRepo citizenPlansRepo;
	@Autowired
	private AdminPlansRepo adminPlansRepo;
	@Autowired
	private IncomeRepo incomeRepo;
	@Autowired
	private ChildRepo childRepo;
	@Autowired
	private EducationRepo educationRepo;
	@Autowired
	private CitizenAppRepo citizenAppRepo;
	

	@Override
	public EligibilityBinding viewEligibilityDetails(Integer citizenAppid) {

		String planStatus=null;
		LocalDate today=LocalDate.now();
		
		CitienPlansEntity citienPlansEntity = citizenPlansRepo.findByCitizenAppid(citizenAppid);
		Integer citizenPlanid = citienPlansEntity.getCitizenPlanid();
		
		Optional<AdminPlansEntity> optional = adminPlansRepo.findById(citizenPlanid);
		AdminPlansEntity adminPlansEntity = optional.get();
		String planName = adminPlansEntity.getPlanName();
		
		IncomeEntity incomeEntity = incomeRepo.findByCitizenAppid(citizenAppid);
		Double monthlyIncome = incomeEntity.getMonthlyIncome();
		
		KidsAge age=new KidsAge();
		ChildEntity childEntity = childRepo.findByCitizenAppid(citizenAppid);
		Integer kidAge = childEntity.getKidAge();
		boolean kidsAgeEligibily = age.kidsAgeEligibily(kidAge);
		
EducationEntity educationEntity = educationRepo.findByCitizenAppid(citizenAppid);
Integer graduationYear = educationEntity.getGraduationYear();

Optional<CitizenappEntity> optional2 = citizenAppRepo.findById(citizenAppid);
CitizenappEntity citizenappEntity = optional2.get();
int citizenyears = Period.between(citizenappEntity.getDob(), today).getYears();
		
		EligibilityBinding eligibilityBinding=new EligibilityBinding();
		eligibilityBinding.setBenifitAmnt(400);
		eligibilityBinding.setHolderName(citizenappEntity.getFullName());
		eligibilityBinding.setHolderssn(citizenappEntity.getSsn());
		eligibilityBinding.setPlanName(planName);
		eligibilityBinding.setCitizenAppid(citizenAppid);
		eligibilityBinding.setPlanStatus(planStatus);
		
		if("SNAP".equals(planName) && monthlyIncome>5000)
		{
planStatus="Denied";
eligibilityBinding.setPlanStatus(planStatus);
		eligibilityBinding.setDenialReaon("monthly income is more");
		}
		if("CCAP".equals(planName) && kidAge>17)
		{
			planStatus="Denied";
			eligibilityBinding.setDenialReaon("kid age must be less tha 16 years");
		}
		if("MEDICARE".equals(planName) && citizenyears <65)
		{
			planStatus="Denied";
			eligibilityBinding.setDenialReaon("age must be less tha 65 years");
		}
		if("NJW".equals(planName) && graduationYear==null)
{
	planStatus="Denied";
	eligibilityBinding.setDenialReaon("year must be mentioned");
}
		planStatus="Approved";
			if("Approved".equals(planStatus))
			{
				eligibilityBinding.setPlanStartDate(today);
				eligibilityBinding.setPlanEndDate(today.plusMonths(6));
			}
			else {
				eligibilityBinding.setPlanStartDate(null);
				eligibilityBinding.setPlanEndDate(null);
			}
			saveElibilityDetails(eligibilityBinding);
	
	return eligibilityBinding;
	}
	private String saveElibilityDetails(EligibilityBinding eligibilityBinding)
	{
		EligibilityEntity eligibilityEntity=new EligibilityEntity();
		BeanUtils.copyProperties(eligibilityBinding, eligibilityEntity);
		return "Data saved ";
	}

}