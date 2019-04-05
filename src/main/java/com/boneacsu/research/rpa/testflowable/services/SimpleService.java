package com.boneacsu.research.rpa.testflowable.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service("simpleService")
public class SimpleService {
    public void doSimpleSynchCall(String var1, String var2) {
        log.info("executing simple synch call with vars {} and {}", var1, var2);
    }
}
