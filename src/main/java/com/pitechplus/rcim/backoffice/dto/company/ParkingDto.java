package com.pitechplus.rcim.backoffice.dto.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.dto.common.Address;
import com.pitechplus.rcim.backoffice.dto.common.Coordinates;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by dgliga on 13.07.2017.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id","siteId"})
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingDto{

    UUID id;
    UUID siteId;
    SiteDto site;
    String additionalInformation;
    Address address;
    Boolean alwaysOpen;
    Coordinates coordinates;
    Boolean disabledAccess;
    Boolean electricCharging;
    String groupId;
    Boolean gsmConnection;
    String name;
    Boolean privateAccess;
    Integer radius;
}
