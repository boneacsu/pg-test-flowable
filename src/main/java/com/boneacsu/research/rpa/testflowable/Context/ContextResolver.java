package com.boneacsu.research.rpa.testflowable.Context;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.Execution;
import org.springframework.stereotype.Service;

@Service("contextResolverService")
@Log4j2
public class ContextResolver {
    public boolean shouldSkipTask(DelegateExecution execution)
    {
        log.info("Skipping task {}", execution.getCurrentFlowElement().getName());
        return true;
    }
}
