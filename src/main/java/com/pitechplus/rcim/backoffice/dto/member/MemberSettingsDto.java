package com.pitechplus.rcim.backoffice.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberSettingsDto {
	boolean sendMailForCallCenterChange;
	boolean sendMailForPersonalChange;
	boolean sendNotificationsByEmail;
	boolean sendNotificationsBySms;
	Integer smsBeforeArrivalTime;
	Integer smsBeforeDepartureTime;

}
