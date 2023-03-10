package com.xxl.job.service.impl;

import com.alibaba.fastjson.JSON;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.service.MessagePushService;
import com.xxl.job.service.entity.MailBean;
import com.xxl.job.service.entity.MessageBean;
import com.xxl.job.service.utils.AliHttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送接口
 *
 * @author Qiin
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {

    private static Logger logger = LoggerFactory.getLogger(MessagePushServiceImpl.class);

    @Autowired
    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void messagePush() {
        //国阳云短信推送
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "8e2ff929470d45f9b672ee37490e09c1";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        String param = XxlJobHelper.getJobParam();
        MessageBean message = JSON.parseObject(param, MessageBean.class);
        querys.put("mobile", StringUtils.isEmpty(message.getMobile()) ? "13085512310" : message.getMobile());
        //获取当前日期
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        querys.put("param", "**name**:" + message.getName() + ",**date**:" + date + ",**place**:" + message.getPlace());
        XxlJobHelper.log("短信推送结参数......" + querys.toString());
        //smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html
        querys.put("smsSignId", "b37dc584132a45ed8a4a8a3fc70eedde");
        querys.put("templateId", "ad4d761badd54d60b3ab203e76aa891a");
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse response = AliHttpUtils.doPost(host, path, method, headers, querys, bodys);
            XxlJobHelper.log("短信推送结果......" + response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mailPush() {
        String param = XxlJobHelper.getJobParam();
        MailBean mail = JSON.parseObject(param, MailBean.class);
        String from = "827989320@qq.com";
        String to = mail.getMailTo();
        String subject = mail.getTitle();
        String context = mail.getContent();
        //推送
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from + "(qinpeiyang)");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);
        javaMailSender.send(message);
    }
}
