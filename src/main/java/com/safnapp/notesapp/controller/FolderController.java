package com.safnapp.notesapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.safnapp.notesapp.dto.FolderDTO;
import com.safnapp.notesapp.entity.Folder;
import com.safnapp.notesapp.service.FolderService;
import com.safnapp.notesapp.service.StorageService;

import jakarta.validation.Valid;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/folder")
public class FolderController {
	 
	@Autowired
	private FolderService folderService;
	 
	@Autowired
	private StorageService storageService;
	
	
	@GetMapping
    public List<Folder> getAllFolders(@RequestParam(required = false) String type) {
		if ("private".equalsIgnoreCase(type)) {
	        return folderService.findByUserIdAndIsPrivate(true);
	    } else if ("public".equalsIgnoreCase(type)) {
	        return folderService.findByUserIdAndIsPrivate(false);
	    }else{
	        return folderService.findByUserId();
	    }
    }


    @GetMapping("/{folderId}")
    public ResponseEntity<Object> getFolderById(@PathVariable Long folderId) {
        Object folder = folderService.findFolderById(folderId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping("/{folderId}/notes")
    public ResponseEntity<Object> getNotesUnderFolder(@PathVariable Long folderId) {
        Object folder = folderService.getNotesUnderFolder(folderId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping("/{folderId}/images")
    public ResponseEntity<Object> getImageIdsByFolderId(@PathVariable Long folderId) {
        Object folder = storageService.findImageIdsByFolderId(folderId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping("/{folderId}/images/{imageId}")
	public ResponseEntity<?> downloadImage(@PathVariable long imageId){
    	
		byte[] imageData=storageService.downloadImage(imageId, false);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
    
	@PostMapping
    public  ResponseEntity<Folder> addFolder(@Valid @RequestBody FolderDTO folderDTO) throws Exception{
		Folder createdFolder =  folderService.addFolder(folderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder); 
    }
	
	@PutMapping("/{folderId}")
	public ResponseEntity<?> updateFolder(@PathVariable Long folderId,@Valid @RequestBody FolderDTO updatedFolder) {
        Object updated = folderService.updateFolder(folderId, updatedFolder);
        return ResponseEntity.ok(updated);
	    
	}
	
	@DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(folderService.deleteFolder(folderId));
    }
}
