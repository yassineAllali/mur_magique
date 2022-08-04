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

	private CodeService codeService;

	public CodeController(CodeService codeService)
	{
		this.codeService = codeService;
	}

	@GetMapping
	public List<Code> getAll()
	{
		return codeService.getAll();
	}

	@GetMapping("/{id}")
	public Code getCode(@PathVariable Long id)
	{
		return codeService.getCode(id);
	}

	@PostMapping()
	public Code createCode(@RequestParam() MultipartFile code,
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

	@PostMapping("/pop")
	public Code pop()
	{
		return codeService.popCode();
	}
}
