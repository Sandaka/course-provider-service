package com.kingston.msc.repository;

import com.kingston.msc.entity.TempStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Repository
public interface TempStudentRepository extends JpaRepository<TempStudent, Long> {
}
