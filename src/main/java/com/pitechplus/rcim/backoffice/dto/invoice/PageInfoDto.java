package com.pitechplus.rcim.backoffice.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class PageInfoDto {
	Integer currentPage;
	Integer pageSize;
	Integer totalPages;
	Integer totalResults;

}
