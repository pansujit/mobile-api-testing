package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.common.FileDto;
import com.pitechplus.rcim.backoffice.utils.exceptions.BackOfficeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;



/**
 * Created by dgliga on 26.09.2017.
 */

@Component("configs-controller")
public class ConfigsService {

    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;

    @Value("${services.gw.mobile}")
    private String gwMobileBaseUrl;

    @Value("${colors}")
    private String colors;

    @Value("${files}")
    private String files;

    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);

    public ResponseEntity<FileDto> createFile(String xAuthToken, FileDto fileDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<FileDto> fileDtoHttpEntity = new HttpEntity<>(fileDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Create file")
                .url(gwAdminBaseUrl + files)
                .httpMethod(HttpMethod.POST)
                .requestBody(fileDtoHttpEntity)
                .response(FileDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }
    
    public ResponseEntity<FileDto> mobileCreateFile(String xAuthToken, FileDto fileDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", xAuthToken);
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<FileDto> fileDtoHttpEntity = new HttpEntity<>(fileDto, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Create file")
                .url(gwMobileBaseUrl + files)
                .httpMethod(HttpMethod.POST)
                .requestBody(fileDtoHttpEntity)
                .response(FileDto.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }

}
