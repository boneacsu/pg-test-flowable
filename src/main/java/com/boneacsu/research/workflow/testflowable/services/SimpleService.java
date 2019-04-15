package com.boneacsu.research.workflow.testflowable.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service("simpleService")
public class SimpleService {
    public void doSimpleSynchCall(String var1, String var2) {
        log.info("Executing simple synch call with vars {} and {}", var1, var2);
    }

    public void logMessage(String message)
    {
        log.info("Executing simplest thing ever :) : {} ", message);
    }
}
