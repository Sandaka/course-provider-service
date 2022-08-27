package com.kingston.msc.model;

import lombok.*;

import java.util.Date;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/26/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class CourseYearFeeDto {

    //private Date dueDate;
    private long courseId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String seats;
    private String courseTypeName;
    private long eduLevelId;
    private String eduLevel;
    private String years;
    private String medium;
    private String courseFee;
    private String offer;
    //private String year;
    //private String cost;
    private String schedule;
}
