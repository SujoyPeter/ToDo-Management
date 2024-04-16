package com.management.todo.service.impl;

import com.management.todo.dto.LoginDTO;
import com.management.todo.dto.RegisterDTO;
import com.management.todo.entity.Role;
import com.management.todo.entity.User;
import com.management.todo.exception.TodoAPIException;
import com.management.todo.repository.RoleRepository;
import com.management.todo.repository.UserRepository;
import com.management.todo.secutiry.JwtTokenProvider;
import com.management.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDTO registerDTO) {

        //check username is already exist
        if(userRepository.existsByUserName(registerDTO.getUserName())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!!");
        }

        //check email is already exist
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw  new TodoAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!!");
        }

        User user = new User();

        user.setName(registerDTO.getName());
        user.setUserName(registerDTO.getUserName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));


        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        roles.add(roleUser);

        user.setRoles(roles);

        userRepository.save(user);


        return "User Registered Successfully!!!";
    }

    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUserNameOrEmail(),
                loginDTO.getPassword()
        ));

        System.out.println(loginDTO);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.generateToken(authenticate);

        return token;
    }
}
