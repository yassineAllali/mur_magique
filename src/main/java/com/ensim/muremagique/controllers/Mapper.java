package com.ensim.muremagique.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.ensim.muremagique.entities.Code;

public class Mapper
{
	public CodeResponse map(Code code)
	{
		return new CodeResponse(code.getId(), code.getPath(), "1 mo", code.getUser().getLastName(),
			code.getCreationDate());
	}

	public List<CodeResponse> map(List<Code> codes)
	{
		return codes.stream().map(code -> map(code)).collect(Collectors.toList());
	}
}
