package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/13/22
 */
@Entity
@Table(name = "qualification_detail")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QualificationDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_student_id", referencedColumnName = "id", nullable = false)
    private TempStudent tempStudentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", referencedColumnName = "id", nullable = false)
    private Qualification qualificationId;
}
