package com.sloth.domain.user.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.global.auth.dto.CredentialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public CredentialDto getCredential(String username) {   // 자격 증명 조회
        User user = userRepository.findByUsername(username);
        return new CredentialDto(user.getUsername(), user.getPassword());
    }
}
