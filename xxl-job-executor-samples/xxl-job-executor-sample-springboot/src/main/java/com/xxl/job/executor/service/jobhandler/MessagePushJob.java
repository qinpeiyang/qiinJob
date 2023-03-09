package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.service.MessagePushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MessagePushJob {

    private static Logger logger = LoggerFactory.getLogger(MessagePushJob.class);

    @Autowired
    private MessagePushService messagePushService;

    @XxlJob("messagePushHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("短信推送开始......");
        messagePushService.messagePush();

    }

}
