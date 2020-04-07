package com.mindtree.comicapp.entity;

import java.io.Serializable;

public class SuperHero implements Comparable<SuperHero>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7191681318192830864L;
	private long superHeroId;
	private String superHeroName;
	private long hp;

	public SuperHero() {
		// TODO Auto-generated constructor stub
	}

	public SuperHero(long superHeroId, String superHeroName, long hp) {
		super();
		this.superHeroId = superHeroId;
		this.superHeroName = superHeroName;
		this.hp = hp;
	}

	public long getSuperHeroId() {
		return superHeroId;
	}

	public void setSuperHeroId(long superHeroId) {
		this.superHeroId = superHeroId;
	}

	public String getSuperHeroName() {
		return superHeroName;
	}

	public void setSuperHeroName(String superHeroName) {
		this.superHeroName = superHeroName;
	}

	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (hp ^ (hp >>> 32));
		result = prime * result + (int) (superHeroId ^ (superHeroId >>> 32));
		result = prime * result + ((superHeroName == null) ? 0 : superHeroName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperHero other = (SuperHero) obj;
		if (hp != other.hp)
			return false;
		if (superHeroId != other.superHeroId)
			return false;
		if (superHeroName == null) {
			if (other.superHeroName != null)
				return false;
		} else if (!superHeroName.equals(other.superHeroName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SuperHero [superHeroId=" + superHeroId + ", superHeroName=" + superHeroName + ", hp=" + hp + "]";
	}

	@Override
	public int compareTo(SuperHero s) {
		if (superHeroId > s.getSuperHeroId())
			return 1;
		else if (superHeroId < s.getSuperHeroId()) {
			return -1;

		} else {
			return 0;
		}
	}

}
