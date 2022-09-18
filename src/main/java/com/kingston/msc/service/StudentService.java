package com.kingston.msc.service;

import com.kingston.msc.entity.Registration;
import com.kingston.msc.entity.TempStudent;
import com.kingston.msc.model.StudentPostDto;
import com.kingston.msc.model.TempStudentDetails;
import com.kingston.msc.model.VerifyStudentApplication;

import java.util.List;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
public interface StudentService {

    TempStudent saveTempStudent(TempStudentDetails tempStudentDetails);

    //courseid, barnchid, coursetypeid,
    List<VerifyStudentApplication> findNewStudentApplications(long courseId, int status, long branchId);

    List<TempStudent> findNewStudentApplications2(long courseId, int status, long branchId);

    List<VerifyStudentApplication> findPaymentCompletedApplications(long courseId, int status, long branchId);

    Registration saveAndEnrollStudent(VerifyStudentApplication verifyStudentApplication);

    long getRegistrationIdBySmsUserId(long smsUserId);

//    String get

    List<StudentPostDto> getStudentsByRegIds(List<Long> regIds);

    int updateStatus(VerifyStudentApplication verifyStudentApplication);
}
