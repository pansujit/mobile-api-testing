package com.pitechplus.rcim.backofficetests.customization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.dto.customizations.ResultSubscription;

public class CustomFieldTest extends BackendAbstract {

	@Test(description="This test will get the custom field of the  given super company in "
			+ "subscription")
	public void getCustomFieldTest() throws JsonParseException, JsonMappingException, IOException {

		ResultSubscription x=customizationService.abcd(rcimTestData.getSuperAdminToken(),
				rcimTestData.getCustomSuperCompanyId()).getBody();
		HashMap<String, String> newMap= new HashMap<String, String>();
		for(int i=0;i<x.getSUBSCRIPTION().size();i++) {
			newMap.put(x.getSUBSCRIPTION().get(i).getFieldType(), x.getSUBSCRIPTION().get(i).getId());
		}
	assertThat("The company may not contains the custom fields",newMap.size(),greaterThan(0));

	}

}
