package com.ylink.cim.admin.domain;

public class LimitGroupId implements java.io.Serializable {

	private static final long serialVersionUID = -4324693236333146397L;

	private String limitGroupId;

	private String limitId;

	public LimitGroupId() {
	}


	public boolean equals(final Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LimitGroupId))
			return false;
		LimitGroupId castOther = (LimitGroupId) other;

		return ((getLimitGroupId() == castOther.getLimitGroupId()) || (getLimitGroupId() != null
				&& castOther.getLimitGroupId() != null && getLimitGroupId().equals(castOther.getLimitGroupId())))
				&& ((getLimitId() == castOther.getLimitId()) || (getLimitId() != null && castOther.getLimitId() != null && getLimitId()
						.equals(castOther.getLimitId())));
	}

	public String getLimitGroupId() {
		return this.limitGroupId;
	}

	public String getLimitId() {
		return this.limitId;
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLimitGroupId() == null ? 0 : getLimitGroupId().hashCode());
		result = 37 * result + (getLimitId() == null ? 0 : getLimitId().hashCode());
		return result;
	}

	public void setLimitGroupId(final String limitGroupId) {
		this.limitGroupId = limitGroupId;
	}

	public void setLimitId(final String limitId) {
		this.limitId = limitId;
	}

}