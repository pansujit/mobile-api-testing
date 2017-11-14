package com.pitechplus.rcim.backoffice.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.*;
import com.pitechplus.rcim.backoffice.dto.company.ParkingDto;
import com.pitechplus.rcim.backoffice.dto.supercompany.SuperCompanyDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

/**
 * Created by dgliga on 18.07.2017.
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id","VehicleVersionDto"})
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleDto {
	
	Set<AccessoryType> accessories;
	Integer doorsNumber;
	FuelLevelDto fuelLevel;
	FuelType fuelType;
	UUID id;
	String model;
	ParkingDto parking;
	String pictureUrl;
	String registrationNumber;
	Integer seats;
	ServiceLevelType serviceLevel;
	StatusType statusType;
	TransmissionType transmissionType;
    
    
}
