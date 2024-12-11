package com.project.abc.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HealthController {

    private static Instant START_AT = Instant.now();

    @Autowired
    RequestDataProvider requestDataProvider;

    @GetMapping("/health/status")
    public Map<String, Object> healthCheck() {
        Map<String, Object> info = new HashMap<>();
        Duration upTime = Duration.between(START_AT, Instant.now());
        info.put("uptime",  upTime.toDays() + "d " + upTime.toHoursPart() + "h " + upTime.toMinutesPart() + "m " + upTime.toSecondsPart() + "s");
        info.put("status", "OK");
        info.put("client_ip", requestDataProvider.getClientIP());
        info.put("client_device", requestDataProvider.getDevice());
        info.put("client_platform", requestDataProvider.getAppPlatform());
        info.put("client_version", requestDataProvider.getAppVersion());
        return info;
    }
}
