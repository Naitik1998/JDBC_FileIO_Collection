package com.mindtree.comicapp.service;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Set;

import com.mindtree.comicapp.entity.SuperHero;
import com.mindtree.comicapp.entity.Univers;
import com.mindtree.comicapp.exception.service.ComicAppServiceException;

public interface ComicAppService {

	public String addUnivers(Univers univers) throws ComicAppServiceException;

	public String addSuperHero(SuperHero superHero) throws ComicAppServiceException;

	public String assignSuperHeroToUnivers(String spName, long universId) throws ComicAppServiceException;

	public Set<SuperHero> displaySuperHeroByUniversName(String universName1) throws ComicAppServiceException;

	public String updateSuperHeroHP(String sname, long hp1) throws ComicAppServiceException;

	public SuperHero displaySuperHeroWithMaxHpByUniversName(String universName2) throws ComicAppServiceException;

	public String writingIntoFile(String universName1, Set<SuperHero> superHeros) throws ComicAppServiceException;

	public BufferedReader fileReading() throws ComicAppServiceException;

	public Map<SuperHero, Univers> getAllDataOFSuperheroWithUnivers() throws ComicAppServiceException;

	public String writeIntoExcel() throws ComicAppServiceException;

	public void readFromExcel() throws ComicAppServiceException;

	public String serializationData(SuperHero superHeroData) throws ComicAppServiceException;

	public void deserializationData() throws ComicAppServiceException;

}
