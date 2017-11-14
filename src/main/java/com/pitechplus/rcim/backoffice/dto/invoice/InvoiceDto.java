package com.pitechplus.rcim.backoffice.dto.invoice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.InvoiceType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class InvoiceDto {
	String id;
	String invoiceNumber;
	InvoiceType type;

}
