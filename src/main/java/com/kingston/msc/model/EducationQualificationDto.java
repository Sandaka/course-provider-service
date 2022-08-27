package com.kingston.msc.model;

import com.kingston.msc.utility.FileInfo;
import lombok.*;

import java.util.Date;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/9/22
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class EducationQualificationDto {

    private long qualificationTypeId;
    private String qualificationName;
    private Date effectiveDate;
    private Date startDate;
    private String school;
    private String overallGrade;
    private FileInfo fileInfo;

    private String subject;
    private String grade;
}
