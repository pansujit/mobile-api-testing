package com.pitechplus.rcim.backoffice.utils.mappers;


import com.pitechplus.rcim.backoffice.dto.member.CustomMemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberDto;
import com.pitechplus.rcim.backoffice.dto.supercompany.SuperCompanyDto;
import com.pitechplus.rcim.nissan.be.nissandata.nissanenums.ReviewStatus;
import  com.pitechplus.rcim.backoffice.dto.member.*;
public class Mapper {
	
    /**
     * This method is used to create a memberCreateDto object with valid data
     *
     * @param memberCreateDto -what member was sent on the request
     * @return memberCreateDto object with all fields having valid values
     */
    public static MemberDto mapMemberCreateDtoToMemberDto(CustomMemberDto memberCreateDto, SuperCompanyDto superCompanyDto) {
        return MemberDto.builder()
                .login(memberCreateDto.getLogin())
                .firstName(memberCreateDto.getFirstName())
                .lastName(memberCreateDto.getLastName())
                .phoneNumber(memberCreateDto.getPhoneNumber())
                .birthDate(memberCreateDto.getBirthDate())
                .address(memberCreateDto.getAddress())
                .vip(memberCreateDto.getVip())
                .enabled(null)
                .suspended(false)
                .commercialOffers(false)
                .professional(false)
                .validated(memberCreateDto.getDrivingLicence().getValidated())
                .status(ReviewStatus.APPLIED)
                .locale(memberCreateDto.getLocale())
                .company(superCompanyDto)
                .technician(false)
                .drivingLicence(memberCreateDto.getDrivingLicence())
                .build();
        			
    }

}
