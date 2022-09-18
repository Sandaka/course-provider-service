package com.kingston.msc.model;

import lombok.*;

import java.util.Date;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/12/22
 */
@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TempStudentCourseDetailDto {

    private long courseId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private long tempStuId;
    private String fullName;
    private String courseTypeName;
    private String courseFee;
    private String years;
    private String medium;
    private String year;
    private String cost;
    private Date dueDate;
    private String schoolName;
    private int paymentStatus;
}
