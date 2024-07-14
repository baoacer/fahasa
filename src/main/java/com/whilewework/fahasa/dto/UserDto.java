package com.whilewework.fahasa.dto;

import com.whilewework.fahasa.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRole userRole;

}