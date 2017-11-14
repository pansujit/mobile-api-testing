package com.pitechplus.rcim.backoffice.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.InvoiceStatus;
import com.pitechplus.rcim.backoffice.data.enums.InvoiceType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@FieldDefaults(level=AccessLevel.PRIVATE)
public class MemberInvoiceQueryDto {
	
	String maxCreatedDate;
	String minCreatedDate;
	Integer month;
	String reference;
	InvoiceStatus status;
	InvoiceType type;
	String vehicleRegistrationNumber;
	Boolean withTransactions;
	Integer year;


}
