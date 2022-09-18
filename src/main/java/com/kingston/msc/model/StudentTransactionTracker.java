package com.kingston.msc.model;

import lombok.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/3/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class StudentTransactionTracker {

    private long studentId;
    private long registrationId;
    private String paymentTransactionId;
    private long smsAccountId;
    private String subscription;
    private long smsUserId;
    private String username;
    private String password;
    private String email;
}
