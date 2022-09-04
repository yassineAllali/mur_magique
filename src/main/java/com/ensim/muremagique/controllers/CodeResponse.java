package com.ensim.muremagique.controllers;

import java.util.Date;

public class CodeResponse
{
	private final Long id;
	private final String name;
	private final String size;
	private final String uploader;
	private final Date createdAt;

	public CodeResponse(Long id, String name, String size, String uploader, Date createdAt)
	{
		this.id = id;
		this.name = name;
		this.size = size;
		this.uploader = uploader;
		this.createdAt = createdAt;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getSize()
	{
		return size;
	}

	public String getUploader()
	{
		return uploader;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}
}
