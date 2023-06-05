package com.passioncode.procurementsystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.passioncode.procurementsystem.entity.DetailPurchaseOrder;
import com.passioncode.procurementsystem.entity.PurchaseOrder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class DetailPurchaseOrderRepositoryTests {
	
	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	DetailPurchaseOrderRepository detailPurchaseOrderRepository;
	
	@Test
	public void getList() {
		Optional<DetailPurchaseOrder> list = detailPurchaseOrderRepository.findById(3);
		
		DetailPurchaseOrder detail = list.get();
		
		List<DetailPurchaseOrder> list2 = detailPurchaseOrderRepository.findAll();
		
		log.info(">>>>>>>>>>"+detail);
		log.info(">>>>>>>>>>"+list);
		log.info("여러개>>>>>>>>>>"+list2);
		
	}
	
	@Test
	public void InsertTest() {	//발주 코드 생성 테스트
		PurchaseOrder purchaseOrder = new PurchaseOrder(101);
		//Integer code, Integer amount, LocalDateTime date, Integer purchaseOrderNo		
		
		DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(null, 450, LocalDateTime.now(), purchaseOrder);
		detailPurchaseOrderRepository.save(detailPurchaseOrder);
		
	}
	

}
