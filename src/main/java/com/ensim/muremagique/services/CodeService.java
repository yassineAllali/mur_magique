package com.ensim.muremagique.services;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.repositories.CodeRepository;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CodeService
{

	private final CodeRepository codeRepository;
	private final StorageService storageService;

	public CodeService(CodeRepository codeRepository, StorageService storageService)
	{
		this.codeRepository = codeRepository;
		this.storageService = storageService;
	}

	public List<Code> getAll()
	{
		return codeRepository.findAll();
	}

	public Code getCode(Long id)
	{

		Code code = codeRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Code not foud"));
		return code;
	}

	public Code addCode(MultipartFile file)
	{
		storageService.store(file);
		Code code = new Code(file.getOriginalFilename(), 0);
		long order = codeRepository.count();
		code.setOrder((int)(order + 1));
		codeRepository.save(code);
		return code;
	}

	public Code popCode()
	{
		List<Code> orderedCodes = codeRepository.findByOrderByOrderAsc();
		Code codeToBeDeleted = orderedCodes.get(0);

		orderedCodes.forEach(code -> {
			code.setOrder(code.getOrder() - 1);
			codeRepository.save(code);
		});

		codeRepository.delete(codeToBeDeleted);
		return codeToBeDeleted;
	}


}
