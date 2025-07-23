package backend.app.model;

import java.lang.String;

public class User {
  private String roles;

  private boolean active;

  private int id;

  private String email;

  private String username;

  public String getRoles() {
    return this.roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public boolean getActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
