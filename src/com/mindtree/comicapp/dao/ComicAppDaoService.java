package com.mindtree.comicapp.dao;

import java.util.Map;
import java.util.Set;

import com.mindtree.comicapp.entity.SuperHero;
import com.mindtree.comicapp.entity.Univers;
import com.mindtree.comicapp.exception.dao.ComicAppDaoException;

public interface ComicAppDaoService {

	public String addUniversToDB(Univers univers) throws ComicAppDaoException;

	public String addSuperHeroToDB(SuperHero superHero) throws ComicAppDaoException;

	public String assignSuperHeroToUniversInDB(String spName, long universId) throws ComicAppDaoException;

	public boolean validateUnivers(Univers univers) throws ComicAppDaoException;

	public boolean validateSuperHero(String superHeroName) throws ComicAppDaoException;

	public boolean validateUnivers(long universId) throws ComicAppDaoException;

	public Set<SuperHero> getSuperHeroByUniversName(long universId) throws ComicAppDaoException;

	public long getUniversId(String universName1) throws ComicAppDaoException;

	public String updateSuperHeroHpInDB(String sname, long hp1) throws ComicAppDaoException;

	public SuperHero getSuperHeroWithMaxHpByUniversName(long universId) throws ComicAppDaoException; 
	
	public Map<SuperHero, Univers> getUniversAndSuperheroFromDB() throws ComicAppDaoException; 

	
}
