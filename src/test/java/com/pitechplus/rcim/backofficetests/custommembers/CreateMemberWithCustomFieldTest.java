package com.pitechplus.rcim.backofficetests.custommembers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.dto.member.CustomMemberDto;
import com.pitechplus.rcim.backoffice.dto.member.MemberCustomValues;
import com.pitechplus.rcim.backoffice.dto.member.MemberDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitechplus.rcim.backoffice.utils.mappers.Mapper;
import com.pitechplus.rcim.nissan.be.nissanutils.nissanmappers.MemberMapper;

public class CreateMemberWithCustomFieldTest extends BackendAbstract {
	
	private String fileId;
	private String textId;
	private List<MemberCustomValues> customData= new ArrayList<MemberCustomValues>();
	private CustomMemberDto customMemberDto;
	@BeforeClass
	public void CustomFileData() {
		fileId="f477614f-cf47-4557-9147-6d157592c767";
		textId="466c430e-a68b-4474-af34-0a7725f70792";	
		List<UUID> y=commonUtils.createArrayOfValidFiles(2);
		//List<UUID> y=nissanUtils.createArrayOfValidFiles();
		System.out.println("yyyy"+y.size());
		customData.add(MemberCustomValues.builder().companyCustomFieldId(fileId).value(y.get(1).toString()).build());
		customData.add(MemberCustomValues.builder().companyCustomFieldId(textId).value("hello").build());
		customMemberDto=DtoBuilders.customBuildMemberCreateDto(rcimTestData.getCustomSuperCompanyId(), y.get(0), customData);
		
	}
	
	@Test(description="This test will create a member with custom field")
	public void createMemberWithCustomFieldTest() {
		
		MemberDto responseMemberDto=memberService.createMemberWithCustomField(rcimTestData.getMemberToken(), customMemberDto).getBody();
		MemberDto expectedMemberDto=Mapper.mapMemberCreateDtoToMemberDto(customMemberDto, companyService
                .getSuperCompany(rcimTestData.getSuperAdminToken(), 
                		UUID.fromString(rcimTestData.getCustomSuperCompanyId())).getBody());
		expectedMemberDto.getPhoneNumber().setPhoneNumberName(null);
		expectedMemberDto.getCompany().setContract(null);
		 assertThat("Information given on response to add member does not reflect request !",
	                responseMemberDto, is(expectedMemberDto));	
	}

	

}
