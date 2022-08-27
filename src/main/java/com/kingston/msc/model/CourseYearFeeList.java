package com.kingston.msc.model;

import lombok.*;

import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/26/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CourseYearFeeList {

    private List<CourseYearFeeDto> courseDetailsList;
    private List<CourseFeeDto> courseFeeList;
}
