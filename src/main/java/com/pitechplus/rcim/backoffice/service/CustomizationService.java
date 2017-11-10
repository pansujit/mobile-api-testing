package com.pitechplus.rcim.backoffice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.customizations.ResultSubscription;
import com.pitechplus.rcim.backoffice.dto.customizations.SUBSCRIPTION;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;

@Component("customization-controller")
public class CustomizationService {
	
    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;
    
    @Value("${custom.field.customization}")
    private String customField;
    
    
    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);

    public ResponseEntity<ResultSubscription> abcd(String xAuthToken,String superCompanyId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity customFieldHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: custom field on subscription" )
                .url(gwAdminBaseUrl + customField)
                .httpMethod(HttpMethod.GET)
                .uriVariables(superCompanyId)
                .requestBody(customFieldHttpEntity)
                .response(ResultSubscription.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    public void test(String xAuthToken,String superCompanyId) {
    	RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
    	restTemplate.setDefaultUriVariables(headers);
    	Object quote = restTemplate.getForObject(gwAdminBaseUrl + customField, Object.class,superCompanyId);
        System.out.println("hihih"+quote);
    
    	
    }

    

}
