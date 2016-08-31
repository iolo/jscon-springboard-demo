package kr.iolo.springboard.dto;

import lombok.Data;

/**
 * @author iolo
 */
@Data
public class SignupForm {
    public String username;
    public String password;
    public String next;
}
