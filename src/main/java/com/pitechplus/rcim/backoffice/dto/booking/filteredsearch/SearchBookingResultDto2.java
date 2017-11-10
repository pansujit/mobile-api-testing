package com.pitechplus.rcim.backoffice.dto.booking.filteredsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.BookingType;
import com.pitechplus.rcim.backoffice.dto.vehicle.VehicleDto;
import com.pitechplus.rcim.backoffice.dto.vehicle.VehicleDto1;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Created by dgliga on 21.08.2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchBookingResultDto2 {

    BookingType type;
    DateLocationDto start;
    DateLocationDto end;
    VehicleDto1 vehicle;
    String comment;
    Integer reservedSeats;
    CarSharingInfoDto carSharingInfo;
    RideSharingInfoDto rideSharingInfo;
    ShuttleInfoDto shuttleInfo;

}
