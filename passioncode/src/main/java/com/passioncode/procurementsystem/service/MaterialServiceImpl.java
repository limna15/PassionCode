package com.passioncode.procurementsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.passioncode.procurementsystem.dto.MaterialDTO;
import com.passioncode.procurementsystem.entity.Material;
import com.passioncode.procurementsystem.repository.ContractRepository;
import com.passioncode.procurementsystem.repository.MaterialRepository;
import com.passioncode.procurementsystem.repository.MiddleCategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MaterialServiceImpl implements MaterialService {
	
	private final ContractRepository contractRepository;	
	private final MaterialRepository materialRepository;
	private final MiddleCategoryRepository middleCategoryRepository;
	
	
	@Override
	public Material getMaterial(String code) {		
		return materialRepository.findById(code).orElse(null);
	}
	
	/**
	 * 공용여부 Integer -> 한글로 만들어주기 <br>
	 * 0 : 공용, 1 : 전용
	 * @param shareStatus
	 * @return
	 */
	public String shareStatusChangeToString(Integer shareStatus) {
		// 0 : 공용, 1 : 전용
		return shareStatus==0 ? "공용" : "전용";
	}
	
	/**
	 * 공용여부 한글 -> Integer 로 만들어주기 <br>
	 * 공용 : 0 , 전용 : 1
	 * @param shareStatus
	 * @return
	 */
	public Integer shareStatusChangeToInteger(String shareStatus) {
		// 공용 : 0 , 전용 : 1
		return shareStatus.equals("공용") ? 0 : 1;
	}

	@Override
	public MaterialDTO entityToDTO(Material material) {
		//품목코드, 품목명, 대, 중, 규격, 재질, 제작사양, 도면번호, 도면Image, 공용여부, 계약상태
		MaterialDTO materialDTO =  MaterialDTO.builder().code(material.getCode()).name(material.getName()).size(material.getSize()).quality(material.getQuality())
														.spec(material.getSpec()).drawingNo(material.getDrawingNo()).drawingFile(material.getDrawingFile())
														.shareStatus(shareStatusChangeToString(material.getShareStatus())).stockAmount(material.getStockAmount())
														.largeCategoryName(material.getMiddleCategory().getLargeCategory().getCategory())
														.middleCategoryName(material.getMiddleCategory().getCategory())
														.contractStatus(contractStatusCheck(material))
														.largeCategoryCode(material.getMiddleCategory().getLargeCategory().getCode())
														.middleCategoryCode(material.getMiddleCategory().getCode()).build();						
		return materialDTO;
	}

	@Override
	public Material dtoToEntity(MaterialDTO materialDTO) {		
		//품목코드, 품목명, 공용여부, 규격, 재질, 제작사양, 도면번호, 도면, 중분류
		Material material = Material.builder().code(materialDTO.getCode()).name(materialDTO.getName()).shareStatus(shareStatusChangeToInteger(materialDTO.getShareStatus()))
												.stockAmount(materialDTO.getStockAmount()).size(materialDTO.getSize()).quality(materialDTO.getQuality())
												.spec(materialDTO.getSpec()).drawingNo(materialDTO.getDrawingNo()).drawingFile(materialDTO.getDrawingFile())
												.middleCategory(middleCategoryRepository.findById(materialDTO.getMiddleCategoryCode()).get()).build();
		return material;
	}

	@Override
	public List<MaterialDTO> getDTOList() {
		List<Material> materialList = materialRepository.findAll();
		List<MaterialDTO> materialDTOList = new ArrayList<>();
		for(int i=0;i<materialList.size();i++) {
			materialDTOList.add(entityToDTO(materialList.get(i)));
		}
		log.info("materialDTO 리스트 제대로 되었나 봐보자! : "+materialDTOList);
		return materialDTOList;
	}

	@Override
	public String contractStatusCheck(Material material) {		
		//완료 : 계약상태 O, 미완료 : 계약상태 X
		return contractRepository.existsByMaterial(material) ? "완료" : "미완료";
	}
	
	
	@Override
	public String register(MaterialDTO materialDTO) {
		Material material = dtoToEntity(materialDTO);
		materialRepository.save(material);
		log.info("저장된 품목(material) 정보 : "+material);
		
		return material.getCode();
	}
	
	@Transactional
	@Override
	public void modify(MaterialDTO materialDTO) {
		Material material = dtoToEntity(materialDTO);
		materialRepository.save(material);
		log.info("수정된 품목(material) 정보 : "+material);
	}
	
	@Override
	public void delete(MaterialDTO materialDTO) {
		log.info("삭제된 품목(material)정보 : "+dtoToEntity(materialDTO));
		materialRepository.deleteById(materialDTO.getCode());		
	}
	
	



}
