package com.kingston.msc.stripe.model;

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
public class Charge {

    private String email;
    private String token;
    private int amount;
}
