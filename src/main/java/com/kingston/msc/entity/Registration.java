package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "registration")
@Getter
@Setter
@NoArgsConstructor
@Data
//@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Registration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;


    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", nullable = false)
//    @JsonIgnore
    private Branch branchId;

    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_detail_id", referencedColumnName = "id", nullable = false)
//    @JsonIgnore
    private CourseDetail courseDetailId;

    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", referencedColumnName = "id", nullable = false)
//    @JsonIgnore
    private Badges badgesId;

    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_offer_id", referencedColumnName = "id", nullable = false)
//    @JsonIgnore
    private CourseOffers courseOffersId;

    @Column(name = "sms_user_id", nullable = false)
    private Long smsUserId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties
    private Set<StudentPayment> studentPaymentSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    private Set<CourseMembership> courseMembershipSet;

    @Embedded
    private Audit audit;
}
