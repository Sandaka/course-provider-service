package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/13/22
 */
@Entity
@Table(name = "course_provider")
@Getter
@Setter
@NoArgsConstructor
//@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CourseProvider implements Serializable {

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

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "nic")
    private String nic;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "description")
    private String description;

    @Column(name = "sms_account_id")
    private Long smsAccountId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<Branch> branchSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<CourseProviderPayment> courseProviderPaymentSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<Course> courseSet;

    @Embedded
    private Audit audit;
}
