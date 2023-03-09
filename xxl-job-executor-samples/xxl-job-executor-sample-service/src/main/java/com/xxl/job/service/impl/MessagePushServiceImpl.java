package com.xxl.job.service.impl;

import com.xxl.job.service.MessagePushService;
import org.springframework.stereotype.Service;

/**
 * 推送接口
 *
 * @author Qiin
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {

    @Override
    public void messagePush() {
        System.out.println("短信发送测试");
    }
}
