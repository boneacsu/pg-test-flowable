package com.boneacsu.research.rpa.testflowable;

import com.boneacsu.research.rpa.testflowable.services.ServiceThrowingError;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
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
public class SimpleServiceExplicitRetryAndErrorHandlingTests {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ServiceThrowingError serviceThrowingError;

    @Test
    public void contextLoads() throws Exception{
        Map<String, Object> varsInit = new HashMap<>();
        varsInit.put(ServiceThrowingError.VAR_NOTHROW, false);

        ProcessInstance processInstance =
                this.runtimeService.startProcessInstanceByKey(
                        "errorHandlingProcess2", varsInit);
        String processInstanceId = processInstance.getId();

        log.info("process instance ID: {} ", processInstanceId);

        Assert.assertNotNull(processInstanceId);

        //get the pending user task that should handle the error outside the system
        List<Task> tasks = this.taskService
                .createTaskQuery()
                .taskDefinitionKey("handleErrorUserTask")
                .includeProcessVariables()
                .list();

        Assert.assertTrue("there should be one outstanding", tasks.size() >= 1 );

        log.info("All tasks : {}", tasks);

        // setup next cycle to not throw exception again
        // and var for the seq flow to retry service execution
        varsInit.put(ServiceThrowingError.VAR_NOTHROW, true);
        varsInit.put("retry", true);

        //SIMULATE user has fixed the root cause and the process should be retried
        tasks.forEach( task -> {
                    this.taskService.complete(task.getId(), varsInit);
                }
        );

        Assert.assertEquals(
                0L,
                this.runtimeService.createProcessInstanceQuery()
                        .processInstanceId(processInstanceId).count());
    }
}
