package com.example.demo.usermgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.usermgmt.common.Constants;
import com.example.demo.usermgmt.dto.UserDto;

@TestPropertySource("classpath:/test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserMgmtDemoRestApiTest {

  @Autowired
  private TestRestTemplate _rest;

  @Autowired
  private JdbcTemplate _jdbc;

  @BeforeEach
  public void checkInitData() {
    ResponseEntity<List<UserDto>> response =
        _rest.exchange(Constants.BASE_URL + "/listUsers", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<UserDto>>() {
            });

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody(), "Response body for listUsers is null");

    List<UserDto> users = response.getBody();
    assertEquals(2, users.size(), "Size of initial users data doesn't match.");
  }

  @Test
  public void testFindUserBad() {
    ResponseEntity<String> response =
        _rest.getForEntity(Constants.BASE_URL + "/isUserExists", String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    response =
        _rest.getForEntity(Constants.BASE_URL + "/isUserExists/", String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testAddUserBad() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // Try empty request
    ResponseEntity<String> response =
        _rest.postForEntity(Constants.BASE_URL + "/upsertUser",
            new HttpEntity<String>("", headers), String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    // Try empty payload
    response = _rest.postForEntity(Constants.BASE_URL + "/upsertUser",
        new HttpEntity<String>("{}", headers), String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    // Try invalid payload
    response = _rest.postForEntity(Constants.BASE_URL + "/upsertUser",
        new HttpEntity<String>("{\"firstName\": \"Igor\"}", headers),
        String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testAddUpdateUserGood() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    final String email1 = "igor.peonte@example.com";
    // Check if new user can be found
    checkUser(email1, false);

    // Add new user
    HttpEntity<String> entity = new HttpEntity<String>(
        "{\"firstName\": \"Igor\",\"lastName\":\"Peonte\", " + "\"email\": \"" +
            email1 + "\"}",
        headers);

    UserDto user = checkUserDtoResponse(_rest.postForEntity(
        Constants.BASE_URL + "/upsertUser", entity, UserDto.class));

    // Check if new user can be found after adding
    checkUser(email1, true);

    // Check user entry available in database
    assertEquals(1,
        _jdbc.queryForObject(
            "select count(*) from " + "users where email = '" + email1 + "'",
            Integer.class).intValue());

    // Update user
    final String email2 = "igor.144@gmail.com";
    user.setEmail(email2);
    checkUserDtoResponse(_rest.postForEntity(Constants.BASE_URL + "/upsertUser",
        user, UserDto.class));

    // Check user can not be found by old email
    checkUser(email1, false);

    // Check if user can be found by new email
    checkUser(email2, true);

    // Delete new user entry
    _jdbc.execute("delete from users where id = " + user.getId());

    // Check if new user can be found any more
    checkUser(email2, false);

  }

  private UserDto checkUserDtoResponse(ResponseEntity<UserDto> response) {
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    return response.getBody();
  }

  private void checkUser(String email, Boolean isFound) {
    String url = Constants.BASE_URL + "/isUserExists/" + email;

    ResponseEntity<String> response = _rest.getForEntity(url, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody(), "Response body for isUserExists is null");
    assertEquals(isFound, Boolean.valueOf(response.getBody()),
        "Response for isUserExists for " + email + " doesn't match");
  }

}
