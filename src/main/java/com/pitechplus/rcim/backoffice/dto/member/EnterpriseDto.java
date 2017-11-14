package com.pitechplus.rcim.backoffice.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.AccessoryType;
import com.pitechplus.rcim.backoffice.dto.supercompany.SuperCompanyDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Created by dgliga  on 21.07.2017.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnterpriseDto {

    String address;
    String city;
    String name;
    String phoneNumber;
    AccessoryType accountType;
    String zipCode;
    String email;
    String country;
    Float discountPercentage;
    String enterpriseUniqueId;
    String agencyCode;
    SuperCompanyDto companyAttachment;
}
