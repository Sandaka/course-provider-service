package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/13/22
 */
@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line2", nullable = false)
    private String addressLine2;

    @Column(name = "address_line3")
    private String addressLine3;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "telephone1", nullable = false)
    private String telephone1;

    @Column(name = "telephone2")
    private String telephone2;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @Column(name = "nic", nullable = false)
    private String nic;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "civil_status", nullable = false)
    private String civilStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "temp_student_id")
    private Long tempStudentId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<CourseMembership> courseMembershipSet;

    @Embedded
    private Audit audit;
}
