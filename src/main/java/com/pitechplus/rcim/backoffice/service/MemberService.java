package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.member.CustomMemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberPaymentURLResponseDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberSettingsDto;
import com.pitechplus.rcim.backoffice.dto.member.PaymentCardDto;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by dgliga on 26.09.2017.
 */

@Component("member-controller")
public class MemberService {

    
    @Value("${services.gw.mobile}")
    private String gwMobileBaseUrl;
	
	@Value("${member.payment.url}")
	private String paymentUrl;
	
	@Value("${member.information}")
	private String memberInformation;
	
	@Value("${member.settings}")
	private String memberSettings;
	
	@Value("${member.card.info}")
	private String memberCardInfo;
	
	@Value("${members}")
	private String members;
	
	
    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);
    
    public ResponseEntity<MemberDto> createMemberWithCustomField(String xAuthToken, CustomMemberDto customMemberDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<CustomMemberDto> CustomMemberHttpEntity = new HttpEntity<>(customMemberDto,headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Creating memeber with custom Field " )
                .url(gwMobileBaseUrl + members)
                .httpMethod(HttpMethod.POST)
                .requestBody(CustomMemberHttpEntity)
                .response(MemberDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<MemberPaymentURLResponseDto> getPaymentURL(String xAuthToken, String  memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity urlHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: get the payment url of member of id: "+ memberId )
                .url(gwMobileBaseUrl + paymentUrl)
                .httpMethod(HttpMethod.GET)
                .requestBody(urlHttpEntity)
                .uriVariables(memberId)
                .response(MemberPaymentURLResponseDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<MemberDto> getSingleMember(String xAuthToken, String  memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity urlHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: get the information of member: "+ memberId )
                .url(gwMobileBaseUrl + memberInformation)
                .httpMethod(HttpMethod.GET)
                .requestBody(urlHttpEntity)
                .uriVariables(memberId)
                .response(MemberDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<MemberSettingsDto> getMemberSetting(String xAuthToken, String  memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity settingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: get setting information of member : "+ memberId )
                .url(gwMobileBaseUrl + memberSettings)
                .httpMethod(HttpMethod.GET)
                .requestBody(settingHttpEntity)
                .uriVariables(memberId)
                .response(MemberSettingsDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<MemberSettingsDto> putMemberSetting(String xAuthToken, String  memberId,
    		MemberSettingsDto memberSettingDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<MemberSettingsDto> settingHttpEntity = new HttpEntity<>(memberSettingDto,headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: get setting information of member : "+ memberId )
                .url(gwMobileBaseUrl + memberSettings)
                .httpMethod(HttpMethod.PUT)
                .requestBody(settingHttpEntity)
                .uriVariables(memberId)
                .response(MemberSettingsDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<PaymentCardDto> getmemberCardInfo(String xAuthToken, String  memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity settingHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: get card information of the member : "+ memberId )
                .url(gwMobileBaseUrl + memberCardInfo)
                .httpMethod(HttpMethod.GET)
                .requestBody(settingHttpEntity)
                .uriVariables(memberId)
                .response(PaymentCardDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    
    
    
  
}
