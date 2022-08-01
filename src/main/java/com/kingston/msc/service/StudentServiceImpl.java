package com.kingston.msc.service;

import com.kingston.msc.repository.StudentRepository;
import com.kingston.msc.repository.TempStudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/1/22
 */
@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TempStudentRepository tempStudentRepository;

    @Autowired
    private StudentRepository studentRepository;
}
