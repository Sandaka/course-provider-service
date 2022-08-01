package com.kingston.msc.repository;

import com.kingston.msc.entity.CPTransactionTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/25/22
 */
@Repository
public interface CPTransactionTrackerRepository extends JpaRepository<CPTransactionTracker, Long> {
}
