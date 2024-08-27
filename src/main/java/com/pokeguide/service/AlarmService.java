package com.pokeguide.service;

import com.pokeguide.entity.Alarm;
import com.pokeguide.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public void createAlarm(String uid, String message, Integer chatNo){
        Alarm alarm = Alarm.builder()
                .uid(uid)
                .message(message)
                .chatNo(chatNo)
                .checked(false)
                .build();
        alarmRepository.save(alarm);
    }

    public List<Alarm> getUncheckedAlarms(String uid) {
        return alarmRepository.findByUidAndCheckedFalse(uid);
    }

    public void checkAlarm(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new IllegalArgumentException("Invalid alarm ID"));
        alarm.setChecked(true);
        alarmRepository.save(alarm);
    }

    public void clearCheckedAlarms(String uid) {
        alarmRepository.deleteByUidAndCheckedTrue(uid);
    }

}
