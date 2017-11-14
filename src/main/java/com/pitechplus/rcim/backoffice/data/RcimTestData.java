package com.pitechplus.rcim.backoffice.data;

import com.pitechplus.rcim.backoffice.dto.backuser.BackUser;
import com.pitechplus.rcim.backoffice.dto.backuser.Login;
import com.pitechplus.rcim.backoffice.dto.color.ColorDto;
import com.pitechplus.rcim.backoffice.dto.common.FileDto;
import com.pitechplus.rcim.backoffice.dto.company.ParkingDto;
import com.pitechplus.rcim.backoffice.dto.company.SiteDto;
import com.pitechplus.rcim.backoffice.dto.supercompany.SuperCompanyDto;
import com.pitechplus.rcim.backoffice.dto.vehicle.CategoryDto;
import com.pitechplus.rcim.backoffice.dto.vehicle.VehicleDto;
import com.pitechplus.rcim.backoffice.service.*;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.pitechplus.rcim.backoffice.utils.DataExtractors.extractXAuthTokenFromResponse;

/**
 * Created by dgliga on 31.05.2017.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Component("testData")
public class RcimTestData {


	@Autowired
	protected BackUserService backUserService;

	@Autowired
	protected CompanyService companyService;

	@Autowired
	protected ColorService colorService;

	@Autowired
	protected ConfigsService configsService;

	@Autowired
	protected ParkingService parkingService;

	@Autowired
	protected SiteService siteService;

	@Autowired
	protected VehicleService vehicleService;

	@Autowired
	protected MemberService memberService;

	@Autowired
	protected MobileService mobileService;


	@Value("${bo.admin.username}")
	String boAdminUsername;

	@Value("${bo.admin.password}")
	String boAdminPassword;

	@Value("${bo.super.admin.username}")
	String boSuperAdminUsername;

	@Value("${bo.super.admin.password}")
	String boSuperAdminPassword;

	@Value("${super.company.booking.id}")
	String bookingSuperCompanyId;

	@Value("${automation.site.id}")
	String automationSiteId;

	@Value("${automation.vehicle.id}")
	String automationVehicleId;

	@Value("${automation.parking.id}")
	String automationParkingId;

	@Value("${custom.super.company.id}")
	String customSuperCompanyId;
	//added for member
	@Value("${member.user}")
	String memberLoginEmail;

	@Value("${vehicle.id}")
	String memberVehicleId;

	@Value("${member.parking.id}")
	String memberParkingId;

	// end here member
	String superAdminToken;
	UUID configurationId;
	String groupTemplate;
	UUID addedSuperCompanyId;
	String adminToken;
	UUID adminSuperCompanyId;
	UUID adminCompanyId;
	SuperCompanyDto superCompanyDto;
	SiteDto siteDto;
	ParkingDto parkingDto;
	CategoryDto categoryDto;

	FileDto fileDto;
	VehicleDto automationVehicle;
	ParkingDto automationParking;
	String memberPassword;
	String memberLogin;
	String memberToken;
	ParkingDto memberAutomationParking;
	VehicleDto memberAutomationVehicle;
	String memberId;

	VehicleDto memberAutomationVehicle1;
	@PostConstruct
	public void initRcimTestData() throws Exception {

		ResponseEntity<BackUser> memberLogin = mobileService.authUser(new Login(memberLoginEmail, boSuperAdminPassword));
		this.memberToken = extractXAuthTokenFromResponse(memberLogin);
		this.memberId=memberLogin.getBody().getId().toString();

				this.superAdminToken = extractXAuthTokenFromResponse(backUserService.authUser(new Login(boSuperAdminUsername, boSuperAdminPassword)));

		List<ColorDto> colorDtos =  Arrays.asList(colorService.getAllColor(this.superAdminToken).getBody());

		this.memberAutomationVehicle = vehicleService.getVehicle(this.getSuperAdminToken(),
				UUID.fromString(this.getMemberVehicleId())).getBody();

		this.memberAutomationParking = parkingService.getParking(this.superAdminToken,
				UUID.fromString(this.getMemberParkingId())).getBody();

		this.memberAutomationVehicle1 = vehicleService.getVehicle1(this.getSuperAdminToken(),
				UUID.fromString(this.getMemberVehicleId())).getBody();       
	}



}