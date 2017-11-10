package com.pitechplus.rcim.backoffice.dto.booking.search;

import com.pitechplus.rcim.backoffice.dto.common.Coordinates;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Created by dgliga on 14.02.2017.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryAddress {

    String city;
    String country;
    String formattedAddress;
    String postalCode;
    String streetName;
    String streetNumber;
    Coordinates coordinates;
}
