package com.passioncode.procurementsystem.service;

import java.util.List;

import com.passioncode.procurementsystem.dto.MaterialDTO;
import com.passioncode.procurementsystem.entity.Material;

public interface MaterialService {
	
	/**
	 * 품목코드를 이용해서 Material 품목 엔티티 가져오기
	 * @param code
	 * @return
	 */
	Material get(String code);
	
	/**
	 * 품목 엔티티를 이용해서 MaterialDTO로 만들기(Material -> MaterialDTO)
	 * @param material
	 * @return
	 */
	MaterialDTO entityToDTO(Material material);
	
	/**
	 * MaterialDTO를 이용해서 품목 엔티티 만들기(MaterialDTO -> Material)
	 * @param materialDTO
	 * @return
	 */
	Material dtoToEntity(MaterialDTO materialDTO);
	
	/**
	 * MaterialDTO 리스트 가져오기
	 * @return
	 */
	List<MaterialDTO> getDTOList();

}