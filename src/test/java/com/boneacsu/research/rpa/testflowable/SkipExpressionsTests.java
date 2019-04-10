package com.boneacsu.research.rpa.testflowable;

import com.boneacsu.research.rpa.testflowable.services.ServiceThrowingError;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class SkipExpressionsTests {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ServiceThrowingError serviceThrowingError;

    @Test
    public void contextLoads() throws Exception{
        Map<String, Object> varsInit = new HashMap<>();
        varsInit.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);
        varsInit.put("skipUserTask2", true);

        ProcessInstance processInstance =
                this.runtimeService.startProcessInstanceByKey(
                        "deployProcessComponent", varsInit);
        String processInstanceId = processInstance.getId();

        log.info("Process instance ID: {} ", processInstanceId);

        Assert.assertNotNull(processInstanceId);
    }
}