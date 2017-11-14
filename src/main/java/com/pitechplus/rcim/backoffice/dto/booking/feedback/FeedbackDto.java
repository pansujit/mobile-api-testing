package com.pitechplus.rcim.backoffice.dto.booking.feedback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDto {
	
	String comment;
	Integer externalCleanliness;
	Integer internalCleanliness;
	FeedbackReportDto report;
	

}
