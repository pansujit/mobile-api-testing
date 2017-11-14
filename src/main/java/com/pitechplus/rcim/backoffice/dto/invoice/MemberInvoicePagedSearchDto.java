package com.pitechplus.rcim.backoffice.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.dto.pagination.Page;

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
public class MemberInvoicePagedSearchDto {
	Page page;
	MemberInvoiceQueryDto query;
	MemberInvoiceSortDto sort;

}
