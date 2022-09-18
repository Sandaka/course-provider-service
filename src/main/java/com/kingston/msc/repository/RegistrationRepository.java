package com.kingston.msc.repository;

import com.kingston.msc.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 9/3/22
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Registration findRegistrationBySmsUserId(long smsUserId);
}
