package com.blog.app.ws;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blog.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepistory extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}
