package com.example.demo.usermgmt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.usermgmt.common.UserMgmtException;
import com.example.demo.usermgmt.dto.UserDto;
import com.example.demo.usermgmt.jpa.model.User;
import com.example.demo.usermgmt.jpa.repo.UserRepository;
import com.example.demo.usermgmt.service.UserService;

/**
 * UserService implementation
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepo;

  @Override
  public List<UserDto> listUsers() throws UserMgmtException {
    try {
      return userRepo.findAll().stream().map(u -> new UserDto(u.getId(),
          u.getFirstName(), u.getLastName(), u.getEmail()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new UserMgmtException("Error retrieving all users", e);
    }
  }

  @Override
  public UserDto upsertUser(UserDto userDto) throws UserMgmtException {
    try {
      User user = userRepo.save(new User(userDto.getId(),
          userDto.getFirstName(), userDto.getLastName(), userDto.getEmail()));
      return new UserDto(user.getId(), user.getFirstName(), user.getLastName(),
          user.getEmail());
    } catch (Exception e) {
      throw new UserMgmtException("Error upsert users", e);
    }
  }

  @Override
  public Boolean isUserExists(String email) throws UserMgmtException {
    try {
      return userRepo.findByEmail(email) != null;
    } catch (Exception e) {
      throw new UserMgmtException("Error check user exist", e);
    }
  }

}
