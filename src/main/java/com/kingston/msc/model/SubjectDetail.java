package com.kingston.msc.model;

import lombok.*;

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
public class SubjectDetail {

    private long id;
    private String name;
    private String instructorName;
    private long instructorId;
}
