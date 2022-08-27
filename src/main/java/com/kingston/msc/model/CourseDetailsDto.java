package com.kingston.msc.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class CourseDetailsDto {

    private String courseName;
    private String years;
    private long courseTypeId;
    private long eduLevelId;
    private Date startDate;
    private Date endDate;
    private String lectureDate;
    private String lectureTime;
    private String seats;
    private String medium;
    private BigDecimal courseFee;
    private String description;
    private List<SubjectDetail> subjectList;
    private List<CourseYearsDetail> courseYearsList;

    private String offer;
    private String offerDescription;
    private Date validUntil;
}
