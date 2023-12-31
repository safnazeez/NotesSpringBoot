package com.safnapp.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FolderDTO {

	@NotBlank(message = "Folder name cannot be blank")
	private String name;
	
	@Builder.Default
	private Boolean isPrivate = Boolean.TRUE;
	
	private FolderDTO(String name, Boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
        if (this.isPrivate == null) {
            this.isPrivate = true;
        }    
	}
}
