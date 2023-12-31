package com.safnapp.notesapp.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.safnapp.notesapp.config.GeneralResponse;
import com.safnapp.notesapp.dao.FolderRepository;
import com.safnapp.notesapp.dto.FolderDTO;
import com.safnapp.notesapp.dto.FolderResponseDTO;
import com.safnapp.notesapp.entity.Folder;
import com.safnapp.notesapp.entity.Note;
import com.safnapp.notesapp.entity.User;

@Service
public class FolderService {

	@Autowired
	private FolderRepository repo;
	public List<Folder> getAllFolders() {
		return repo.findAll();
	}
	public Folder addFolder(FolderDTO folderDTO) throws Exception {
		try {
			 User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			 Folder folder = Folder.builder()
		                .name(folderDTO.getName())
		                .isPrivate(folderDTO.getIsPrivate())
		                .user(user)
		                .build();
			 return repo.save(folder);
	    } catch (Exception e) {
	        throw new Exception(e);
	    }
		
	}
	
	public List<Folder> findByUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();
		
		return repo.findByUserId(id);
	}
	
	public List<Folder> findByUserIdAndIsPrivate(boolean b) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();
		
		return repo.findByUserIdAndIsPrivate(id, b);
	}
	
	public Object updateFolder(Long folderId, FolderDTO updatedFolder) {
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		  Integer userId = user.getId();
		    
		 Folder existingFolder = repo.findByIdAndUserId(folderId, userId);
	        if (existingFolder != null) {
	            existingFolder.setName(updatedFolder.getName());
	            existingFolder.setIsPrivate(updatedFolder.getIsPrivate());
	            Folder updated = repo.save(existingFolder);
	            return updated;
	        } else {
	            return new GeneralResponse("Folder with ID " + folderId + " not found.", HttpStatus.NOT_FOUND);
	        }
	}
	
	public Object deleteFolder(Long folderId) {
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
	        
		Folder folder = repo.findByIdAndUserId(folderId, userId);
        if (folder != null) {
        	repo.deleteById(folderId);
            return new GeneralResponse("Folder with ID " + folderId + " deleted successfully.", HttpStatus.OK);
        } else {
            return new GeneralResponse("Folder with ID " + folderId + " not found.", HttpStatus.NOT_FOUND);
        }
	}
	
	public Object findFolderById(Long folderId) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();

		Folder folder = repo.findByIdAndUserId(folderId, id);
		if (folder != null) {
            return folder;
        } else {
            return new GeneralResponse("Folder with ID " + folderId + " not found.", HttpStatus.NOT_FOUND);
        }
	}
	
	public List<Note> getNotesUnderFolder(Long folderId) {
		 return repo.findById(folderId)
	                .map(Folder::getNotes)
	                .orElse(Collections.emptyList());

    }
	public List<Note> getNotesUnderPublicFolder(Long folderId) {
		 return repo.findByIdAndIsPrivateFalse(folderId)
	                .map(Folder::getNotes)
	                .orElse(Collections.emptyList());

   }
	public List<FolderResponseDTO> findByIsPrivateFalse() {
		List<Folder> nonPrivateFolders = repo.findByIsPrivateFalse();
        List<FolderResponseDTO> folderDTOs = nonPrivateFolders.stream()
                .map(folder -> new FolderResponseDTO(folder.getId(), folder.getName()))
                .collect(Collectors.toList());
        return folderDTOs;
	}
	public Object findFolderByIdByIsPrivateFalse(Long folderId) {
		return repo.findByIdAndIsPrivateFalse(folderId);
	}
	
}
