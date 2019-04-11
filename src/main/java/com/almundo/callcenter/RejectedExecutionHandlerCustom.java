package com.almundo.callcenter;

import com.almundo.callcenter.Constants.OperatorMessagges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Effie on 8/4/2019.
 */
public class RejectedExecutionHandlerCustom implements RejectedExecutionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RejectedExecutionHandlerCustom.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.info("Call Rejected : " + ((Call) r).getDuration());
        logger.info(OperatorMessagges.HIGH_CALL_VOLUME);
    }
}
