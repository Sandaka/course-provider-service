package com.kingston.msc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/20/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyStudentApplication {

    private long tempStudentId;
    private String fullName;
    private String email;
    private String nationality;
    private String telephone;
    private String nic;
    private String gender;
    private Date createdDate;

    private String qualificationName;
    private String qualificationTypeName;
    private String school;
    private String overallGrade;
    private Date effectiveDate;
    //private List<EducationQualificationDto> educationQualificationDtoList;

    private long courseId;
    private long courseTypeId;
    private long batchId;
    private long courseProviderId;
    private long smsAccountId;
    private String paymentStatus;
    private String amount;
    private Date paymentDate;
    private long courseOfferId; // this can be empty left join
    private String offerName;

    private long branchId;
    private String username;
    private int status;

}
