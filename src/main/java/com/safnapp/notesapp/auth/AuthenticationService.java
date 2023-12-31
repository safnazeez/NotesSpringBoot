package com.safnapp.notesapp.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.safnapp.notesapp.config.JwtService;
import com.safnapp.notesapp.dao.UserRepository;
import com.safnapp.notesapp.dto.LoginDTO;
import com.safnapp.notesapp.dto.RegisterDTO;
import com.safnapp.notesapp.entity.Role;
import com.safnapp.notesapp.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	  private final JwtService jwtService;
	  
	  private final AuthenticationManager authenticationManager;


	public AuthenticationResponse register(@RequestBody @Valid RegisterDTO request) {
		
		Optional<User> mobileEntry = repository.findByMobile(request.getMobile());
	    if(mobileEntry.isPresent()){
	        throw new RuntimeException("Mobile already exists!");
	    } 
	    
		var user = User.builder().name(request.getName())
			.mobile(request.getMobile())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.USER)
			.build();
		
		repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse login(@RequestBody @Valid LoginDTO request) {
		authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(request.getMobile(), request.getPassword()));
		var user = repository.findByMobile(request.getMobile()).orElseThrow();
		
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}
