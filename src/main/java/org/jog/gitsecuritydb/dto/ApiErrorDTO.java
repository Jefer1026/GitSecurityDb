package org.jog.gitsecuritydb.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorDTO implements Serializable {

    private String backendMessage;
    private String message;
    private String url;

    private String method;
    private LocalDateTime timeStamp;
}
