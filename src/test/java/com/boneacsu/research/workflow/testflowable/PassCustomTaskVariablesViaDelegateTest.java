package com.boneacsu.research.workflow.testflowable;

import com.google.common.collect.ImmutableMap;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
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

        HistoricVariableInstance varMap =
                historyService.createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .variableName("map")
                        .singleResult();

        Map<String, String> map = (Map<String, String>) varMap.getValue();

        Assert.assertNotNull(map);

        Assert.assertEquals(3, map.size());

        Assert.assertTrue(map.containsKey("one"));
        Assert.assertTrue(map.containsKey("two"));
        Assert.assertTrue(map.containsKey("three"));

        HistoricVariableInstance varComplex =
                historyService.createHistoricVariableInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .variableName("complexObjectViaEnv")
                        .singleResult();

        Assert.assertNotNull("varComplex");
    }
}
