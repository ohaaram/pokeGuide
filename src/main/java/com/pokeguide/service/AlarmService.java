package com.pokeguide.service;

import com.pokeguide.entity.Alarm;
import com.pokeguide.repository.AlarmRepository;
import jakarta.transaction.Transactional;
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

    // 알람 확인 후 삭제하는 메서드
    public void checkAndDeleteAlarm(Long alarmId) {
        alarmRepository.findById(alarmId).ifPresent(alarm -> {
            alarm.setChecked(true);
            alarmRepository.delete(alarm);
        });
    }


    public void clearCheckedAlarms(String uid) {
        alarmRepository.deleteByUidAndCheckedTrue(uid);
    }

    @Transactional
    public void deleteAlarmsByChatRoomAndUser(int chatNo, String uid) {
        System.out.println("Deleting alarms for UID: " + uid + " in chatNo: " + chatNo); // 로그 추가
        alarmRepository.deleteByChatNoAndUid(chatNo, uid);
    }

}
