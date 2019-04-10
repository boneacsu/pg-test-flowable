package com.boneacsu.research.rpa.testflowable;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
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
public class CallActivityByCallingOtherWorkflowsTests {

	public static final String CONFIRM_EMAIL_USER_TASK = "confirm-email-user-task";

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	private static final String VAR1 = "1";
	private static final String VAR2 = "2";

	@Test
	public void contextLoads() throws Exception{
		String processInstanceId = this.startWorkflow(VAR1, VAR2);
		log.info("process instance ID: {} ", processInstanceId);

		Assert.assertNotNull(processInstanceId);

		String downloadActivityProcessInstanceId =
				runtimeService.createProcessInstanceQuery()
						.processDefinitionKey("downloadProcessComponent")
						.singleResult()
						.getProcessInstanceId();

        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(downloadActivityProcessInstanceId)
                .activityId("executeRemoteAsynchCallByAgentDelegation")
                .singleResult();

        Assert.assertNotNull(execution);

        Thread.sleep(5000);
        Map<String,Object> processVariables = new HashMap<>();
        processVariables.put("supposeWeReceiveThisVariableFromTheRemoteWorker", "someRandomValue");

        runtimeService.trigger(execution.getId(), processVariables, null);
	}

	String startWorkflow(String customerId, String email) {
		Map<String, Object> vars = new HashMap<>();
		vars.put("var1", VAR1);
		vars.put("var2", VAR2);
		vars.put("skips1", false);
		vars.put("skipu1", false);
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey("upgrade-process", vars);
		return processInstance.getId();
	}
}