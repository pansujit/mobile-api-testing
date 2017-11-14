package com.pitechplus.rcim.backoffice.service;

import com.pitechplus.qautils.restutils.RestExchangeInfo;
import com.pitechplus.qautils.restutils.RestTemplateUtils;
import com.pitechplus.rcim.backoffice.data.enums.ApplicationType;
import com.pitechplus.rcim.backoffice.dto.backuser.BackUser;
import com.pitechplus.rcim.backoffice.dto.backuser.Login;
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

@Component("back-user-controller")
public class BackUserService {

    @Value("${services.gw.admin}")
    private String gwAdminBaseUrl;

    @Value("${backusers}")
    private String backUsers;

    @Value("${auth.user}")
    private String authUser;


    private RestTemplateUtils restTemplateUtils = new RestTemplateUtils(15000, 15000);



    public ResponseEntity<BackUser> authUser(Login login) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-origin", ApplicationType.GLIDE_BO.toString());
        HttpEntity<Login> loginDtoHttpEntity = new HttpEntity<>(login, headers);
        RestExchangeInfo restExchangeInfo = RestExchangeInfo.builder()
                .requestDescription("Call service: Auth Back User: " + login.getLogin())
                .url(gwAdminBaseUrl + authUser)
                .httpMethod(HttpMethod.POST)
                .requestBody(loginDtoHttpEntity)
                .response(BackUser.class)
                .build();
        return restTemplateUtils.makeExchange(restExchangeInfo, BackOfficeException.class);
    }









 
}
