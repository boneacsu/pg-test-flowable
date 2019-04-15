package com.boneacsu.research.workflow.testflowable.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class EmailService {
    public ConcurrentHashMap<String, AtomicInteger> sends = new ConcurrentHashMap<>();

    public void sendWelcomeEmail(String customerId, String email) {
        log.info("sending welcome email for {} to {}", customerId, email);
        sends.computeIfAbsent(email, c -> new AtomicInteger());
        sends.get(email).incrementAndGet();
    }
}