package model;

public class UserData {
  private String number;
  private String code;
  private String pass;
  private String pass_confirm;
  private String name;
  private String last_name;
  private String email;
  private String postal_code;
  private String state;
  private String street;
  private String street_num;

  public UserData(String number, String code, String pass, String pass_confirm) {
    this.number = number;
    this.code = code;
    this.pass = pass;
    this.pass_confirm = pass_confirm;
  }

  public String getNumber() {
    return number;
  }

  public String getCode() {
    return code;
  }

  public String getPass() {
    return pass;
  }

  public String getPass_confirm() {
    return pass_confirm;
  }
}
