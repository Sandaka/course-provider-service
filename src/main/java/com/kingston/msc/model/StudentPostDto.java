package com.kingston.msc.model;

import lombok.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/10/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class StudentPostDto {

    private String fullName;
    private String email;
    private long courseId;
    private long smsUSerId;
    private long registrationId;
}
