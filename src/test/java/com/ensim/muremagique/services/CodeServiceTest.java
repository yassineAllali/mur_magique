package com.ensim.muremagique.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.repositories.CodeRepository;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CodeServiceTest
{
	private CodeRepository codeRepository;
	private StorageService storageService;
	private CodeService codeService;

	@BeforeEach
	public void setUp()
	{
		codeRepository = mock(CodeRepository.class);
		storageService = mock(StorageService.class);
		codeService = new CodeService(codeRepository, storageService);
	}

	@Test
	public void uploadCodeShouldSucceed()
	{
		// Given
		MultipartFile file = new MockMultipartFile("test", "test", null, new byte[]{});

		doNothing().when(storageService).store(any());
		given(codeRepository.count()).willReturn(0l);
		given(codeRepository.save(any(Code.class))).willReturn(new Code("dummy", 0));

		// When
		Code actual = codeService.addCode(file);

		// Then

		assert(actual.getOrder()).equals(1);
		assert(actual.getPath()).equals("test");
	}
}