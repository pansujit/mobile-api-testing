package com.pitechplus.rcim.backoffice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.backuser.BackUser;
import com.pitechplus.rcim.backoffice.dto.backuser.BackUserCreate;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherGroupEditDto;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherGroupViewDto;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherViewDto;

@Component("voucher-controller")
public class VoucherService {

	@Value("${services.gw.admin}")
	private String gwAdminBaseUrl;
	@Value("${voucher}")
	private String voucherGetOrPost;
	@Value("${id.group.voucher}")
	private String getVoucherGroup;
	@Value("${id.voucher}")
	private String getVoucherId;
	
	
    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);

    public ResponseEntity<VoucherGroupViewDto> createVoucherGroup(String xAuthToken,VoucherGroupEditDto voucherGroupEditDto ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<VoucherGroupEditDto> voucherEntity = new HttpEntity<>(voucherGroupEditDto,headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Create a voucher Group")
                .url(gwAdminBaseUrl + voucherGetOrPost)
                .httpMethod(HttpMethod.POST)
                .requestBody(voucherEntity)
                .response(VoucherGroupViewDto.class)
                .sleepTimeAfterRequestInSec(2)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<VoucherGroupViewDto[]> searchAllVoucherGroup(String xAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity voucherEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get all voucher Groups")
                .url(gwAdminBaseUrl + voucherGetOrPost)
                .httpMethod(HttpMethod.GET)
                .requestBody(voucherEntity)
                .response(VoucherGroupViewDto[].class)
                .sleepTimeAfterRequestInSec(2)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<VoucherGroupViewDto> searchAVoucherGroup(String xAuthToken,String Id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        //headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity voucherEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get a single voucher group")
                .url(gwAdminBaseUrl + getVoucherGroup)
                .httpMethod(HttpMethod.GET)
                .requestBody(voucherEntity)
                .uriVariables(Id)
                .response(VoucherGroupViewDto.class)
                .sleepTimeAfterRequestInSec(2)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<VoucherViewDto> searchAVoucher(String xAuthToken,String voucherId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity voucherEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get a single voucher")
                .url(gwAdminBaseUrl + getVoucherId)
                .httpMethod(HttpMethod.GET)
                .requestBody(voucherEntity)
                .uriVariables(voucherId)
                .response(VoucherViewDto.class)
                .sleepTimeAfterRequestInSec(2)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }


}
