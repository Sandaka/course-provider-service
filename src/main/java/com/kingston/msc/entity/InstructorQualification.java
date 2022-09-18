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
@Table(name = "instructor_qualification")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InstructorQualification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "qualification_provider", nullable = false)
    private String qualificationProvider;

    @Column(name = "qualification_name", nullable = false)
    private String qualificationName;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false)
    private Instructor instructorId;

    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_type_id", referencedColumnName = "id", nullable = false)
    private QualificationType qualificationTypeId;

    @Embedded
    private Audit audit;
}
