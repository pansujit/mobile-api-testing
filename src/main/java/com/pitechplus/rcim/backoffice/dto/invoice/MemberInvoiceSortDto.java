package com.pitechplus.rcim.backoffice.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.InvoiceProperty;
import com.pitechplus.rcim.backoffice.data.enums.SortDirection;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@FieldDefaults(level=AccessLevel.PRIVATE)
public class MemberInvoiceSortDto {
	
	
	SortDirection direction;
	InvoiceProperty property;

}
