package com.safnapp.notesapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safnapp.notesapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByMobile(String mobile);
	boolean existsByMobile(String mobile);
}
