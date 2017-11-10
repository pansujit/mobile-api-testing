package com.pitechplus.rcim.backoffice.dto.customizations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SUBSCRIPTION {
	String form;
	int position;
	String name;
	String fieldType;
	Boolean mandatory;
	int min;
	int max;
	Boolean visible;
	List<CompanyCustomFieldLabel> companyCustomFieldLabels;
	List<CustomFileToValidate> customFileToValidate;
	String id;


}