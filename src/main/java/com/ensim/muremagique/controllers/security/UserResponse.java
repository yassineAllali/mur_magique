package com.ensim.muremagique.controllers.security;

public class UserResponse
{
	private final Long id;
	private final String email;
	private final String firstName;
	private final String lastName;
	private final String role;

	public UserResponse(Long id, String email, String firstName, String lastName, String role)
	{
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public Long getId()
	{
		return id;
	}

	public String getEmail()
	{
		return email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getRole()
	{
		return role;
	}
}
