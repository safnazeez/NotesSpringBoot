package com.safnapp.notesapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.safnapp.notesapp.config.GeneralResponse;
import com.safnapp.notesapp.dao.FolderRepository;
import com.safnapp.notesapp.dao.NotesRepository;
import com.safnapp.notesapp.dto.NoteDTO;
import com.safnapp.notesapp.entity.Folder;
import com.safnapp.notesapp.entity.Note;
import com.safnapp.notesapp.entity.User;
import com.safnapp.notesapp.exception.NotFoundException;
import jakarta.validation.Valid;

@Service
public class NoteService {

	@Autowired
	private NotesRepository repo;
	
	@Autowired
	private FolderRepository folderRepo;

	
	public List<Note> getAllFolders() {
		return repo.findAll();
	}
	public Note addNote(long folderId, NoteDTO notesDTO) throws Exception {
		try {
			 User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 Folder folder = folderRepo.findById(folderId)
	                    .orElseThrow(() -> new NotFoundException("Note not found with ID: " + folderId));
			 
			 Note note = Note.builder()
		                .name(notesDTO.getName())
		                .body(notesDTO.getBody())
		                .folder(folder)
		                .user(user)
		                .build();
			 return repo.save(note);
	    } catch (Exception e) {
	        throw new Exception(e);
	    }
		
	}
	
	public List<Note> findByUserId() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();
		
		return repo.findByUserId(id);
	}
	
	
	public Object updateNote(Long noteId, @Valid NoteDTO updatedNote) throws IOException {
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		  Integer userId = user.getId();
		    
		  Optional<Note> existingNote = repo.findByIdAndUserId(noteId, userId);
	        if (existingNote.isPresent()) {
	        	Note note = existingNote.get();
	        	note.setName(updatedNote.getName());
	        	note.setBody(updatedNote.getBody());
	            Note updated = repo.save(note);
	            return updated;
	        } else {
	            return new GeneralResponse("Note with ID " + noteId + " not found.", HttpStatus.NOT_FOUND);
	        }
	}
	
	
	public Object deleteNote(Long noteId) {
		 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
	        
        Optional<Note> note= repo.findByIdAndUserId(noteId, userId);
        if (note.isPresent()) {
            return new GeneralResponse("Note with ID " + noteId + " Successfully deleted.", HttpStatus.OK);
        	
        } else {
            return new GeneralResponse("Note with ID " + noteId + " not found.", HttpStatus.NOT_FOUND);
        }
	}
	
	public Object findNoteById(Long noteId) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer id = user.getId();

		Optional<Note> note = repo.findByIdAndUserId(noteId, id);
		
		if (note.isPresent()) {
            return note;
        } else {
            return new GeneralResponse("Note with ID " + noteId + " not found.", HttpStatus.NOT_FOUND);
        }
	}
	
}
