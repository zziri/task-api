package com.zziri.todo.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SocialServiceFactory {
    private final Map<String, SocialService> socialServiceMap = new HashMap<>();

    public SocialServiceFactory(List<SocialService> socialServiceList) {
        socialServiceList.forEach(service -> socialServiceMap.put(service.getType(), service));
    }

    public SocialService getService(String type) {
        return socialServiceMap.get(type);
    }
}
