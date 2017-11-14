package com.pitechplus.rcim.backoffice.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitechplus.rcim.backoffice.data.enums.ReviewStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by dgliga  on 21.07.2017.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (exclude="id")
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentaryEvidenceDto {

    UUID id;
    UUID fileId;
    Boolean validated;
    ReviewStatus status;
}
