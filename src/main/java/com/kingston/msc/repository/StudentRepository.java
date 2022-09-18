package com.kingston.msc.repository;

import com.kingston.msc.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByTempStudentId(long tempStuId);
}
