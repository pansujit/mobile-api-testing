package com.pitechplus.rcim.backoffice.dto.member;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberPaymentURLResponseDto {
String url;
}
