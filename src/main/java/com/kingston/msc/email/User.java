package com.kingston.msc.email;

import lombok.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/26/22
 */

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class User {

    private String username;
    private String name;
    private String email;
    //getter & setter
}
