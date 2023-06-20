package com.passioncode.procurementsystem.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.passioncode.procurementsystem.dto.DetailPurchaseOrderDTO;
import com.passioncode.procurementsystem.dto.MaterialInDTO;
import com.passioncode.procurementsystem.dto.ProcurementPlanDTO;
import com.passioncode.procurementsystem.dto.ProgressCheckDTO;
import com.passioncode.procurementsystem.dto.PurchaseOrderDTO;
import com.passioncode.procurementsystem.entity.DetailPurchaseOrder;
import com.passioncode.procurementsystem.entity.MRP;
import com.passioncode.procurementsystem.entity.MaterialIn;
import com.passioncode.procurementsystem.entity.ProcurementPlan;
import com.passioncode.procurementsystem.entity.ProgressCheck;
import com.passioncode.procurementsystem.entity.PurchaseOrder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class ProgressCheckRepositoryTests {

	@Autowired
	ProgressCheckRepository progressCheckRepository;
	
	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	DetailPurchaseOrderRepository detailPurchaseOrderRepository;
	
	@Autowired
	ProcurementPlanRepository procurementPlanRepository;
	
	@Autowired
	MaterialInRepository materialInRepository;
	
	@Autowired
	TransactionDetailRepository transactionDetailRepository;
	
	@Autowired
	MRPRepository mrpRepository;
	
	@Transactional
	@Test
	public void detailPurchaseOrderDTOTest() {
		//발주서 목록 화면
		//DTO로 갖고와서 뿌리기
		
		//발주서 목록은 다 뿌려야 한다
		List<DetailPurchaseOrder> detailList= detailPurchaseOrderRepository.findAll();
		
		List<MaterialInDTO> materialInDTOList= new ArrayList<>();
		MaterialInDTO materialInDTO= null;
		List<ProcurementPlan> ppList= new ArrayList<>();
		List<MaterialIn>miList= new ArrayList<>();
		
		DetailPurchaseOrder detailPurchaseOrder = detailPurchaseOrderRepository.findById(1).get();
	
		
	}
	
	@Transactional
	@Test
	public void getDTOList() {
		//List<DetailPurchaseOrder> optionalDpo= detailPurchaseOrderRepository.findAll();
		//ProcurementPlan pp= procurementPlanRepository.findByDetailPurchaseOrder(optionalDpo.get());
		//log.info("세부구매발주서로 procurementPlan 찾기! " + pp.getDueDate());
		
		List<DetailPurchaseOrder> detailList= detailPurchaseOrderRepository.findAll();
		List<ProgressCheckDTO> progressCheckDTOList = new ArrayList<>();
		
		
		for(int i=0; i<detailList.size(); i++) {
			//progressCheckDTOList.add(entityToDTO(detailList.get(i)));
		}
		
		log.info(">> 목록 보여주세요 >>>"+progressCheckDTOList);
	
	}
	
	@Test
	public void getProgressCheckDTOList() {//발주서 목록 갖고오기
		List<DetailPurchaseOrder> detilList = detailPurchaseOrderRepository.findAll();
		
		List<ProgressCheckDTO> progressCheckDTOlList = new ArrayList<>();
		for(int i=0;i<detilList.size();i++) {
			//progressCheckDTOlList.add(entityToDTO(detilList.get(i)));
					}
		
	}
	
	
	@Transactional
	@Test
	public void entityToDTO() {//이게 가져와 지는 것
		//발주서 목록 갖고오기
		//ProcurementPlan procurementPlan = procurementPlanRepository.findById(1).get();
		//DetailPurchaseOrder detailPurchaseOrder2 = detailPurchaseOrderRepository.findById(1).get();
		//1번 세구부매 발주서로 테스트
		
		//세부구매발주서 -> 조달계획 -> ..
		DetailPurchaseOrder detailPO= detailPurchaseOrderRepository.findById(1).get();
		ProcurementPlan procurementPlan= procurementPlanRepository.findByDetailPurchaseOrder(detailPO);
		MaterialIn maIn = materialInRepository.findByDetailPurchaseOrder(detailPO);
		
		
		  ProgressCheckDTO progressCheckDTO = ProgressCheckDTO.builder()
		.companyName(procurementPlan.getContract().getCompany().getName())
		  .purchaseOrderCode(procurementPlan.getDetailPurchaseOrder().getCode())
		  .orderAmount(procurementPlan.getDetailPurchaseOrder().getAmount())
		  .dueDate(procurementPlan.getDueDate())
		  .materialName(procurementPlan.getContract().getMaterial().getName())
		  .orderAmount(procurementPlan.getAmount())
		  .unitPrice(procurementPlan.getContract().getUnitPrice())
		  .diliveryPercent("미등록")
		  .inspectionComplete("미완료")
		  .purchaseOrderDeadlineStatus(null)
		  .nextCheckDate(null)
		  .build();
		  
		  log.info(">> 목록 보여주세요 >>>"+progressCheckDTO); 
		 		
	}
		
	@Transactional
	@Test
	public void DTOTest() {//자재 입고 화면 가져온 것
		DetailPurchaseOrder detailPurchaseOrder2= detailPurchaseOrderRepository.findById(1).get();
		ProcurementPlan pp= procurementPlanRepository.findByDetailPurchaseOrder(detailPurchaseOrder2);
		MaterialIn mi= materialInRepository.findByDetailPurchaseOrder(detailPurchaseOrder2);
		log.info("pp는 어케 찍히나 " + pp);
		
		MaterialInDTO materialInDTO= MaterialInDTO.builder().no(detailPurchaseOrder2.getPurchaseOrder().getNo()).code(detailPurchaseOrder2.getCode())
				.dueDate(pp.getDueDate()).materialCode(pp.getMrp().getMaterial().getCode())
				.materialName(pp.getMrp().getMaterial().getName())
				.amount(pp.getDetailPurchaseOrder().getAmount()).status(true).transactionStatus("발행 예정")
				.inDate(mi.getDate()).build();
		
		log.info("DTO 하나는 어케 가져오는거죠 " + materialInDTO);
	}
	
	
	
//	public String existMInStatus(DetailPurchaseOrder detailPO) {//조달 계획을 가져와야 하기 때문에 리턴이 필요하다. 
//		String detailStatus = null;
//		if (detailPO.getCode() == null) {//입고상태 존재X
//			detailStatus = "미완료";
//			
//		} else {
//			detailStatus = "완료";
//		}
//		
//		return detailStatus;
//	}
	
	public LocalDateTime extistPurchaseOrderDate(ProcurementPlan procurementPlan) {//조달 계획을 가져와야 하기 때문에 리턴이 필요하다. 
		//세부 구매발주서에 있는 발주 번호 갖고 오기
		LocalDateTime detailPurchaseOrderDate = null;
		if(procurementPlan.getDetailPurchaseOrder() != null) {
			detailPurchaseOrderDate=procurementPlan.getDetailPurchaseOrder().getDate();
		}
		
		return detailPurchaseOrderDate;
		
	}
	
	@Test
	public void showPercent() {//해당하는 것의 진척 평가를 갖고오기
		Optional<ProgressCheck> list = progressCheckRepository.findById(1);
		//ProgressCheck pcheckList = pcheckList.get();
		List<ProgressCheck> list2 = progressCheckRepository.findAll();
		List<ProgressCheckDTO> progressCheckDTOlList = new ArrayList<>();
		List<DetailPurchaseOrder> dp = detailPurchaseOrderRepository.findAll();
		Optional<DetailPurchaseOrder> dp2 = detailPurchaseOrderRepository.findById(1);
		Integer aa=  list.get().getRate();
		LocalDateTime bb = list.get().getDate();
		String cc = list.get().getEtc();
		Integer dd = list.get().getDetailPurchaseOrder().getCode();//진척검수에서 가져온 발주코드
		log.info("*******이건 진척검수에서 가져온 발주코드"+dd);
		
		progressCheckDTOlList.add(null);
		log.info("DTO보기>>"+progressCheckDTOlList);
		Optional<ProgressCheck> pcL= progressCheckRepository.findById(2);
		
		log.info("조달계획 가져오기>>"+pcL);
		log.info("조달계획 1번 퍼센트 가져오기>>"+aa);
		log.info("조달계획 1번 진척검수일 가져오기>>"+bb);
		log.info("조달계획 1번 기타사항 가져오기>>"+cc);
		log.info("조달계획 1번 가져오기>>"+list);
		log.info("조달계획 전부 다 가져오기>>"+list2);
		
		Integer nono= dp2.get().getCode();
		if(nono==dd) {//만약 발주코드와 진척검수 발주 코드가 같다면
			Integer aa2=  list.get().getRate();
			
			log.info("이건 해당하는 납기 진도율"+aa2);			
		}
	}
	
	@Test
	public void getCompateList() {//여기에서 for이용해서 비교하기
		Optional<DetailPurchaseOrder> list = detailPurchaseOrderRepository.findById(1);
		List<DetailPurchaseOrder> detailList = detailPurchaseOrderRepository.findAll();
		//ProgressCheck pcheckList = pcheckList.get();
		List<PurchaseOrder> list2 = purchaseOrderRepository.findAll();
		List<ProgressCheckDTO> pcDTOList = new ArrayList<>();
		Optional<MaterialIn> ma = materialInRepository.findById(1);
		log.info("발주서 마감: "+ma.get().getStatus());//true라고 나옴
		if(ma.get().getStatus()==false) {
			log.info("존재하지 않으면");
			
		}else {
			log.info("발주서 마감이라면");
			
		}
		for(int i=0;i<detailList.size();i++) {
			
		}
		log.info("발주서 가져오기>>"+list2);
		
	}
	
	@Test
	public void existMIn() {
		String inStatus = null;
		Optional<MaterialIn> ma = materialInRepository.findById(1);
		log.info("발주서 마감: "+ma.get().getStatus());//true라고 나옴
		DetailPurchaseOrder detailPO= detailPurchaseOrderRepository.findById(1).get();
		ProcurementPlan procurementPlan= procurementPlanRepository.findByDetailPurchaseOrder(detailPO);
		MaterialIn maIn = materialInRepository.findByDetailPurchaseOrder(detailPO);
		log.info("뭐라고 출력되는지? 입고>> "+maIn);
		//MaterialIn(code=1, status=true, date=2023-06-16T11:45:17, transactionStatus=발행 완료)
		log.info("발주서 마감 상태**>>"+maIn.getStatus());
		if(ma.get().getStatus()==false) {//발주서가 존재하면
			log.info("발주서 마감이라면");
			inStatus = "미완료";
		}else {
			log.info("존재하고 있음");
			inStatus = "완료";
			
		}
		log.info("발주서 마감 상태: "+inStatus);
		//return inStatus;
	}
	
	@Transactional
	@Test
	public String existMIn2(DetailPurchaseOrder dp) {
		//발주서 마감 상태 잘 보내줌
		String inStatus = null;
		Optional<MaterialIn> ma = materialInRepository.findById(1);
		log.info("발주서 마감: "+ma.get().getStatus());//true라고 나옴
		DetailPurchaseOrder detailPO= detailPurchaseOrderRepository.findById(1).get();
		ProcurementPlan procurementPlan= procurementPlanRepository.findByDetailPurchaseOrder(detailPO);
		MaterialIn maIn = materialInRepository.findByDetailPurchaseOrder(dp);
		log.info("뭐라고 출력되는지? 입고>> "+maIn);
		//MaterialIn(code=1, status=true, date=2023-06-16T11:45:17, transactionStatus=발행 완료)
		if(maIn!=null) {
			
		if(ma.get().getStatus()==false) {//발주서가 존재하면
			log.info("발주서 마감이라면");
			inStatus = "미완료";
		}else {
			log.info("존재하고 있음");
			inStatus = "완료";
		}
		}else {
			inStatus = "미완료";
			
		}
		log.info("발주서 마감 상태: "+inStatus);
		return inStatus;
	}
	@Test
	public void getPercent() {//해당하는 발주번호 찾아서 진척검수 보여주기
		DetailPurchaseOrder detailPO= detailPurchaseOrderRepository.findById(1).get();
		//Optional<ProgressCheck> pgCheck = progressCheckRepository.findById(detailPO.getCode());
		ProgressCheck pg = progressCheckRepository.findByDetailPurchaseOrder(detailPO);
		
		log.info("납기 진도율 퍼센트"+pg.getRate());//납기 진도율
		String aa= pg.getRate()+"%";
		log.info(aa);
		log.info("");
	}
	//여러개 등록되어 있을 경우 마지막의 것으로 가져오는 조건 
	//추가하기
	
	@Test
	public void nextCheckDate() {
		DetailPurchaseOrder detailPO= detailPurchaseOrderRepository.findById(2).get();
		ProgressCheck pg = progressCheckRepository.findByDetailPurchaseOrder(detailPO);
		log.info(pg.getDate());
		}
	
}
	