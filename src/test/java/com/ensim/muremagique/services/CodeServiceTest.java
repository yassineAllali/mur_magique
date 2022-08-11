package com.ensim.muremagique.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.entities.User;
import com.ensim.muremagique.repositories.CodeRepository;
import com.ensim.muremagique.repositories.UserRepository;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CodeServiceTest
{
	private CodeRepository codeRepository;
	private StorageService storageService;
	private UserRepository userRepository;
	private CodeService codeService;

	@BeforeEach
	public void setUp()
	{
		codeRepository = mock(CodeRepository.class);
		storageService = mock(StorageService.class);
		codeService = new CodeService(codeRepository, storageService, userRepository);
	}

	@Test
	public void addCodeShouldSucceed()
	{
		// Given
		MultipartFile file = new MockMultipartFile("test", "test", null, new byte[]{});
		String email = "email@email.com";

		doNothing().when(storageService).store(any());
		given(codeRepository.count()).willReturn(0l);
		given(codeRepository.save(any(Code.class))).willReturn(new Code("dummy", new Date(), new User()));

		// When
		Code actual = codeService.addCode(file, email);

		// Then
		assert(actual.getPath()).equals("test");
	}

	@Test
	public void addCodeShouldFailWhenFileAlreadyExist()
	{
		// Given

		MultipartFile file = new MockMultipartFile("test", "test", null, new byte[]{});
		String email = "email@email.com";

		doNothing().when(storageService).store(any());
		given(codeRepository.count()).willReturn(0l);
		given(codeRepository.save(any(Code.class))).willReturn(new Code("dummy", new Date(), new User()));
		given(codeRepository.existsByPath("test")).willReturn(true);

		// Then

		assertThrows(BusinessException.class, () -> {
			codeService.addCode(file, email);
		});
	}

	@Test
	public void popCodeShouldSucceed()
	{
		// Given
		List<Code> codes = List.of(new Code("test1", new Date(), new User()), new Code("test2", new Date(), new User()));


	}

	@Test
	public void popCodeShouldFailWhenNoCodeExist()
	{
		// Given
		List<Code> codes = new ArrayList<>();

	}

}