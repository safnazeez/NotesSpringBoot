package com.safnapp.notesapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.safnapp.notesapp.config.GeneralResponse;
import com.safnapp.notesapp.dao.FolderRepository;
import com.safnapp.notesapp.dao.StorageRepository;
import com.safnapp.notesapp.entity.Folder;
import com.safnapp.notesapp.entity.ImageData;
import com.safnapp.notesapp.entity.User;
import com.safnapp.notesapp.exception.NotFoundException;
import com.safnapp.notesapp.utils.ImageUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    @Autowired
	private FolderRepository folderRepo;

    public Object uploadImage(MultipartFile file, long folderId) throws IOException {
    	
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();
		
    	Folder folder = folderRepo.findByIdAndUserId(folderId, id);
	 
    	if (folder != null) {
    		 ImageData imageData = repository.save(ImageData.builder()
    	                .name(file.getOriginalFilename())
    	                .type(file.getContentType())
    	                .imageData(ImageUtils.compressImage(file.getBytes())).folder(folder).build());
    	        if (imageData != null) {
    	            return imageData;
    	        }
        } else {
            return new GeneralResponse("Folder with ID " + folderId + " not found.", HttpStatus.NOT_FOUND);
        }
       
        return null;
    }

    public byte[] downloadImage(long imageId, boolean isPublic){
    	if(isPublic) {
    		 Optional<ImageData> dbImageData = repository.findByIdAndIsPrivateFalse(imageId);
             byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
             return images;
    	} else {
    		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		Integer id = user.getId();
    		
            Optional<ImageData> dbImageData = repository.findByIdAndUserId(imageId, id);
            byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
            return images;
    	}
        
    }

    public List<Long> findImageIdsByFolderId(Long folderId) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();
		
        return repository.findImageIdsByFolderId(folderId, id);
    }
    public List<Long> findImageIdsByPublicFolderId(Long folderId) {
    	
        return repository.findImageIdsByPublicFolderId(folderId);
    }
}