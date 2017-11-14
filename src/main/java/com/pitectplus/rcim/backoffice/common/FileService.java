package com.pitectplus.rcim.backoffice.common;

import com.pitechplus.rcim.backoffice.dto.backuser.Login;
import com.pitechplus.rcim.backoffice.service.ConfigsService;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.mobile.service.MobileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pitechplus.rcim.backoffice.utils.DataExtractors.extractXAuthTokenFromResponse;

/**
 * Created by dgliga  on 16.08.2017.
 */

@Component("MyFileService")
public class FileService {

    @Autowired
    ConfigsService configService;

    @Autowired
    MobileService mobileService;

 

    @Value("${member.user}")
    String memberUsername;

    @Value("${bo.super.admin.password}")
    String memberPassword;


    public List<UUID> createArrayOfValidFiles(int nrOfValidFiles) {
        return IntStream.range(0, nrOfValidFiles)
                .mapToObj(index -> configService.createFile(extractXAuthTokenFromResponse(mobileService.authUser
                        (new Login(memberUsername, memberPassword))), DtoBuilders.buildFile()).getBody().getId())
                .collect(Collectors.toList());
    }
    


}
