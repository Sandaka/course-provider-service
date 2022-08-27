package com.kingston.msc.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/25/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CourseYearsDetail {

    private BigDecimal fee;
    private String year;
    private Date dueDate;
}
