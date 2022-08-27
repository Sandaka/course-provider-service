package com.kingston.msc.utility;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Data
@SuperBuilder
public class HttpResponse {

    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String timeStamp;
    protected Object data;
}
