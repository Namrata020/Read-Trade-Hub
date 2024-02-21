package com.app.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.users.models.User;

public interface IUserRepository extends JpaRepository<User,String> {

}
