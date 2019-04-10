package com.boneacsu.research.rpa.testflowable.services;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.BpmnError;
import org.springframework.stereotype.Service;

@Service("serviceWithError")
@Log4j2
public class ServiceThrowingError {
    public static String ERROR_CODE = "ERROR_CALLING_EXTERNAL_SERVICE";
    public static String VAR_NOTHROW = "NOTHROW";

    public void execute(boolean NOTHROW)
    {
        log.info("Executing service with error with value '{}'.", NOTHROW);

        if (NOTHROW)
            return;
        throw new BpmnError(ERROR_CODE, "Error thrown");
    }
}
