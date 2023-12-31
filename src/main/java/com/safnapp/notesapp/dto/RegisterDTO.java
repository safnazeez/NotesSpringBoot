package com.safnapp.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDTO {
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Invalid mobile number")
	private String mobile;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
}
