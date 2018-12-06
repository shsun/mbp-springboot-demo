package com.as.cyems;

import lombok.Data;

@Data
public class SessionInfo {
    private Integer userId;
    private String username;
    private String ua;
    private String ip;

    public SessionInfo(Integer userId) {
        this.setUserId(userId);
    }
}
