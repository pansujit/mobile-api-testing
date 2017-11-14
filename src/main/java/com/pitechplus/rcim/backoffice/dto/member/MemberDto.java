package com.pitechplus.rcim.backoffice.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.Civility;
import com.pitechplus.rcim.backoffice.data.enums.MaritalStatus;
import com.pitechplus.rcim.backoffice.data.enums.ReviewStatus;
import com.pitechplus.rcim.backoffice.data.enums.SexDto;
import com.pitechplus.rcim.backoffice.dto.common.Address;
import com.pitechplus.rcim.backoffice.dto.common.PhoneNumber;
import com.pitechplus.rcim.backoffice.dto.supercompany.SuperCompanyDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by dgliga on 21.07.2017.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude= "id")
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto {

    UUID id;
    Civility civility;
    String login;
    String firstName;
    String lastName;
    PhoneNumber phoneNumber;
    PhoneNumber secondaryPhoneNumber;
    PhoneNumber emergencyPhoneNumber;
    String birthDate;
    String birthPlace;
    Address address;
    Boolean vip;
    Boolean enabled;
    Boolean suspended;
    Boolean commercialOffers;
    Boolean professional;
    Boolean validated;
    MemberAvatarDto avatar;
    ReviewStatus status;
    DrivingLicenceDto drivingLicence;
    DocumentaryEvidenceDto identityDocument;
    DocumentaryEvidenceDto employerCertificate;
    DocumentaryEvidenceDto lastTaxNotice;
    DocumentaryEvidenceDto lastPaySlip;
    Locale locale;
    SuperCompanyDto company;
    EnterpriseDto enterprise;
    Boolean technician;
    SexDto sex;
    MaritalStatus maritalStatus;
    String profession;
    String description;
    List<CommentResponseDto> comments;
}
