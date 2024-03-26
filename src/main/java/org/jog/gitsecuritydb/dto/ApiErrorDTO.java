package org.jog.gitsecuritydb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timeStamp;
}
