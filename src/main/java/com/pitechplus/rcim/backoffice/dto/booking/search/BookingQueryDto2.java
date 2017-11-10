package com.pitechplus.rcim.backoffice.dto.booking.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.AccessoryType;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingStatusType;
import com.pitechplus.rcim.backoffice.data.enums.BookingType;
import com.pitechplus.rcim.backoffice.data.enums.FuelType;
import com.pitechplus.rcim.backoffice.data.enums.StatusPayment;
import com.pitechplus.rcim.backoffice.data.enums.TransmissionType;
import com.pitechplus.rcim.backoffice.data.enums.UsageType;
import com.pitechplus.rcim.backoffice.data.enums.VehicleType;
import com.pitechplus.rcim.backoffice.dto.pagination.Page;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

/**
 * Created by dgliga on 30.08.2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingQueryDto2 {
	AccessoryType accessoryType;
	String companyId;
	String doorsNumber;
	SearchDateLocationDto end;
	FuelType fuelType;
	Page page;
	Integer passengers;
	SearchDateLocationDto start;
	String startParkingId;
	TransmissionType transmissionType;
	UsageType usageType;
	VehicleType vehicleType;
	String voucherCode;


}
