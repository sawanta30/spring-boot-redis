package com.redis.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.redis.model.User;
@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
