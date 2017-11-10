package com.pitechplus.rcim.backoffice.utils;

import static com.pitechplus.rcim.backoffice.utils.DataExtractors.extractXAuthTokenFromResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitechplus.rcim.backoffice.dto.backuser.Login;
import com.pitechplus.rcim.backoffice.service.BackUserService;
import com.pitechplus.rcim.backoffice.service.ConfigsService;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component("file-Ids")
public class CommonUtils {
    @Autowired
    ConfigsService backOfficeService;

    @Autowired
    BackUserService backUserService;
    
    @Value("${bo.super.admin.username}")
    String boSuperAdminUsername;

    @Value("${bo.super.admin.password}")
    String boSuperAdminPassword;


    public  List<UUID>  createArrayOfValidFiles(int nrOfValidFiles) {
    	
        return IntStream.range(0, nrOfValidFiles)
                .mapToObj(index -> backOfficeService.createFile(extractXAuthTokenFromResponse(backUserService.authUser
                        (new Login(boSuperAdminUsername, boSuperAdminPassword))), DtoBuilders.buildFile()).getBody().getId())
                .collect(Collectors.toList());
    }
}
