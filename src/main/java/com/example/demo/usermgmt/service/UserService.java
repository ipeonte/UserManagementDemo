package com.example.demo.usermgmt.service;

import java.util.List;

import com.example.demo.usermgmt.common.UserMgmtException;
import com.example.demo.usermgmt.dto.UserDto;

/**
 * Interface for User Service
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public interface UserService {

  /**
   * Get all users
   * 
   * @return All Users
   */
  List<UserDto> listUsers() throws UserMgmtException;

  /**
   * Add New or Update Existing user
   * 
   * @param user UserDto object
   * @return New or updated user
   */
  public UserDto upsertUser(UserDto user) throws UserMgmtException;

  /**
   * Check if user exists
   * 
   * @param email User Email object
   * @return TRUE if exists and FALSE if not
   */
  public Boolean isUserExists(String email) throws UserMgmtException;
}
