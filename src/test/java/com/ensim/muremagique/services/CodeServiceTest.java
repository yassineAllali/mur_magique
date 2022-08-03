package com.ensim.muremagique.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import java.util.ArrayList;
import java.util.List;

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
	public void addCodeShouldSucceed()
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

	@Test
	public void addCodeShouldFailWhenFileAlreadyExist()
	{
		// Given

		MultipartFile file = new MockMultipartFile("test", "test", null, new byte[]{});

		doNothing().when(storageService).store(any());
		given(codeRepository.count()).willReturn(0l);
		given(codeRepository.save(any(Code.class))).willReturn(new Code("dummy", 0));
		given(codeRepository.existsByPath("test")).willReturn(true);

		// Then

		assertThrows(BusinessException.class, () -> {
			codeService.addCode(file);
		});
	}

	@Test
	public void popCodeShouldSucceed()
	{
		// Given
		List<Code> codes = List.of(new Code("test1", 1), new Code("test2", 2));

		given(codeRepository.findByOrderByOrderAsc()).willReturn(codes);

		// When

		Code code = codeService.popCode();

		// Then

		assert(code.getPath()).equals("test1");
		assert(code.getOrder()).equals(0);

	}

	@Test
	public void popCodeShouldFailWhenNoCodeExist()
	{
		// Given
		List<Code> codes = new ArrayList<>();

		given(codeRepository.findByOrderByOrderAsc()).willReturn(codes);

		// Then

		assertThrows(BusinessException.class, () -> {
			codeService.popCode();
		});
	}

}