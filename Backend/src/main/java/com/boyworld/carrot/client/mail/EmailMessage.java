package com.boyworld.carrot.client.mail;

import lombok.Builder;
import lombok.Data;

@Data
public class EmailMessage {

    private String to;
    private String subject;
    private String message;

    @Builder
    private EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
