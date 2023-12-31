package com.safnapp.notesapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safnapp.notesapp.entity.Note;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface NotesRepository extends JpaRepository<Note, Long>{

	List<Note> findByUserId(Integer id);

	Optional<Note> findByIdAndUserId(Long noteId, Integer userId);

	void deleteById(Long folderId);

}
