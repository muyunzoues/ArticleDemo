package com.example.smalldemo.pojo;

import lombok.Data;

@Data
public class PasswordData {
    private String oldPwd;
    private String newPwd;
    private String rePwd;

}
