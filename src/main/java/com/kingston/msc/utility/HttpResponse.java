package com.kingston.msc.utility;

import lombok.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpResponse {

    protected int statusCode;
    protected String status;
    protected String message;
    protected String timeStamp;
}
