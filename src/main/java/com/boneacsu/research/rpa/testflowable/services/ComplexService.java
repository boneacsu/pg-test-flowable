package com.boneacsu.research.rpa.testflowable.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service("complexService")
public class ComplexService {

    ObjectMapper mapper = new ObjectMapper();

    public void processMap(Map<String, String> map)
    {
        log.info("Simple processing of a map : {} ", map);
    }

    public ObjectNode createComplexObject(DelegateExecution ex)
    {
        log.info("Creating complex object ... ");

        ObjectNode node = mapper.createObjectNode();

        ArrayNode array = node.putArray("listOfStrings");

        array.add("String1");
        array.add("String2");

        node.putPOJO("listOfStrings", array);

        node.put("additionalField", "additionalFieldValue");

        log.info("Complex object has been built : {}", node);

        ex.setVariable("complexObjectViaEnv" ,node);

        return node;
    }

    public void processComplexObject(ObjectNode complexObjectViaReturnContext)
    {
        log.info("Simple processing of a complex : {} ", complexObjectViaReturnContext);
    }

    public void produceInterimProcessVar(DelegateExecution ex)
    {
        ex.setVariable("taskOutput", "taskOutputValue");

        log.info("Have produced the following output : {} ", ex.getVariable("taskOutput"));
    }

    public void consumeInterimProcessVar(DelegateExecution ex)
    {
        log.info("Consuming the following input(s) : {} and {}",
                ex.getVariable("complexObjectViaEnv"),
                ex.getVariable("taskOutput"));
    }
}
