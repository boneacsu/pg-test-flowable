package com.boneacsu.research.rpa.testflowable;

import com.boneacsu.research.rpa.testflowable.services.ServiceThrowingError;
import com.google.common.collect.ImmutableMap;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class PassCustomTaskVariablesViaDelegateTest {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ServiceThrowingError serviceThrowingError;

    @Autowired
    private HistoryService historyService;

    @Test
    public void contextLoads(){
        Map<String, Object> varsInit = new HashMap<>();

        varsInit.put("map", new ImmutableMap.Builder<String, String>()
                                    .put("one", "val1")
                                    .put("two", "val2")
                                    .put("three", "val3")
                                    .build());

        ProcessInstance processInstance =
                this.runtimeService.startProcessInstanceByKey(
                        "complexVariableProcess", varsInit);

        String processInstanceId = processInstance.getId();

        log.info("Process instance ID: {} ", processInstanceId);

        Assert.assertNotNull(processInstanceId);
    }
}