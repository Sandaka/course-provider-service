package com.kingston.msc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/25/22
 */
@Entity
@Table(name = "cp_transaction_tracker")
@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CPTransactionTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_provider_id")
    private String courseProviderId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "sms_account_id")
    private String smsAccountId;

    @Column(name = "subscription")
    private String subscription;
}
