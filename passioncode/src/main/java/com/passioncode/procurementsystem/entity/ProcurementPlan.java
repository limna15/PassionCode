package com.passioncode.procurementsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 조달계획 테이블을 위한 엔티티 클래스
 * @author KSH
 * 
 */ 
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"mrp","contract","detailPurchaseOrder"})
public class ProcurementPlan {		//조달계획
	
	/**
	 * 조달계획코드
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 5, columnDefinition = "INT(5)")
	private Integer code;	
	
	/**
	 * 필요수량
	 */
	@Column(length = 10, columnDefinition = "INT(10)", nullable = false)
	private Integer amount;		
	
	/**
	 * 조달납기 예정일
	 */
	@Column(columnDefinition = "DATE", nullable = false)
	private Date dueDate;	
	
	/**
	 * 최소발주일
	 */
	@Column(columnDefinition = "DATE", nullable = false)
	private Date minimumOrderDate;		
	
	/**
	 * 조달계획 등록일
	 */
	@Column(columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime registerDate;			
	
	/**
	 * 조달계획 완료일
	 */
	@Column(columnDefinition = "DATETIME")
	private LocalDateTime completionDate;		
	
	/**
	 * 자재소요계획코드(외래키)(품목)
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private MRP mrp;		
	
	/**
	 * 계약서번호(외래키)(계약서)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Contract contract;	
	
	/**
	 * 발주코드(외래키)(세부구매발주서)
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private DetailPurchaseOrder detailPurchaseOrder;	
	
	

}
