package com.pokeguide.controller.chat;

import com.pokeguide.entity.Alarm;
import com.pokeguide.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/alarms")
    public ResponseEntity<List<Alarm>> getAlarms(@RequestParam String uid){
        return ResponseEntity.ok(alarmService.getUncheckedAlarms(uid));
    }

    @DeleteMapping("/alarms/{alarmId}/check")
    public ResponseEntity<Void> checkAndDeleteAlarm(@PathVariable Long alarmId) {
        alarmService.checkAndDeleteAlarm(alarmId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chatrooms/{chatNo}/enter")
    public ResponseEntity<Void> enterChatRoom(@PathVariable int chatNo, @RequestParam String uid) {
        // 해당 유저가 이 방에 들어왔을 때 알람 삭제
        alarmService.deleteAlarmsByChatRoomAndUser(chatNo, uid);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/alarms/checked")
    public ResponseEntity<Void> clearCheckedAlarms(@RequestParam String uid) {
        alarmService.clearCheckedAlarms(uid);
        return ResponseEntity.noContent().build();
    }

}
