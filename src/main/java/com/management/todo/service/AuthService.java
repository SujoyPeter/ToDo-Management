package com.management.todo.service;

import com.management.todo.dto.LoginDTO;
import com.management.todo.dto.RegisterDTO;

public interface AuthService {

    String register(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);
}
