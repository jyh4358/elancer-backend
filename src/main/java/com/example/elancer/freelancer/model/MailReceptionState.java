package com.example.elancer.freelancer.model;

public enum MailReceptionState {
    RECEPTION("수신"),
    NOT_RECEPTION("미수신")
    ;

    private String desc;

    MailReceptionState(String desc) {
        this.desc = desc;
    }
}
