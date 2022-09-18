package com.kingston.msc.repository;

import com.kingston.msc.entity.Audit;
import com.kingston.msc.entity.Branch;
import com.kingston.msc.entity.Course;
import com.kingston.msc.entity.TempStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Repository
public interface TempStudentRepository extends JpaRepository<TempStudent, Long> {

    List<TempStudent> findTempStudentsByCourseIdAndBranchId(Course courseId, Branch branchId);

}
