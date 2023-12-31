package com.safnapp.notesapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safnapp.notesapp.entity.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long>{

	@Override
    Folder save(Folder folder);

	List<Folder> findByUserId(Integer id);
	
    List<Folder> findByUserIdAndIsPrivate(Integer userId, boolean isPrivate);

	Folder findByIdAndUserId(Long folderId, Integer userId);

	List<Folder> findByIsPrivateFalse();

    Optional<Folder> findByIdAndIsPrivateFalse(Long id);


}
