package com.kingston.msc.repository;

import com.kingston.msc.entity.QualificationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/9/22
 */
@Repository
public interface QualificationDetailRepository extends JpaRepository<QualificationDetail, Long> {
}
