package com.etiya.ReCapProject.business.concretes;

import org.springframework.stereotype.Service;

import com.etiya.ReCapProject.business.abstracts.FindeksScoreService;
import com.etiya.ReCapProject.core.webServices.FindeksService;

@Service
public class FindeksServiceAdapter implements FindeksScoreService{
	 
	
	 
	@Override
	public int getIndividualFindeksScore(String identityNumber) {
		FindeksService findeksScoreService = new FindeksService();
		return findeksScoreService.getIndividualFindeksScore(identityNumber);
	}

	@Override
	public int getCorporateFindeksScore(String taxNumber) {
		FindeksService findeksScoreService = new FindeksService();
		return findeksScoreService.getCorporateFindeksScore(taxNumber);
	}

}
