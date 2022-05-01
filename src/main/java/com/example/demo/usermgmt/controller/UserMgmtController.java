package com.example.demo.usermgmt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.usermgmt.common.Constants;
import com.example.demo.usermgmt.common.UserMgmtException;
import com.example.demo.usermgmt.dto.UserDto;
import com.example.demo.usermgmt.service.UserService;

/**
 * User Management Controller
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
@Validated
@RestController
@RequestMapping(Constants.BASE_URL)
public class UserMgmtController {

  @Autowired
  private UserService userService;

  @RequestMapping("/listUsers")
  public List<UserDto> listUsers() throws UserMgmtException {
    return userService.listUsers();
  }

  @PostMapping("/upsertUser")
  public UserDto upsertUser(@Valid @RequestBody UserDto user)
      throws UserMgmtException {
    return userService.upsertUser(user);
  }

  @RequestMapping("/isUserExists/{email}")
  public boolean isUserExists(@PathVariable String email)
      throws UserMgmtException {
    return userService.isUserExists(email);
  }
}
