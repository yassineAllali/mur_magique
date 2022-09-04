package com.ensim.muremagique.controllers;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.services.CodeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/codes")
public class CodeController
{

	private final CodeService codeService;
	private final Mapper mapper;

	public CodeController(CodeService codeService)
	{
		this.codeService = codeService;
		this.mapper = new Mapper();
	}

	@GetMapping
	public List<CodeResponse> getAll()
	{
		return mapper.map(codeService.getAll());
	}

	@GetMapping("/{id}")
	public Code getCode(@PathVariable Long id)
	{
		return codeService.getCode(id);
	}

	@GetMapping("/first")
	public Code getFirstCode()
	{
		return codeService.getFirstCode();
	}

	@PostMapping()
	public Code addCode(@RequestParam() MultipartFile code,
		@CurrentSecurityContext(expression = "authentication") Authentication authentication)
	{
		Object principal = authentication.getPrincipal();

		if(principal == null){
			throw new UsernameNotFoundException("username not found");
		}

		String username = null;

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		return codeService.addCode(code, username);
	}

	@DeleteMapping("/{id}")
	public Code deleteCode(@PathVariable Long id)
	{
		return codeService.deleteCode(id);
	}
}
