package com.ecwid.consul.v1.acl.model;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jon Huang (jon5477)
 */
@JsonInclude(Include.NON_NULL)
public final class AclTemplatePolicy {
	@JsonProperty("TemplateID")
	@SerializedName("TemplateID")
	private String templateID;
	@JsonProperty("TemplateName")
	@SerializedName("TemplateName")
	private String templateName;
	@JsonProperty("TemplateVariables")
	@SerializedName("TemplateVariables")
	private Map<String, String> templateVariables;

	public AclTemplatePolicy() {
	}

	public AclTemplatePolicy(String templateID, String templateName, Map<String, String> templateVariables) {
		this.templateID = templateID;
		this.templateName = templateName;
		this.templateVariables = templateVariables;
	}

	public AclTemplatePolicy(String templateName, Map<String, String> templateVariables) {
		this.templateName = templateName;
		this.templateVariables = templateVariables;
	}

	public final String getTemplateID() {
		return templateID;
	}

	public final void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public final String getTemplateName() {
		return templateName;
	}

	public final void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public final Map<String, String> getTemplateVariables() {
		return templateVariables;
	}

	public final void setTemplateVariables(Map<String, String> templateVariables) {
		this.templateVariables = templateVariables;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(templateID, templateName, templateVariables);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclTemplatePolicy)) {
			return false;
		}
		AclTemplatePolicy other = (AclTemplatePolicy) obj;
		return Objects.equals(templateID, other.templateID) && Objects.equals(templateName, other.templateName)
				&& Objects.equals(templateVariables, other.templateVariables);
	}

	@Override
	public final String toString() {
		return "AclTemplatePolicy [templateID=" + templateID + ", templateName=" + templateName + ", templateVariables="
				+ templateVariables + "]";
	}
}