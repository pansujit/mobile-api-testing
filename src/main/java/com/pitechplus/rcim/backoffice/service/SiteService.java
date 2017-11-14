package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.company.ParkingDto;
import com.pitechplus.rcim.backoffice.dto.company.SiteDto;
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

@Component("site-controller")
public class SiteService {

    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;

 



    @Value("${parkings}")
    private String parkings;

    @Value("${get.site.parkings}")
    private String getSiteParkings;

    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);


    
    public ResponseEntity<ParkingDto[]> getSiteParkings(String xAuthToken, UUID siteId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity siteDtoHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get Parkings for site with id: " + siteId)
                .url(gwAdminBaseUrl + getSiteParkings)
                .httpMethod(HttpMethod.GET)
                .requestBody(siteDtoHttpEntity)
                .response(ParkingDto[].class)
                .uriVariables(siteId)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
}
