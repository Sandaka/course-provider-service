package com.kingston.msc.stripe.utils;

import lombok.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/7/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Response {

    private boolean status;
    private String details;
}
