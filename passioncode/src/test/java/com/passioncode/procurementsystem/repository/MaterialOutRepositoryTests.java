package com.passioncode.procurementsystem.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.results.complete.ModelPartReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.passioncode.procurementsystem.dto.MaterialOutDTO;
import com.passioncode.procurementsystem.entity.MRP;
import com.passioncode.procurementsystem.entity.MaterialOut;
import com.passioncode.procurementsystem.entity.ProcurementPlan;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class MaterialOutRepositoryTests {
	
	@Autowired
	MaterialOutRepository materialOutRepository;
	
	@Autowired
	ProcurementPlanRepository procurementPlanRepository;
	
	@Autowired
	MRPRepository mrpRepository;
	
	@Transactional
	@Test
	public void findByMRPTest() {
		MRP mrp = mrpRepository.findById(1).get();
		log.info("출고 찍히는거 보자 : "+materialOutRepository.findByMrp(mrp));
	}
	
	@Test
	public void InsertTest() {
		//출고코드, 출고상태, 출고일, 자재소요계획코드(외래키)(MRP)
		MRP mrp = mrpRepository.findById(1).get();
		
		MaterialOut materialOut = MaterialOut.builder().status(1).mrp(mrp).build();
		
		materialOutRepository.save(materialOut);
		
	}
	
	@Transactional
	@Test
	public void DTOListTest() {
		List<MRP> mrpList= mrpRepository.findAll();
		List<ProcurementPlan> ppList= new ArrayList<>();
				
		for(int i=0; i<mrpList.size(); i++) {
			if(procurementPlanRepository.existsByMrp(mrpList.get(i))) {
				ppList.add(procurementPlanRepository.findByMrp(mrpList.get(i)));				
			}
		}
		
		//log.info("ppList 한번 보자 >>> " + ppList + ", 사이즈는 >>> " + ppList.size());
		
		List<MaterialOutDTO> moDTOList= new ArrayList<>();
		MaterialOutDTO moDTO= null;
		List<MaterialOut> moList= materialOutRepository.findAll();
		log.info("moList 한번 보자 >>> " + moList + ", 사이즈는 >>> " + moList.size());
		
		for(int i=0; i<ppList.size(); i++) {
		log.info("ppList 완료일 한번 보자 >>> " + ppList.get(i).getCompletionDate());
			//세부구매발주서 등록 +  완료일(입고일) 등록 -> 출고 리스트(출고 상태 0(버튼))
			if(ppList.get(i).getDetailPurchaseOrder() != null && ppList.get(i).getCompletionDate() != null) {
				//출고 엔티티에 존재 O
				if(materialOutRepository.existsByMrp(ppList.get(i).getMrp())){
					moDTO= MaterialOutDTO.builder().dpoCode(ppList.get(i).getDetailPurchaseOrder().getCode())
							.mrpDate(ppList.get(i).getMrp().getDate()).materialCode(ppList.get(i).getMrp().getMaterial().getCode())
							.materialName(ppList.get(i).getMrp().getMaterial().getName())
							.process(ppList.get(i).getMrp().getProcess()).mrpAmount(ppList.get(i).getMrp().getAmount()).outStatus("1").build();			
					moDTOList.add(moDTO);		
				//출고 엔티티에 존재 X	
				}else {
					moDTO= MaterialOutDTO.builder().dpoCode(ppList.get(i).getDetailPurchaseOrder().getCode())
							.mrpDate(ppList.get(i).getMrp().getDate()).materialCode(ppList.get(i).getMrp().getMaterial().getCode())
							.materialName(ppList.get(i).getMrp().getMaterial().getName())
							.process(ppList.get(i).getMrp().getProcess()).mrpAmount(ppList.get(i).getMrp().getAmount()).outStatus("0").build();			
					moDTOList.add(moDTO);			
				}
				
			}
		}
		log.info("moDTOList >>> " + moDTOList + ", 사이즈는 >>> " + moDTOList.size());	
	}
	
}
