package com.passioncode.procurementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@ContextConfiguration
public class MaterialInServiceTests {
	
	@Mock
	MaterailInService service;
	
	@Test
	public void testGetList() {
		service.getList().forEach(vo -> log.info("리스트다--------"+vo));
	}

}