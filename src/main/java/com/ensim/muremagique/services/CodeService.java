package com.ensim.muremagique.services;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.entities.User;
import com.ensim.muremagique.repositories.CodeRepository;
import com.ensim.muremagique.repositories.UserRepository;
import com.ensim.muremagique.services.infrastructure.StorageService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
		String sortBy = "creationDate";
		String sortDir = "asc";

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
			Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

		return codeRepository.findAll(sort);
	}

	public Code getCode(Long id)
	{

		Code code = codeRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Code not foud"));
		return code;
	}

	public Code getFirstCode()
	{
		List<Code> codes = getAll();
		if( codes.size() <= 0) {
			throw new BusinessException("Codes list is empty!");
		}
		return codes.get(0);
	}

	public Code addCode(MultipartFile file, String email)
	{
		if (codeRepository.existsByPath(file.getOriginalFilename())) {
			throw new BusinessException("Code already exist");
		}

		User user = userRepository.findFirstByEmail(email).orElseThrow(
			() -> new UsernameNotFoundException("username not found"));

		storageService.store(file);
		Code code = new Code(file.getOriginalFilename(), new Date(), file.getSize(), user);
		codeRepository.save(code);
		return code;
	}

	public Code deleteCode(Long id)
	{
		Code codeToBeDeleted = codeRepository.findById(id).orElseThrow(
			() -> new NotFoundException("Code not found"));
		User owner = codeToBeDeleted.getUser();
		owner.getCodes().remove(codeToBeDeleted);
		userRepository.save(owner);

		storageService.remove(codeToBeDeleted.getPath());
		return codeToBeDeleted;
	}
}
