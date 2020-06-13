package com.example.hansol.mbtistd.serviceimpl;

import com.example.hansol.mbtistd.repository.userRepository;
import com.example.hansol.mbtistd.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class userServiceImpl implements userService {

    @Autowired
    userRepository USER_REPOSITORY;
    
}