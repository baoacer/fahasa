package com.whilewework.fahasa.services.auth;

import com.whilewework.fahasa.dto.SignupRequest;
import com.whilewework.fahasa.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithUsername(String email);
}