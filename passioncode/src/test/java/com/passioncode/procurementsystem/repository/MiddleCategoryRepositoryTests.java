package com.passioncode.procurementsystem.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.passioncode.procurementsystem.entity.LargeCategory;
import com.passioncode.procurementsystem.entity.MiddleCategory;

@SpringBootTest
public class MiddleCategoryRepositoryTests {

	@Autowired
	MiddleCategoryRepository middleCategoryRepository;
	
	@Autowired
	LargeCategoryRepository largeCategoryRepository;
	
	@Test
	public void InsertTest() {
//		LargeCategory largeCategory=new LargeCategory("CC0001", "철");
		LargeCategory largeCategory=new LargeCategory("BB0001", "보드");
//		MiddleCategory middleCategory=new MiddleCategory("GG0001", "기어", largeCategory);
//		MiddleCategory middleCategory=new MiddleCategory("BB0001", "브라켓", largeCategory);
//		MiddleCategory middleCategory=new MiddleCategory("NN0001", "나사", largeCategory);
		MiddleCategory middleCategory=new MiddleCategory("PP0001", "PCB", largeCategory);
		middleCategoryRepository.save(middleCategory);
	}
	
	
	@Test
	public void Insert2Test() {
		Optional<LargeCategory> result = largeCategoryRepository.findById("PP0001");
		
		LargeCategory largeCategory = result.get();
		
		System.out.println("어디 어떻게 찍히나 보자 : "+largeCategory);
		MiddleCategory middleCategory=new MiddleCategory("CC0001", "케이스", largeCategory);
		middleCategoryRepository.save(middleCategory);
		
	}
}