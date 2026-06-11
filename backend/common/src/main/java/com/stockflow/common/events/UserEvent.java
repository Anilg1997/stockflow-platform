package com.stockflow.common.events;

import com.stockflow.common.dto.UserDTO;

public class UserEvent extends BaseEvent {

    private UserAction action;
    private UserDTO user;

    public enum UserAction {
        CREATED, UPDATED, KYC_VERIFIED, DEACTIVATED
    }

    public UserEvent() {}

    public UserEvent(UserAction action, UserDTO user) {
        this.action = action;
        this.user = user;
    }

    public UserAction getAction() { return action; }
    public void setAction(UserAction action) { this.action = action; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
}
