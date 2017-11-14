package com.pitechplus.rcim.backoffice.dto.booking.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.AccessoryType;
import com.pitechplus.rcim.backoffice.data.enums.FuelType;
import com.pitechplus.rcim.backoffice.data.enums.TransmissionType;
import com.pitechplus.rcim.backoffice.data.enums.UsageType;
import com.pitechplus.rcim.backoffice.dto.pagination.Page;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchBookingDto {
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
	String voucherCode;

}
