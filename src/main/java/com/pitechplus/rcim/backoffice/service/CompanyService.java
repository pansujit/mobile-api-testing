package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;


import com.pitechplus.rcim.backoffice.dto.supercompany.*;
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

@Component("company-controller")
public class CompanyService {

    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;


    @Value("${get.super.company}")
    private String getSuperCompany;




    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);




    public ResponseEntity<SuperCompanyDto> getSuperCompany(String xAuthToken, UUID companyId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity superCompanyGetHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get Super Company with id: " + companyId.toString())
                .url(gwAdminBaseUrl + getSuperCompany)
                .httpMethod(HttpMethod.GET)
                .requestBody(superCompanyGetHttpEntity)
                .response(SuperCompanyDto.class)
                .uriVariables(companyId)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }










    

    

    
}
