package com.boneacsu.research.rpa.testflowable.task;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;

import java.io.Serializable;

@Log4j2
public class TriggerableServiceTask implements JavaDelegate, TriggerableActivityBehavior, Serializable {
    @Override
    public void execute(DelegateExecution execution) {
        log.info("Executing some awesome work from a triggerable service task");
    }

    @Override
    public void trigger(DelegateExecution execution, String signalEvent, Object signalData) {
        log.info("This task has received a trigger event {} with some data {} ", signalEvent, signalData);

        if(execution.hasVariable("supposeWeReceiveThisVariableFromTheRemoteWorker"))
        {
            String variable = (String) execution.getVariable("supposeWeReceiveThisVariableFromTheRemoteWorker");
            execution.setVariable("supposeWeReceiveThisVariableFromTheRemoteWorker", "someNewAlteredRandomValue");
        }
    }
}
