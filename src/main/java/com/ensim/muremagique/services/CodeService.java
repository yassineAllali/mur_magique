package com.ensim.muremagique.services;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.entities.User;
import com.ensim.muremagique.repositories.CodeRepository;
import com.ensim.muremagique.repositories.UserRepository;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CodeService
{

	private final CodeRepository codeRepository;
	private final StorageService storageService;
	private final UserRepository userRepository;

	public CodeService(CodeRepository codeRepository, StorageService storageService,
		UserRepository userRepository)
	{
		this.codeRepository = codeRepository;
		this.storageService = storageService;
		this.userRepository = userRepository;
	}

	public List<Code> getAll()
	{
		return codeRepository.findAll();
	}

	public Code getCode(Long id)
	{

		Code code = codeRepository.findById(id).orElseThrow(
			() -> new BusinessException("Code not foud"));
		return code;
	}

	public Code addCode(MultipartFile file, String email)
	{
		if (codeRepository.existsByPath(file.getOriginalFilename())) {
			throw new BusinessException("Code already exist");
		}



		User user = userRepository.findFirstByEmail(email).orElseThrow(
			() -> new UsernameNotFoundException("username not found"));

		storageService.store(file);
		Code code = new Code(file.getOriginalFilename(), 0, user);
		long order = codeRepository.count();
		code.setOrder((int)(order + 1));
		codeRepository.save(code);
		return code;
	}

	public Code popCode()
	{
		List<Code> orderedCodes = codeRepository.findByOrderByOrderAsc();
		if (orderedCodes.size() == 0) {
			throw new BusinessException("Code Queue is empty");
		}
		Code codeToBeDeleted = orderedCodes.get(0);

		orderedCodes.forEach(code -> {
			code.setOrder(code.getOrder() - 1);
			codeRepository.save(code);
		});

		codeRepository.delete(codeToBeDeleted);

		storageService.remove(codeToBeDeleted.getPath());

		return codeToBeDeleted;
	}
}
