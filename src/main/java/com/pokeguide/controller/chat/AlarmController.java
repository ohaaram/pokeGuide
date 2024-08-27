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

    @PostMapping("/alarms/{alarmId}/check")
    public ResponseEntity<Void> checkAlarm(@PathVariable Long alarmId) {
        alarmService.checkAlarm(alarmId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/alarms/checked")
    public ResponseEntity<Void> clearCheckedAlarms(@RequestParam String uid) {
        alarmService.clearCheckedAlarms(uid);
        return ResponseEntity.noContent().build();
    }

}
