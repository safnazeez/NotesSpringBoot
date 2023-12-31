package com.safnapp.notesapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safnapp.notesapp.entity.ImageData;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface StorageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);

    @Query("SELECT i.id FROM ImageData i JOIN i.folder f JOIN f.user u WHERE f.id = :folderId AND u.id = :userId")
	List<Long> findImageIdsByFolderId(@Param("folderId") Long folderId, @Param("userId") Integer id );
    
    @Query("SELECT i.id FROM ImageData i JOIN i.folder f WHERE f.id = :folderId AND f.isPrivate = false")
	List<Long> findImageIdsByPublicFolderId(@Param("folderId") Long folderId);

    @Query("SELECT i FROM ImageData i WHERE i.id = :imageId AND i.folder.isPrivate = false")
	Optional<ImageData> findByIdAndIsPrivateFalse(long imageId);

    @Query("SELECT i FROM ImageData i JOIN i.folder f JOIN f.user u WHERE i.id = :imageId AND u.id = :id")
	Optional<ImageData> findByIdAndUserId(long imageId, Integer id);
}