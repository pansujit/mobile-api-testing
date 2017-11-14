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
import com.pitechplus.rcim.backoffice.dto.invoice.InvoiceResultDto;
import com.pitechplus.rcim.backoffice.dto.invoice.MemberInvoicePagedSearchDto;
import com.pitechplus.rcim.backoffice.dto.member.CustomMemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberDto;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;

@Component("invoice-test-controller")
public class InvoiceService {
	
	   @Value("${services.gw.mobile}")
	    private String gwMobileBaseUrl;
	   
	   @Value("${search.invoice}")
	    private String searchInvoice;
	   
		
		
	    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);
	    
	    public ResponseEntity<InvoiceResultDto> searchInvoice(String xAuthToken, 
	    		MemberInvoicePagedSearchDto invoiceData) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("X-AUTH-TOKEN", xAuthToken);
	        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
	        HttpEntity<MemberInvoicePagedSearchDto> CustomMemberHttpEntity = new HttpEntity<>(invoiceData,headers);
	        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
	                .requestDescription("Call service: Creating memeber with custom Field " )
	                .url(gwMobileBaseUrl + searchInvoice)
	                .httpMethod(HttpMethod.POST)
	                .requestBody(CustomMemberHttpEntity)
	                .response(InvoiceResultDto.class)
	                .build();
	        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
	    }

}
