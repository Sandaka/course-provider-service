package com.kingston.msc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 6/25/22
 */

@Getter
@Setter
@NoArgsConstructor
public class Audit {

    @Column(name = "status")
    private int status = 1;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "last_edit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEditDate;

    @Column(name = "last_edit_by")
    private String lastEditBy;
}
