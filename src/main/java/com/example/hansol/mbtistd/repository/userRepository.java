package com.example.hansol.mbtistd.repository;

import com.example.hansol.mbtistd.entity.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends CrudRepository<user, Long> {
    
}