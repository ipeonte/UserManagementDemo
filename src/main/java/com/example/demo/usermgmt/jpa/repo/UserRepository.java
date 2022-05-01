package com.example.demo.usermgmt.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.usermgmt.jpa.model.User;

/**
 * Users repository
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);
}
