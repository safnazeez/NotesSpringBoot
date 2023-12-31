package com.safnapp.notesapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safnapp.notesapp.dto.FolderResponseDTO;
import com.safnapp.notesapp.entity.Folder;
import com.safnapp.notesapp.service.FolderService;
import com.safnapp.notesapp.service.StorageService;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
	 
		@Autowired
		private FolderService folderService;
		 
		@Autowired
		private StorageService storageService;
	
	@GetMapping("/folders")
    public List<FolderResponseDTO> getAllPublicFolders() {
        return folderService.findByIsPrivateFalse();
    }


    @GetMapping("/folder/{folderId}")
    public ResponseEntity<Object> findFolderByIdByIsPrivateFalse(@PathVariable Long folderId) {
        Object folder = folderService.findFolderByIdByIsPrivateFalse(folderId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping("/folder/{folderId}/notes")
    public ResponseEntity<Object> getNotesUnderPublicFolder(@PathVariable Long folderId) {
        Object folder = folderService.getNotesUnderFolder(folderId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping("/folder/{folderId}/images")
    public ResponseEntity<Object> getImageIdsByPublicFolderId(@PathVariable Long folderId) {
        Object folder = storageService.findImageIdsByPublicFolderId(folderId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping("/folder/{folderId}/images/{imageId}")
	public ResponseEntity<?> downloadImage(@PathVariable long imageId){
    	
		byte[] imageData=storageService.downloadImage(imageId, true);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}

}
