package com.gl.studentsapi.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gl.studentsapi.model.Role;
import com.gl.studentsapi.model.Student;
import com.gl.studentsapi.model.User;
import com.gl.studentsapi.repository.RoleRepository;
import com.gl.studentsapi.repository.StudentRepository;
import com.gl.studentsapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootStrapAppData implements ApplicationListener<ApplicationReadyEvent>{
	
	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// implement faker here
		for(int i=0; i<10; i++) {
			Student student = new Student();
			student.setFirstName("Maddy" + i);
			student.setLastName("R" + i);
			student.setCountry("India" + i);
			student.setCourse("IT" + i);
			studentRepository.save(student);
		}
		
		
		Role userRole=new Role();
		userRole.setRoleName("ROLE_USER");
		
		Role adminRole=new Role();
		adminRole.setRoleName("ROLE_ADMIN");
		
		
		
		User user=new User();
		user.setUserName("user");
		user.setPassword(this.passwordEncoder.encode("user"));
		
		User admin=new User();
		admin.setUserName("admin");
		admin.setPassword(this.passwordEncoder.encode("admin"));
				
		admin.addRole(adminRole);
		admin.addRole(userRole);
		
		user.addRole(userRole);
		
		userRepository.save(user);
		userRepository.save(admin);
		roleRepository.save(userRole);
		roleRepository.save(adminRole);
	}

}
