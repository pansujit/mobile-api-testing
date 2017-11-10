package com.pitechplus.rcim.backoffice.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Created by dgliga on 18.07.2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"id","VehicleModelDto"})
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleVersionDto {

    UUID id;
    String name;
    VehicleModelDto model;

}
