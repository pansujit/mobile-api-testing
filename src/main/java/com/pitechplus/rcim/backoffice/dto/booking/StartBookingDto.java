package com.pitechplus.rcim.backoffice.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingStatusType;
import com.pitechplus.rcim.backoffice.data.enums.BookingType;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.CarSharingInfoDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.DateLocationDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.RideSharingInfoDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.ShuttleInfoDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberDto;
import com.pitechplus.rcim.backoffice.dto.vehicle.VehicleDto;
import com.pitechplus.rcim.backoffice.dto.vehicle.VehicleDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by dgliga on 24.08.2017.
 */

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartBookingDto {

  BookingActionsDto allowedActions;
  boolean belongsToUser;
  BluetoothTokenDto bluetoothToken;
  CarSharingInfoDto carSharingInfo;
  String comment;
  DateLocationDto end;
  DateLocationDto start;
  GroupSharingInfoDto groupSharingInfo;
  String id;
  MemberDto memberdto;
  int reservedSeats;
  RideSharingInfoDto rideSharingInfo;
  ShuttleInfoDto shutterInfo;
  BookingState state;
  BookingStatusType status;
  BookingType type;
  VehicleDto vehicle;
  List<BookingCustomValues> bookingCustomValues;
  
  

}
