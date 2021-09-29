package com.etiya.ReCapProject.business.abstracts;

public interface FindeksScoreService {

	int getIndividualFindeksScore(String nationalIdentityNumber);

	int getCorporateFindeksScore(String taxNumber);

}
