package com.passioncode.procurementsystem.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.passioncode.procurementsystem.dto.DetailPublishDTO;
import com.passioncode.procurementsystem.dto.DetailPurchaseOrderDTO;
import com.passioncode.procurementsystem.entity.DetailPurchaseOrder;
import com.passioncode.procurementsystem.entity.ProcurementPlan;
import com.passioncode.procurementsystem.entity.PurchaseOrder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class DetailPurchaseOrderRepositoryTests {

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	DetailPurchaseOrderRepository detailPurchaseOrderRepository;

	@Autowired
	ProcurementPlanRepository procurementPlanRepository;

	@Autowired
	MRPRepository mrpRepository;

	@Autowired
	ContractRepository contractRepository;

	@Transactional
	@Commit
	@Test
	public void updatePp2() {//구매 발주서를 업데이트 시키기
		//조달계획 번호를 받으면 
		//빌드를 해 줘야 한다. 
		//세부의 3가지를 넣어서 + 구매발주서
		log.info(procurementPlanRepository.findById(8).get().getAmount());
		ProcurementPlan pp = procurementPlanRepository.findById(8).get();
		
		Integer amount = procurementPlanRepository.findById(6).get().getAmount();
		PurchaseOrder po = PurchaseOrder.builder().build();
		purchaseOrderRepository.save(po);//발주서 번호 생성 후 저장
		DetailPurchaseOrder detailPurchaseOrder = DetailPurchaseOrder.builder()
				.amount(amount).date(LocalDateTime.now())
				.purchaseOrder(po).build();
		//세부구매발주서 저장
		
		detailPurchaseOrderRepository.save(detailPurchaseOrder);
		
		procurementPlanRepository.save(pp);
		//총 9개
		ProcurementPlan pp2 = ProcurementPlan.builder()
				.amount(pp.getAmount())
				.code(pp.getCode())
				.completionDate(pp.getCompletionDate())
				.contract(pp.getContract())
				.detailPurchaseOrder(detailPurchaseOrder)
				.dueDate(pp.getDueDate())
				.minimumOrderDate(pp.getMinimumOrderDate())
				.mrp(pp.getMrp())
				.registerDate(pp.getRegisterDate()).build();
				
		procurementPlanRepository.save(pp2);
		
		log.info("조달계획8번  >>" + pp2);
		log.info("22288888888>>" + detailPurchaseOrder);
		
	}	
	
	
	// 앞에 들어가는걸 생성 뒤 넣어주기
	// 1. 구매 발주서 -> 2. 세부 구매발주서 -> 3. 조달 계획코드에 들어감
	// DetailPurchaseOrder에 저장
	@Transactional
	@Commit
	@Test
	public void updatePp() {
		//조달품목 리스트를 다 불러와서 널값이면 확인해서 저장해 주기
		
		List<Object[]> result = detailPurchaseOrderRepository.myDetailList(17);// 조달계획 17번
		for (Object[] arr : result) {// 이 아래가 변환하는 것 이다.
			log.info("222>>" + Arrays.toString(arr));
			// [2, (주)경도전자, 2023-06-10, 200, CNa0001, Bolt1, 100]
			// ==>조달 계획 번호만 가져오면 된다.
			//PurchaseOrder purchaseOrder = new PurchaseOrder(null);// 구매 발주서 발행
			//DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(purchaseOrder.getNo(),((Integer) arr[7]) - ((Integer) arr[6]), LocalDateTime.now(), purchaseOrder);
			PurchaseOrder po = PurchaseOrder.builder().build();
			DetailPurchaseOrder detailPurchaseOrder = DetailPurchaseOrder.builder().date(LocalDateTime.now()).amount(((Integer) arr[6])).purchaseOrder(po).build();
			//detailPurchaseOrderRepository.save(detailPurchaseOrder);
			//detailPurchaseOrderRepository.myUpdate(detailPurchaseOrder.getCode(), ((Integer) arr[0]));// 앞 발주번호, 뒤 조달 코드
			purchaseOrderRepository.save(po);
			detailPurchaseOrderRepository.save(detailPurchaseOrder);
			//purchaseOrderRepository.save(purchaseOrder);
			log.info(detailPurchaseOrder);
		
		ProcurementPlan pp = procurementPlanRepository.findById((Integer)arr[0]).get();//특정한 계획 불러오기
		log.info("조달계획>>>>>저장되는 것?"+pp);
		//DetailPurchaseOrder dp = detailPurchaseOrderRepository.findById(15).get();
		//log.info("dp>>"+dp);
		
		//ProcurementPlanDTO ppDTO = ProcurementPlanDTO.builder().
		
		ProcurementPlan pp2 = ProcurementPlan.builder().code(pp.getCode()).amount(pp.getAmount()).dueDate(pp.getDueDate())
				.minimumOrderDate(pp.getMinimumOrderDate()).registerDate(pp.getRegisterDate())
				.completionDate(pp.getCompletionDate()).mrp(pp.getMrp()).contract(pp.getContract())
				.detailPurchaseOrder(detailPurchaseOrder).build();
		
		log.info("pp2>>>>>>>"+pp2);
		procurementPlanRepository.save(pp2);
		}
	}
	
	@Transactional
	@Test
	public void publish2() { // 발주서 번호 생성
		// *******발주서 업데이트 빼고 성공
		List<Object[]> result = detailPurchaseOrderRepository.myDetailList(1);// 조달계획 2번
		for (Object[] arr : result) {// 이 아래가 변환하는 것 이다.
			log.info("222>>" + Arrays.toString(arr));
			// [2, (주)경도전자, 2023-06-10, 200, CNa0001, Bolt1, 100]
			// ==>조달 계획 번호만 가져오면 된다.
			//PurchaseOrder purchaseOrder = new PurchaseOrder(null);// 구매 발주서 발행
			//DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(purchaseOrder.getNo(),((Integer) arr[7]) - ((Integer) arr[6]), LocalDateTime.now(), purchaseOrder);
			PurchaseOrder po = PurchaseOrder.builder().build();
			DetailPurchaseOrder detailPurchaseOrder = DetailPurchaseOrder.builder().date(LocalDateTime.now()).amount(((Integer) arr[6])).purchaseOrder(po).build();
			//detailPurchaseOrderRepository.save(detailPurchaseOrder);
			detailPurchaseOrderRepository.myUpdate(detailPurchaseOrder.getCode(), ((Integer) arr[0]));// 앞 발주번호, 뒤 조달 코드
			purchaseOrderRepository.save(po);
			detailPurchaseOrderRepository.save(detailPurchaseOrder);
			//purchaseOrderRepository.save(purchaseOrder);
			log.info(detailPurchaseOrder);
		}

	}

	@Test
	public void publish() { // 발주서 번호 생성
		// ********잘 만들어진다.
		List<Object[]> result = detailPurchaseOrderRepository.myDetailList(2);// 조달계획 2번

		// 아래 리스트에 더하기
		List<DetailPublishDTO> list = new ArrayList<>();

		for (Object[] arr : result) {// 이 아래가 변환하는 것 이다.
			log.info("222>>" + Arrays.toString(arr));
			// dto 데이터DetailPublishDTO(pono=8, pocode=8, ppcode=2, cname=(주)경도전자,
			// due_date=2023-06-10, mcode=CNa0001, mname=Bolt1, unit_price=200,
			// supply_price=20000,
			// purchaseOrderDate=2023-06-14T20:23:29.881289800, ppamount=100)

			// [2, (주)경도전자, 2023-06-10, 200, CNa0001, Bolt1, 100]
			// ==>조달 계획 번호만 가져오면 된다.
			DetailPublishDTO dto = new DetailPublishDTO();
			PurchaseOrder purchaseOrder = new PurchaseOrder(null);// 구매 발주서 발행
			DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(null,
					((Integer) arr[7]), LocalDateTime.now(), purchaseOrder);
			log.info("저장 되어라==>22" + purchaseOrder);
			log.info("저장 되어라==>" + detailPurchaseOrder);

			dto.setPpcode((Integer) arr[0]);
			dto.setCname((String) arr[1]);
			dto.setDue_date((Date) arr[2]);
			dto.setUnit_price((Integer) arr[3]);
			dto.setMcode((String) arr[4]);
			dto.setMname((String) arr[5]);
			dto.setPpamount(((Integer) arr[6]));// 필요수량 
			dto.setPurchaseOrderDate(LocalDateTime.now());
			dto.setPono(detailPurchaseOrderRepository.findMaxOrderNo());
			dto.setPocode(detailPurchaseOrderRepository.findMaxCode());
			dto.setSupply_price((((Integer) arr[6])) * ((Integer) arr[3]));// 필요수량 * 단가

			// 발주수량과 공급 가격 구하기 위해서 조달계획 가져옴
			// ProcurementPlan procurementPlan =
			// procurementPlanRepository.findById((Integer)arr[0]).get();

			list.add(dto);
			// purchaseOrderRepository.save(purchaseOrder);

			// detailPurchaseOrderRepository.save(detailPurchaseOrder);
			// detailPurchaseOrderRepository.myUpdate(detailPurchaseOrder.getCode(), 14);//앞
			// 발주번호, 뒤 조달 코드

		}
	}

	@Test
	public void bring() {// myDetailList
		// detailPurchaseOrderRepository.myDetailList(2);
		// detailPurchaseOrderRepository.myDetailList();
		List<Object[]> result = detailPurchaseOrderRepository.myDetailList(2);// 조달계획 2번
		
		log.info("중요한 나의 정보>>" + result);

		// 아래 리스트에 더하기
		List<DetailPublishDTO> list = new ArrayList<>();
		
		for (Object[] arr : result) {// 이 아래가 변환하는 것 이다.
			log.info("222>>" + Arrays.toString(arr));
			// dto 데이터DetailPublishDTO(pono=8, pocode=8, ppcode=2, cname=(주)경도전자,
			// due_date=2023-06-10, mcode=CNa0001, mname=Bolt1, unit_price=200,
			// supply_price=20000,
			// purchaseOrderDate=2023-06-14T20:23:29.881289800, mamount=0, ppamount=100)

			// [2, (주)경도전자, 2023-06-10, 200, CN0001, Bolt1, 100]
			DetailPublishDTO dto = new DetailPublishDTO();
			dto.setPpcode((Integer) arr[0]);
			dto.setCname((String) arr[1]);
			dto.setDue_date((Date) arr[2]);
			dto.setUnit_price((Integer) arr[3]);
			dto.setMcode((String) arr[4]);
			dto.setMname((String) arr[5]);
			dto.setPpamount(((Integer) arr[6]));// 필요수량
			dto.setPurchaseOrderDate(LocalDateTime.now());
			dto.setPono(detailPurchaseOrderRepository.findMaxOrderNo());
			dto.setPocode(detailPurchaseOrderRepository.findMaxCode());
			dto.setSupply_price((((Integer) arr[6])) * ((Integer) arr[3]));// 필요수량 * 단가

			// 발주수량과 공급 가격 구하기 위해서 조달계획 가져옴
			// ProcurementPlan procurementPlan =
			// procurementPlanRepository.findById((Integer)arr[0]).get();

			list.add(dto);

		}
		// 잘 들어갔는지 확인
		for (DetailPublishDTO dto : list) {
			log.info("dto 데이터" + dto);
			// dto 데이터DetailPublishDTO(ppcode=2, cname=(주)경도전자, due_date=2023-06-10,
			// mcode=CNa0001,
			// mname=Bolt1, unit_price=200, purchaseOrderDate=2023-06-14T19:26:53.218694900,
			// mamount=null);
		}

	}

	// 버튼에서 보내온 것으로 디테일 코드랑 비교해서 보내려고
	// 조달 계획을 가져와서 -> 발주서 발행
	@Test
	public void chooseOneDetail2() {
		detailPurchaseOrderRepository.myUpdate2(10, 2);// 앞 발주번호, 뒤 조달 코드
		//procurementPlanRepository
		
	}
	
	@Test
	public void aaa2() {
		List<Object[]> result = detailPurchaseOrderRepository.myDetailList(2);// 조달계획 2번
		
		log.info("중요한 나의 정보>>" + result);

		// 아래 리스트에 더하기
		List<DetailPublishDTO> list = new ArrayList<>();
		for (Object[] arr : result) {// 이 아래가 변환하는 것 이다.
			log.info("222>>" + Arrays.toString(arr));
			// dto 데이터DetailPublishDTO(pono=8, pocode=8, ppcode=2, cname=(주)경도전자,
			// due_date=2023-06-10, mcode=CNa0001, mname=Bolt1, unit_price=200,
			// supply_price=20000,
			// purchaseOrderDate=2023-06-14T20:23:29.881289800, mamount=0, ppamount=100)

			// [2, (주)경도전자, 2023-06-10, 200, CNa0001, Bolt1, 100]
			DetailPublishDTO dto = new DetailPublishDTO();
			dto.setPpcode((Integer) arr[0]);
			dto.setCname((String) arr[1]);
			dto.setDue_date((Date) arr[2]);
			dto.setUnit_price((Integer) arr[3]);
			dto.setMcode((String) arr[4]);
			dto.setMname((String) arr[5]);
			dto.setPpamount(((Integer) arr[6]));// 필요수량 
			dto.setPurchaseOrderDate(LocalDateTime.now());
			dto.setPono(detailPurchaseOrderRepository.findMaxOrderNo());
			dto.setPocode(detailPurchaseOrderRepository.findMaxCode());
			dto.setSupply_price((((Integer) arr[6])) * ((Integer) arr[3]));// 필요수량 * 단가

			// 발주수량과 공급 가격 구하기 위해서 조달계획 가져옴
			// ProcurementPlan procurementPlan =
			// procurementPlanRepository.findById((Integer)arr[0]).get();

			list.add(dto);

		}
		// 잘 들어갔는지 확인
		for (DetailPublishDTO dto : list) {
			log.info("dto 데이터" + dto);
			// dto 데이터DetailPublishDTO(ppcode=2, cname=(주)경도전자, due_date=2023-06-10,
			// mcode=CNa0001,
			// mname=Bolt1, unit_price=200, purchaseOrderDate=2023-06-14T19:26:53.218694900,
			// mamount=null);
		}

	}

	@Test
	public void getDTOList() {
		List<ProcurementPlan> procurmentPlanList = procurementPlanRepository.findAll();

		List<DetailPurchaseOrderDTO> detailDTOList = new ArrayList<>();

		for (int i = 0; i < procurmentPlanList.size(); i++) {
			detailDTOList.add(entityToDTO(procurmentPlanList.get(i)));
		}

	}

	@Test
	public void midifyProcurementPlanTest() {// ====>>> ************ 중요 myUpdate() 이 안에 값을 넣기 !!!
		// 발주코드 생성해서 조달계획을 수정해서 저장하는 것
		// 발주코드 같으면 지정해서 조달계획 번호 저장

		// 여기 안에 숫자 넣어서 하기

		detailPurchaseOrderRepository.myUpdate(20, 13);
	}

	@Test
	public void purchaseOrderNoCreateTest() { // 발주서 번호 생성
		// ********잘 만들어진다.
		PurchaseOrder purchaseOrder = new PurchaseOrder(null);
		purchaseOrderRepository.save(purchaseOrder);

		DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(null, 200, LocalDateTime.now(),
				purchaseOrder);
		detailPurchaseOrderRepository.save(detailPurchaseOrder);
		detailPurchaseOrderRepository.myUpdate(detailPurchaseOrder.getCode(), 14);// 앞 발주번호, 뒤 조달 코드
		log.info("저장 되어라==>" + detailPurchaseOrder);
	}

	@Test
	public void InsertTest() { // 6월 13일 잘되던 코드
		// 발주 코드 생성 테스트
		// 잘 만들어 진다

		// PurchaseOrder purchaseOrder = new PurchaseOrder(findMax2());
		// Integer code, Integer amount, LocalDateTime date, Integer purchaseOrderNo
		// 코드는 자동 생성
		// 발주 번호는 참고해서

		// DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(null, 200,
		// LocalDateTime.now(),
		// purchaseOrder);
		// detailPurchaseOrderRepository.save(detailPurchaseOrder);

		// 아래는 테스트 중
		// 발주서 번호-> 발주 코드가 생성되고 난 뒤에 조달계획 코드 업데이트 하기

		// detailPurchaseOrderRepository.myUpdate(detailPurchaseOrder.getCode(),
		// null);//앞 발주번호, 뒤 조달 코드
	}

	@Transactional
	@Test
	public void purchaseOrderTest() {// 테스트는 잘 되는데 오토인크리 되어야 하는 것들이 다 널로 나온다 .
		// purchaseOrderRepository.save(null);
		// detailPurchaseOrderRepository.save(null);// 세부 구매발주서가 여러개

		// 오토인크리가 하나라 발주서 번호 테이블을 따로 만든 것이다. 따라서 발주서 만들어서 갖고 오기

		ProcurementPlan procurementPlan = procurementPlanRepository.findById(13).get();// 조달 계획 코드
		// 발주 수량도 바꾸긴 해야 한다. 일단 이렇게
		PurchaseOrder purchaseOrder = new PurchaseOrder(null);
		DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(null, procurementPlan.getAmount(),
				LocalDateTime.now(), purchaseOrder);

		log.info(">>구매 발주서 번호 만들어 주세요 제발 =========>>" + purchaseOrder);
		log.info(">>만들어 주세요 제발 =========>>" + detailPurchaseOrder);
	}

	@Transactional
	@Test
	public DetailPurchaseOrder purchaseOrderBuilTest23() {// 이거 안됨
		ProcurementPlan procurementPlan = procurementPlanRepository.findById(13).get();
		PurchaseOrder purchaseOrder = PurchaseOrder.builder().build();
		return null;
	}

	@Test
	public void getList() {
		Optional<DetailPurchaseOrder> list = detailPurchaseOrderRepository.findById(1);
		DetailPurchaseOrder detail = list.get();
		List<DetailPurchaseOrder> list2 = detailPurchaseOrderRepository.findAll();

		log.info(">>>>>>>>>>" + detail);
		log.info(">>>>>>>>>>" + list);
		log.info("여러개>>>>>>>>>>" + list2);

	}

	@Test
	public void createCodeAndNo() {
		DetailPurchaseOrder detailPurchaseOrder = detailPurchaseOrderRepository.findById(1).get();
		ProcurementPlan procurementPlan = procurementPlanRepository.findById(5).get();

		// DetailPurchaseOrder
		// dePurchaseOrder=DetailPurchaseOrder.builder().amount(procurementPlan.getAmount()).purchaseOrder(null).build();)
//		log.info("발주서를 발행해 보자 >>"+dePurchaseOrder);

	}

	@Test
	public void InsertTest(DetailPurchaseOrderDTO detailPurchaseOrderDTO) {
		// 발주 코드 생성 테스트
		// 조달계획을 수정하는 것임

		// dto의 조달계획코드랑 조달계획의 코드랑 같으면 생성하고 보내주기

		// if로 조달계획 코드가 있으면 거기에도 보내주기

		DetailPurchaseOrder procurementPlan = procurementPlanRepository.findById(4).get().getDetailPurchaseOrder();
		// 만약에 조달계획번호가 5라면

		Integer detailPurchaseOrder = detailPurchaseOrderDTO.getProcurementPlan();
		PurchaseOrder procurementPlan2 = procurementPlanRepository.findById(4).get().getDetailPurchaseOrder()
				.getPurchaseOrder();// 구매발주번호 no=3이렇게 나옴
		// 조달계획번호 -> 세부 구매발주 번호 -> 발주서 번호 ? 세 단계 순서로 나온다
		Integer procurementPlanDetail = procurementPlanRepository.findById(4).get().getDetailPurchaseOrder().getCode();// 6
																														// 널인걸
																														// 불러오면
																														// 오류남
		log.info("procurementPlan>>>>>>>>>>" + procurementPlan2);// null로 잘 나옴 조달계획을 잘 불러온다

		// procurementPlanRepository.save(detailPurchaseOrder);

		// 조달 계획 번호랑 내가 보낸 번호랑 같으면
		if (procurementPlanDetail == detailPurchaseOrder) {

			log.info("procurementPlan>>>>>>>>>>" + procurementPlan);
			log.info("detailPurchaseOrder>>>>>>>>>>" + detailPurchaseOrder);
			// procurementPlanRepository.save(procurementPlanRepository.findByDetailPurchaseOrder(detailPurchaseOrder.))

		}

		// Integer code, Integer amount, LocalDateTime date, Integer purchaseOrderNo
		// 코드는 자동 생성
		// 발주 번호는 참고해서

		// DetailPurchaseOrder detailPurchaseOrder = new DetailPurchaseOrder(null, 600,
		// LocalDateTime.now(), purchaseOrder);
		// detailPurchaseOrderRepository.save(detailPurchaseOrder);

	}

	// 조달계획안에 발주일자 넣는 테스트 해보기

	@Test
	public void InsertTest2(DetailPurchaseOrderDTO detailPurchaseOrderDTO) {
		// 발주 코드 생성 테스트
		// 조달계획에도 발주 코드가 떠야 한다.

		ProcurementPlan procurementPlan = procurementPlanRepository.findById(5).get();

		// 테스트 실패
		DetailPurchaseOrder detailPurchaseOrder = DetailPurchaseOrder.builder()
				.amount(detailPurchaseOrderDTO.getPurchaseOrderAmount()).date(LocalDateTime.now()).code(null)
				.purchaseOrder(null).build();

		log.info("여러개>>>>>>>>>>" + detailPurchaseOrder);
	}

	@Transactional
	@Test
	public void detailPurchaseOrderEntityToDTOTest() {// 발주서 번호 생성 전 보기
		// 발주계획 번호 받아오기
		ProcurementPlan procurementPlan = procurementPlanRepository.findById(5).get();
		log.info("procurementPlan>>>>>>>>>>" + procurementPlan);// 조달계획을 잘 불러온다

		// 발주서 번호, 협력회사, 발주일자, 납기 예정일
		// 발주 번호, 품목코드, 품목, 발주수량, 단가, 공급 가격,조달계획코드(외래키)
		// 총 11개(+ 외래키 조달계획코드로 갖고오기)

		// 공급가격만 구매발주서에서 가져옴
		// 발주서 번호, 발주 코드
		DetailPurchaseOrderDTO detailPurchaseOrderDTO = DetailPurchaseOrderDTO.builder()
				.purchaseOrderNo(detailPurchaseOrderRepository.findMaxOrderNo())
				.companyName(procurementPlan.getContract().getCompany().getName())
				.purchaseOrderDate(LocalDateTime.now()).dueDate(procurementPlan.getDueDate())
				.purchaseOrderCode(detailPurchaseOrderRepository.findMaxCode())
				.materialCode(procurementPlan.getMrp().getMaterial().getCode())
				.purchaseOrderAmount(
						(procurementPlan.getAmount()))
				.unitPrice(procurementPlan.getContract().getUnitPrice())
				.suppluPrice((procurementPlan.getAmount()) * (procurementPlan.getContract().getUnitPrice()))
				.procurementPlan(procurementPlan.getCode()).build();

		log.info(detailPurchaseOrderDTO);

	}

	@Transactional
	@Test
	public void detailPurchaseOrderDTOTest() {// 발주서 번호 생성 전 보기
		ProcurementPlan procurementPlan = procurementPlanRepository.findById(14).get();
		// Optional<DetailPurchaseOrder> detailPurchaseOrderDTO
		// =detailPurchaseOrderRepository.findById(1);

		// 발주서 번호, 협력회사, 발주일자, 납기 예정일
		// 발주 번호, 품목코드, 품목, 발주수량, 단가, 공급 가격,조달계획코드(외래키)
		// 총 11개(+ 외래키 조달계획코드로 갖고오기)

		// 공급가격만 구매발주서에서 가져옴
		// 발주서 번호, 발주 코드
		DetailPurchaseOrderDTO detailPurchaseOrderDTO = DetailPurchaseOrderDTO.builder()
				.purchaseOrderNo(detailPurchaseOrderRepository.findMaxOrderNo())
				.companyName(procurementPlan.getContract().getCompany().getName())
				.purchaseOrderDate(LocalDateTime.now()).dueDate(procurementPlan.getDueDate())
				.purchaseOrderCode(detailPurchaseOrderRepository.findMaxCode())
				.materialCode(procurementPlan.getMrp().getMaterial().getCode())
				.purchaseOrderAmount((procurementPlan.getAmount()))
				.unitPrice(procurementPlan.getContract().getUnitPrice())
				.suppluPrice((procurementPlan.getAmount()) * (procurementPlan.getContract().getUnitPrice()))
				.procurementPlan(procurementPlan.getCode()).build();

		log.info("무엇을 만드는 것인지>>" + detailPurchaseOrderDTO);

	}

	@Test
	public void findMax() {
		detailPurchaseOrderRepository.findMaxCode();

		log.info(">>>>>>>>>>" + detailPurchaseOrderRepository.findMaxCode());
	}

	@Test
	public void findMax2() {
		detailPurchaseOrderRepository.findMaxOrderNo();

		log.info(">>>>>>>>>>" + detailPurchaseOrderRepository.findMaxOrderNo());

	}

	@Test
	public DetailPurchaseOrderDTO entityToDTO(ProcurementPlan procurementPlan) {
		// 세부 구매 발주서 발행 화면
		DetailPurchaseOrderDTO detailPurchaseOrderDTO = DetailPurchaseOrderDTO.builder()
				.purchaseOrderNo(detailPurchaseOrderRepository.findMaxOrderNo())
				.companyName(procurementPlan.getContract().getCompany().getName())
				.purchaseOrderDate(LocalDateTime.now()).dueDate(procurementPlan.getDueDate())
				.purchaseOrderCode(detailPurchaseOrderRepository.findMaxCode())
				.materialCode(procurementPlan.getMrp().getMaterial().getCode())
				.purchaseOrderAmount((procurementPlan.getAmount()))
				.unitPrice(procurementPlan.getContract().getUnitPrice())
				.suppluPrice((procurementPlan.getAmount()) * (procurementPlan.getContract().getUnitPrice()))
				.procurementPlan(procurementPlan.getCode()).build();

		log.info("세부 구매 발주서 발행DTO" + detailPurchaseOrderDTO);
		return detailPurchaseOrderDTO;
	}

}
