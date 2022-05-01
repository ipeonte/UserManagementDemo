package com.example.demo.usermgmt.common;

/**
 * Top Level Exception
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class UserMgmtException extends Exception {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;

  public UserMgmtException(String message, Exception e) {
    super(message, e);
  }
}
