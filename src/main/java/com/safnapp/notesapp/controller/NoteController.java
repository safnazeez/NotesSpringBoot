package com.safnapp.notesapp.controller;

import java.io.IOException;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safnapp.notesapp.dto.FolderDTO;
import com.safnapp.notesapp.dto.NoteDTO;
import com.safnapp.notesapp.entity.Note;
import com.safnapp.notesapp.service.NoteService;
import com.safnapp.notesapp.service.StorageService;

import jakarta.validation.Path;
import jakarta.validation.Valid;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/notes")
public class NoteController {
	 
	@Autowired
	private NoteService noteService;
	private StorageService service;
	
	
	@GetMapping
    public List<Note> getAllNotes() {
        return noteService.findByUserId();
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Object> getNoteById(@PathVariable Long noteId) {
        Object note = noteService.findNoteById(noteId);
        
        return ResponseEntity.ok(note);
    }
    
	@PostMapping("/folder/{folderId}")
    public  ResponseEntity<Note> addFolder(@PathVariable long folderId, @Valid @RequestBody NoteDTO noteDTO) throws Exception{
		Note createdNote =  noteService.addNote(folderId, noteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote); 
    }
	
	@PutMapping("/{noteId}")
	public ResponseEntity<?> updateFolder(@PathVariable Long noteId,@Valid @RequestBody NoteDTO updatedNote) throws IOException {
        Object updated = noteService.updateNote(noteId, updatedNote);
        return ResponseEntity.ok(updated);
	    
	}
	
	@DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteFolder(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.deleteNote(noteId));
    }
	
	
	
}
