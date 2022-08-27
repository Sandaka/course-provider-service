package com.kingston.msc.model;

import com.kingston.msc.utility.FileInfo;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/6/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class TempStudentDetails {

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
    private String civilStatus;
    private String email;
    private String gender;
    private Date dob;
    private long courseId;
    private long branchId;
    private long courseTypeId;
    private String description;

    private List<EducationQualificationDto> educationalQualificationList;
}
