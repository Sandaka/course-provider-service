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
@Table(name = "registration")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Registration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_membership_id", referencedColumnName = "id", nullable = false)
    private CourseMembership courseMembershipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "id", nullable = false)
    private Branch branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_detail_id", referencedColumnName = "id", nullable = false)
    private CourseDetail courseDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", referencedColumnName = "id", nullable = false)
    private Badges badgesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_offer_id", referencedColumnName = "id", nullable = false)
    private CourseOffers courseOffersId;

    @Column(name = "sms_user_id", nullable = false)
    private Long smsUserId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<StudentPayment> studentPaymentSet;

    @Embedded
    private Audit audit;
}
