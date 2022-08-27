package com.kingston.msc.service;

import com.kingston.msc.entity.TempStudent;
import com.kingston.msc.model.TempStudentDetails;
import com.sun.tools.javac.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
public interface StudentService {

    public TempStudent saveTempStudent(TempStudentDetails tempStudentDetails);

    //courseid, barnchid, coursetypeid,
    List<TempStudentDetails> findNewStudentApplications(long courseId, long branchId, long courseTypeId);
}
