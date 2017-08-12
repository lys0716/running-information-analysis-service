package demo.domain;

import lombok.Data;

@Data
public class UserInfo {
    private String username;
    private String address;

    public UserInfo() {};

    public UserInfo(String username, String address) {
        this.username = username;
        this.address = address;
    }
}
