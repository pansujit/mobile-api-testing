package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.vehicle.*;
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

@Component("vehicle-controller")
public class VehicleService {

    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;

    @Value("${services.gw.mobile}")
    private String gwMobileBaseUrl;

    @Value("${get.vehicle}")
    private String getVehicle;
    
    @Value("${lock.vehicle}")
    private String lockVehicle;
    
    @Value("${unlock.vehicle}")
    private String unlockVehicle;
    
    
    


    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);





    
    public ResponseEntity<VehicleDto> getVehicle1(String xAuthToken, UUID vehicleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<VehicleDto> getVehicleHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get Vehicle with id: " + vehicleId)
                .url(gwAdminBaseUrl + getVehicle)
                .httpMethod(HttpMethod.GET)
                .requestBody(getVehicleHttpEntity)
                .uriVariables(vehicleId)
                .response(VehicleDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<VehicleDto> getVehicle(String xAuthToken, UUID vehicleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<VehicleDto> getVehicleHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Get Vehicle with id: " + vehicleId)
                .url(gwAdminBaseUrl + getVehicle)
                .httpMethod(HttpMethod.GET)
                .requestBody(getVehicleHttpEntity)
                .uriVariables(vehicleId)
                .response(VehicleDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    
    public void lockVehicle(String xAuthToken, UUID vehicleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_MOBILE.toString());
        HttpEntity<VehicleDto> getVehicleHttpEntity = new HttpEntity<>(headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: lock Vehicle with id: " + vehicleId)
                .url(gwMobileBaseUrl + lockVehicle)
                .httpMethod(HttpMethod.GET)
                .requestBody(getVehicleHttpEntity)
                .uriVariables(vehicleId)
                .build();
        restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    



    
}
