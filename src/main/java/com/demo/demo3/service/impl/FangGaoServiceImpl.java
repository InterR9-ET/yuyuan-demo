package com.demo.demo3.service.impl;

import com.demo.demo3.service.FangGaoService;
import com.demo.demo3.utils.ThreadPoolUtils;
import com.demo.demo3.vo.RegistrationInfoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Log4j2
public class FangGaoServiceImpl implements FangGaoService {
//    @Async(ExecutorConfig.TASK_NAME)
    @Override
    public String sendMemberRegist() {
        log.info("开始调用峰高接口");
        //调用峰高提供的接口
        String result="注册成功！";
        log.info("结束调用峰高接口");
        return result;
    }

    @Override
    public String sendRegist(RegistrationInfoVo registrationInfoVo) throws InterruptedException {
        log.info("开始调用峰高接口");
        //调用峰高提供的接口
        ThreadPoolUtils.sendCard();
        String result="注册成功！";
        log.info("结束调用峰高接口");
        return result;
    }


}
