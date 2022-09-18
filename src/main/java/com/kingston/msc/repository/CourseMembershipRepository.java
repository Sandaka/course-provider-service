package com.kingston.msc.repository;

import com.kingston.msc.entity.CourseMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/3/22
 */
@Repository
public interface CourseMembershipRepository extends JpaRepository<CourseMembership, Long> {
}
