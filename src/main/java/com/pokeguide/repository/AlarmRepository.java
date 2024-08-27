package com.pokeguide.repository;

import com.pokeguide.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUidAndCheckedFalse (String uid);
    void deleteByUidAndCheckedTrue(String uid);

}
