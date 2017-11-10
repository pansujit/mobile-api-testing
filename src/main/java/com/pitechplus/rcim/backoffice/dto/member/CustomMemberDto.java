package com.pitechplus.rcim.backoffice.dto.member;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.dto.common.Address;
import com.pitechplus.rcim.backoffice.dto.common.PhoneNumber;
import com.pitechplus.rcim.nissan.be.nissandto.members.DrivingLicenceDto;

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
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomMemberDto {
	
    String login;
    String password;
    String firstName;
    String lastName;
    PhoneNumber phoneNumber;
    String birthDate;
    Address address;
    Boolean vip;
    DrivingLicenceDto drivingLicence;
    Locale locale;
    UUID companyId;
    List<MemberCustomValues> memberCustomValues;

}
