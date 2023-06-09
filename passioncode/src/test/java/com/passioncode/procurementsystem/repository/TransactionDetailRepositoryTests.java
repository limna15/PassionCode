package com.passioncode.procurementsystem.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.passioncode.procurementsystem.dto.TransactionDetailDTO;
import com.passioncode.procurementsystem.entity.Company;
import com.passioncode.procurementsystem.entity.DetailPurchaseOrder;
import com.passioncode.procurementsystem.entity.MaterialIn;
import com.passioncode.procurementsystem.entity.ProcurementPlan;
import com.passioncode.procurementsystem.entity.TransactionDetail;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TransactionDetailRepositoryTests {
	
	@Autowired
	TransactionDetailRepository transactionDetailRepository;
	
	@Autowired
	ProcurementPlanRepository procurementPlanRepository;
	
	@Autowired
	DetailPurchaseOrderRepository detailPurchaseOrderRepository;
	
	@Autowired
	MaterialInRepository materialInRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Transactional
	@Test
	public void DTOTest() {
		//거래명서세번호, 발주회사(=우리회사), 발주서번호, 납기일자(=입고일), 사업자등록번호, 상호, CEO, 주소, 담당자, 연락처, 품목코드, 품목명, 수량, 단가
		DetailPurchaseOrder detailPurchaseOrder= detailPurchaseOrderRepository.findById(1).get();
		//ProcurementPlan pp= procurementPlanRepository.findByDetailPurchaseOrder(detailPurchaseOrder);
		MaterialIn materialIn= materialInRepository.findByDetailPurchaseOrder(detailPurchaseOrder);
		
		Company ourCompany= companyRepository.findById("777-77-77777").get();
		
		TransactionDetail transactionDetail= transactionDetailRepository.findById(1).get();
//		TransactionDetailDTO transactionDetailDTO= TransactionDetailDTO.builder().no(transactionDetail.getNo()).company(ourCompany.getName()).purchaseOrderNo(transactionDetail.getPurchaseOrder().getNo())
//												.date(materialIn.getDate()).companyNo(pp.getContract().getCompany().getNo())
//												.companyName(pp.getContract().getCompany().getName()).CEO(pp.getContract().getCompany().getCeo())
//												.companyAddress(pp.getContract().getCompany().getAddress()).manager(pp.getContract().getCompany().getManager())
//												.managerTel(pp.getContract().getCompany().getManagerTel())
//												.materialCode(pp.getContract().getMaterial().getCode()).materialName(pp.getContract().getMaterial().getName())
//												.amount(pp.getDetailPurchaseOrder().getAmount()).unitPrice(pp.getContract().getUnitPrice()).build();
		
//		log.info("transactionDetailDTO 어떻게 가지고 오는지 보자>>> " + transactionDetailDTO);
	}

}
