package com.mindtree.comicapp.entity;

public class Univers implements Comparable<Univers> {

	private long universId;
	private String universName;

	public Univers(long universId, String universName) {
		this.universId = universId;
		this.universName = universName;
	}

	public Univers() {
		// TODO Auto-generated constructor stub
	}

	public long getUniversId() {
		return universId;
	}

	public void setUniversId(long universId) {
		this.universId = universId;
	}

	public String getUniversName() {
		return universName;
	}

	public void setUniversName(String universName) {
		this.universName = universName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (universId ^ (universId >>> 32));
		result = prime * result + ((universName == null) ? 0 : universName.hashCode());
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
		Univers other = (Univers) obj;
		if (universId != other.universId)
			return false;
		if (universName == null) {
			if (other.universName != null)
				return false;
		} else if (!universName.equals(other.universName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Univers [universId=" + universId + ", universName=" + universName + "]";
	}

	@Override
	public int compareTo(Univers u) {
		if (universId > u.universId) {
			return 1;

		} else if (universId < u.universId) {
			return -1;

		}
		return 0;
	}

}
