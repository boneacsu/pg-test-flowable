package com.boneacsu.research.rcs.testflowable;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class TestFlowableApplicationTests {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;


	@Test
	public void contextLoads() throws Exception{
		String customerId = "1";
		String processInstanceId = this.beginCustomerEnrollmentProcess(customerId, "email@email.com");
		log.info("process instance ID: {} ", processInstanceId);

		Assert.assertNotNull(processInstanceId);


		//asynch
		this.confirmEmail(customerId);

		//
	}

	String beginCustomerEnrollmentProcess(String customerId, String email) {
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey("signup-process", );
		return processInstance.getId();
	}

	private void confirmEmail(String customerId) {
	}
}