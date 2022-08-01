package com.kingston.msc.model;

import lombok.*;

import java.math.BigDecimal;


/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/24/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class CourseProviderDetails {

    private String transactionId;
    private BigDecimal amount;

    private String fullName;
    private String nationality;
    private String nic;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postalCode;
    private String telephone1;
    private String telephone2;
    private String schoolName;
    private String registrationNo;
    private String personalEmail;
    private String schoolWebsite;
    private String description;

    private int packageId;
}
