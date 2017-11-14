package com.pitechplus.rcim.backoffice.dto.booking.filteredsearch;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.BookingMetadataDto;
import com.pitechplus.rcim.backoffice.dto.booking.filteredsearch.SearchBookingResultDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchBookingResponseDto {



	List<SearchBookingResultDto> results;
	BookingMetadataDto metadata;


}
