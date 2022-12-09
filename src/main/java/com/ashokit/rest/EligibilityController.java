package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.binding.EligibilityBinding;
import com.ashokit.service.EligibilityService;

@RestController
public class EligibilityController {

	@Autowired
	private EligibilityService eligibilityService;
	
	@GetMapping("/view/{citienAppId}")
	public ResponseEntity<EligibilityBinding> elig(@PathVariable Integer citienAppId)
	{
		EligibilityBinding eligibilityDetails = eligibilityService.viewEligibilityDetails(citienAppId);
		return new ResponseEntity<>(eligibilityDetails,HttpStatus.OK);
	}
	
}
