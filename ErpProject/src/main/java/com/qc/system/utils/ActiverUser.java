package com.qc.system.utils;

import com.qc.system.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActiverUser implements Serializable {

    private static final long seriaVersionUID = 1L;

    private User user;

    private List<String> roles;
    private List<String> permissions;

}
