package com.boneacsu.research.rcs.testflowable;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class TestFlowableApplicationTests {

	public static final String CONFIRM_EMAIL_USER_TASK = "confirm-email-user-task";
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;

	private static final String CUSTOMER_ID = "customerId";
	private static final String EMAIL = "email@email.com";

	@Test
	public void contextLoads() throws Exception{
		String processInstanceId = this.beginCustomerEnrollmentProcess(CUSTOMER_ID, EMAIL);
		log.info("process instance ID: {} ", processInstanceId);

		Assert.assertNotNull(processInstanceId);

		//task cleaning and completing

		List<Task> tasks = this.taskService
				.createTaskQuery()
				.taskDefinitionKey(CONFIRM_EMAIL_USER_TASK)
				.includeProcessVariables()
				.list();

		Assert.assertTrue("there should be one outstanding", tasks.size() >= 1 );

		log.info("All tasks : {}", tasks);

		tasks.forEach( task -> {
				this.taskService.claim(task.getId(), "");
				this.taskService.complete(task.getId());
			}
		);
	}

	String beginCustomerEnrollmentProcess(String customerId, String email) {
		Map<String, Object> vars = new HashMap<>();
		vars.put(CUSTOMER_ID, customerId);
		vars.put(EMAIL, email);
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey("signup-process", vars);
		return processInstance.getId();
	}

	private void confirmEmail(String customerId) {
	}
}