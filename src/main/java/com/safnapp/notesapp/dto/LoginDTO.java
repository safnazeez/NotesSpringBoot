package com.safnapp.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDTO {
	
	@NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Invalid mobile number")
	private String mobile;
	
	@NotBlank(message = "Mobile number cannot be blank")
	String password;

}
