package com.pitechplus.rcim.backoffice.dto.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.UsageType;
import com.pitechplus.rcim.backoffice.dto.common.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by dgliga on 06.07.2017.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id","zoneId"})
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SiteDto {

    UUID id;
    Integer timeUnitOfBooking;
    String name;
    Address address;
    String zoneId;
}
