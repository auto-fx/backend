package com.autofx.autofxbackend.iam.infrastructure.hashing.bcrypt.services;

import com.autofx.autofxbackend.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements BCryptHashingService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public HashingServiceImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodePassword);
    }
}
