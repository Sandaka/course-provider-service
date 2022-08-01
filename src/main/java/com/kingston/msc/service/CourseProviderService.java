package com.kingston.msc.service;

import com.kingston.msc.entity.CourseProvider;
import com.kingston.msc.model.CourseProviderDetails;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/24/22
 */
public interface CourseProviderService {

    public CourseProvider saveCourseProvider(CourseProviderDetails courseProviderDetails);
}
