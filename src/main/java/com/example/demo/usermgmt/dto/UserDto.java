package com.example.demo.usermgmt.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Class for User DTO
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 */
public class UserDto {

  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @Email
  private String email;

  /**
   * Default constructor
   */
  public UserDto() {
  }

  /**
   * Full constructor
   * 
   * @param id Internal System ID
   * @param firstName First Name
   * @param lastName Last Name
   * @param email Email
   */
  public UserDto(Long id, String firstName, String lastName, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" +
        lastName + ", email=" + email + "]";
  }

}
