package com.example.demo.usermgmt.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JPA model for Users table
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 */
@Entity
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String email;

  /**
   * Default constructor. Required by JPA to work properly since one more 
   * custom constructor defined.
   */
  public User() {
  }

  /**
   * Full Constructor for UserDto mapping
   * 
   * @param id User ID
   * @param firstName First Name
   * @param lastName Last Name
   * @param email Email
   */
  public User(Long id, String firstName, String lastName, String email) {
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
}
