package com.kingston.msc.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/7/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class CourseFeeDto {

    private String year;
    private BigDecimal cost;
    private String dueDate;

}
