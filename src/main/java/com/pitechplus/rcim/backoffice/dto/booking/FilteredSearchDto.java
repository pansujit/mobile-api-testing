package com.pitechplus.rcim.backoffice.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.SubCompanyDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Created by dgliga on 21.08.2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilteredSearchDto {

    String startDate;
    String endDate;
    String endSiteId;
    String startSiteId;
    Integer passengers;
    String memberLogin;
    SubCompanyDto subCompanyDto;
}
