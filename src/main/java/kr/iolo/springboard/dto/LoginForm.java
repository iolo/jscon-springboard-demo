package kr.iolo.springboard.dto;

import lombok.Data;

/**
 * @author iolo
 */
@Data
public class LoginForm {
    public String username;
    public String password;
    public String next;
}
