package com.demo.demo3.service;

import com.demo.demo3.vo.RegistrationInfoVo;

public interface FangGaoService {
    /**
     * 方法描述：会员注册
     * @return
     */
    String sendMemberRegist();

    /**
     * 方法描述：线程 - 会员注册
     * @return
     */
    String sendRegist(RegistrationInfoVo registrationInfoVo) throws InterruptedException;
}
