package com.ensim.muremagique.services;

import org.springframework.web.multipart.MultipartFile;

public class CodeResource
{
	private final Long id;
	private final String path;
	private final MultipartFile file;

	public CodeResource(Long id, String path, MultipartFile file)
	{
		this.id = id;
		this.path = path;
		this.file = file;
	}

	public Long getId()
	{
		return id;
	}

	public String getPath()
	{
		return path;
	}

	public MultipartFile getFile()
	{
		return file;
	}
}
