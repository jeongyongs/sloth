package com.sloth.domain.user.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.global.auth.dto.CredentialDto;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public CredentialDto getCredential(String username) {   // 자격 증명 조회
        User user = userRepository.findByUsername(username);
        return new CredentialDto(user.getUsername(), user.getPassword());
    }

    public User getUserByUsername(String username) {    // 유저명으로 객체 조회
        return userRepository.findByUsername(username);
    }

    public String getName(HttpServletRequest request) throws Exception {    // 이름 조회
        String username = jwtService.getUsername(request);
        User user = getUserByUsername(username);
        return user.getName();
    }

    public User getUserById(Long id) {  // 아이디로 객체 조회
        return userRepository.findById(id);
    }
}
