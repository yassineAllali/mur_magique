package com.ensim.muremagique.services;

import com.ensim.muremagique.entities.Code;
import org.springframework.web.multipart.MultipartFile;

class Mapper
{
	CodeResource map(Code code, MultipartFile file)
	{
		return new CodeResource(code.getId(), code.getPath(), file);
	}
}
