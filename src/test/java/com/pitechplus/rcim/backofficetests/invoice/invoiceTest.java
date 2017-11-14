package com.pitechplus.rcim.backofficetests.invoice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.springframework.http.ResponseEntity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pitechplus.rcim.BackendAbstract;
import com.pitechplus.rcim.backoffice.data.enums.InvoiceStatus;
import com.pitechplus.rcim.backoffice.data.enums.InvoiceType;
import com.pitechplus.rcim.backoffice.dto.invoice.InvoiceResultDto;
import com.pitechplus.rcim.backoffice.dto.invoice.MemberInvoicePagedSearchDto;
import com.pitechplus.rcim.backoffice.utils.builders.DtoBuilders;
import com.rits.cloning.Cloner;

public class invoiceTest extends BackendAbstract {
	
	@Test(dataProvider="invoiceSearchData",description="This test verifies that the searching invoice  of a user")
	public void invoiceSearchTest(MemberInvoicePagedSearchDto searchData) {
		
		ResponseEntity<InvoiceResultDto> responseData=invoiceService.searchInvoice(rcimTestData.getMemberToken(), searchData);
		assertThat("The response code is not as we expected",responseData.getStatusCodeValue(),is(200));
		assertThat("The page info is not same as we provided",responseData.getBody().getPageInfo().getPageSize(),is(50));
		assertThat("The result is null for search invoice",responseData.getBody().getResults().size(),is(notNullValue()));

	}
	
	/**
	 * This data provider used for the search invoice in the mobile booking 
	 * @return object [][]
	 */
	@DataProvider
	public Object[][] invoiceSearchData() {
		MemberInvoicePagedSearchDto baseInvoiceSearch=DtoBuilders.invoiceSearchBuilder();
		Cloner cloner= new Cloner();
		MemberInvoicePagedSearchDto allNull=cloner.deepClone(baseInvoiceSearch);
		MemberInvoicePagedSearchDto monthOnly=cloner.deepClone(baseInvoiceSearch);
		monthOnly.getQuery().setMonth(11);
		MemberInvoicePagedSearchDto yearOnly=cloner.deepClone(baseInvoiceSearch);
		yearOnly.getQuery().setYear(2017);
		MemberInvoicePagedSearchDto ALLDATA=cloner.deepClone(baseInvoiceSearch);
		ALLDATA.getQuery().setStatus(InvoiceStatus.PAID);
		ALLDATA.getQuery().setType(InvoiceType.MISCELLANEOUS);
		ALLDATA.getQuery().setMonth(11);
		ALLDATA.getQuery().setYear(2017);
		ALLDATA.getQuery().setMinCreatedDate("2017-11-07T16:35+01:00");

		return new Object[][] {
			{allNull},
			{monthOnly},
			{yearOnly},
			{ALLDATA},
		};
	}

}
