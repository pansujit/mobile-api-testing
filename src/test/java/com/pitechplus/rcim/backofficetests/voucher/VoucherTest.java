package com.pitechplus.rcim.backofficetests.voucher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.BeanUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherGroupEditDto;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherGroupViewDto;
import com.pitectplus.rcim.backoffice.dto.voucher.VoucherViewDto;
import com.rits.cloning.Cloner;

public class VoucherTest extends BackendAbstract {
	private VoucherGroupEditDto voucherGroupEditDto;
	private VoucherGroupViewDto voucherGroupViewDto;
	
	@BeforeClass
	public void beforeClassCreateVoucher() throws InterruptedException {
		
		voucherGroupEditDto= DtoBuilders.createVoucherGroupDto(rcimTestData.getAdminCompanyId().toString());
		voucherGroupViewDto=voucherService.createVoucherGroup(rcimTestData.getSuperAdminToken(), 
				voucherGroupEditDto).getBody();
		Thread.sleep(4000);
	}
	@Test(description="This test will create a voucher group")
	public void createAVoucherGroupTest() throws IOException {
	
		VoucherGroupViewDto createAVoucherGroup = new VoucherGroupViewDto();
		BeanUtils.copyProperties(voucherGroupEditDto, createAVoucherGroup);
		createAVoucherGroup.setVouchers(voucherGroupViewDto.getVouchers());
		createAVoucherGroup.setId(voucherGroupViewDto.getId());
		createAVoucherGroup.setType(voucherGroupViewDto.getType());
		assertThat("The voucher is not looks like that we have created now",
				voucherGroupViewDto,is(createAVoucherGroup));	
	}
	
	@Test(description="This test will search for all the voucher groups in the given super company")
	public void searchAllVoucherGroupsTest() {
		VoucherGroupViewDto[] x=voucherService.searchAllVoucherGroup(rcimTestData.getSuperAdminToken()).getBody();
		Cloner cloner= new Cloner();
		VoucherGroupViewDto y=cloner.deepClone(voucherGroupViewDto);
		y.setRules(null);
		y.setVouchers(null);
		assertThat("Our voucher group is not in the list",Arrays.asList(x),
				hasItem(y));		
	}
	@Test(description="This test will search for the given voucher group")
	public void searchAVoucherGroupTest() {
		if(voucherGroupViewDto.getId()!=null) {
			VoucherGroupViewDto x=voucherService.searchAVoucherGroup(rcimTestData.getSuperAdminToken(),voucherGroupViewDto.getId()).getBody();
			x.getVouchers()[0].setSuperCompanyId(null);
			Cloner cloner= new Cloner();
			VoucherGroupViewDto y=cloner.deepClone(voucherGroupViewDto);
			y.getVouchers()[0].setSuperCompanyId(null);
			assertThat("The voucher group that we got is not created recently",x,is(y));
		}
		else {
			System.out.println("the test is skippingme");	
		}
	}
	@Test(description="This test will search for the voucher for the given voucher id ")
	public void searchAVoucherTest() {
		VoucherViewDto x=voucherService.searchAVoucher(rcimTestData.getSuperAdminToken(),
				voucherGroupViewDto.getVouchers()[0].getId()).getBody();
		x.setSuperCompanyId(null);
		Cloner cloner= new Cloner();
		VoucherGroupViewDto y=cloner.deepClone(voucherGroupViewDto);
		VoucherViewDto newVoucher =y.getVouchers()[0];
		newVoucher.setSuperCompanyId(null);
		assertThat("Our voucher is not in the list",x,is(newVoucher));		
	}
	
}
