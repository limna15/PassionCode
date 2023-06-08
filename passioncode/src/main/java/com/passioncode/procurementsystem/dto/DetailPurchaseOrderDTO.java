package com.passioncode.procurementsystem.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailPurchaseOrderDTO {
	
	/**
	 * 발주서 번호
	 */
	private Integer purchaseOrderNo;
	
	/**
	 * 협력 회사
	 */
	private String company;
	
	/**
	 * 발주 일자
	 */
	private LocalDateTime date;
	
	/**
	 * 납기 예정일
	 */
	private Date dueDate;
	
	/**
	 * 글 순서번호
	 */
	private Integer no;
	
	/**
	 * 발주코드
	 */
	private Integer purchaseOrderCode;
	
	/**
	 * 품목코드
	 */
	private Integer materialCode;
	
	/**
	 * 발주수량
	 */
	private Integer amount;
	
	
	/**
	 * 단가
	 */
	private Integer unitPrice;
	
	/**
	 * 공급가격
	 */
	private Integer suppluPrice;
	
}
