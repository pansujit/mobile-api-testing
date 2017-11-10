package com.pitechplus.rcim.backoffice.dto.customizations;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"SUBSCRIPTION"
})
public class ResultSubscription {

	@JsonProperty("SUBSCRIPTION")
	private List<SUBSCRIPTION> sUBSCRIPTION = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public ResultSubscription() {
	}

	/**
	 * 
	 * @param sUBSCRIPTION
	 */
	public ResultSubscription(List<SUBSCRIPTION> sUBSCRIPTION) {
		super();
		this.sUBSCRIPTION = sUBSCRIPTION;
	}

	@JsonProperty("SUBSCRIPTION")
	public List<SUBSCRIPTION> getSUBSCRIPTION() {
		return sUBSCRIPTION;
	}

	@JsonProperty("SUBSCRIPTION")
	public void setSUBSCRIPTION(List<SUBSCRIPTION> sUBSCRIPTION) {
		this.sUBSCRIPTION = sUBSCRIPTION;
	}

	public ResultSubscription withSUBSCRIPTION(List<SUBSCRIPTION> sUBSCRIPTION) {
		this.sUBSCRIPTION = sUBSCRIPTION;
		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public ResultSubscription withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("sUBSCRIPTION", sUBSCRIPTION).append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sUBSCRIPTION).append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ResultSubscription) == false) {
			return false;
		}
		ResultSubscription rhs = ((ResultSubscription) other);
		return new EqualsBuilder().append(sUBSCRIPTION, rhs.sUBSCRIPTION).append(additionalProperties, rhs.additionalProperties).isEquals();
	}
}
