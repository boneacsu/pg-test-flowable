package com.boneacsu.research.rcs.testflowable;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class TestFlowableApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestFlowableApplication.class, args);
	}

}

@Log4j2
@Service
class EmailService {
	ConcurrentHashMap<String, AtomicInteger> sends = new ConcurrentHashMap<>();

	public void sendWelcomeEmail(String customerId, String email) {
		log.info("sending welcome email for {} to {}", customerId, email);
		sends.computeIfAbsent(email, c -> new AtomicInteger());
		sends.get(email).incrementAndGet();
	}
}