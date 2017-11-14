package com.pitechplus.rcim.backoffice.dto.booking.search;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.BookingState;
import com.pitechplus.rcim.backoffice.data.enums.BookingType;
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
public class BookingQueryDto {
	String groupId;
	String groupSearchEndDate;
	String groupSearchStartDate;
	String memberId;
	Page page;
	BookingSorterDto sort;
	BookingType type;
	List<BookingState> states;

		

}
