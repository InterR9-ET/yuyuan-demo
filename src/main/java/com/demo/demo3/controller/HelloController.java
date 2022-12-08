package com.demo.demo3.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.demo3.annotation.SecurityParameter;
import com.demo.demo3.dto.LoginReq;
import com.demo.demo3.entity.UserEntity;
import com.demo.demo3.service.FangGaoService;
import com.demo.demo3.utils.AesUtils;
import com.demo.demo3.vo.RegistrationInfoVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.demo.demo3.utils.CheckNotNullUtils.checkNotNull;

@RestController
@Log4j2
public class HelloController {

    @Autowired
    FangGaoService fengGaoService;

    private static final String KEY = "1234123412ABCDEF";

    @PostMapping("/say")
    public String sayHello(@RequestBody UserEntity userEntity) throws Exception {
        System.out.println("---进入Controller---");
        Map map=new HashMap<String,String>();
        map.put("id",userEntity.getId());
        map.put("name",userEntity.getName());
        map.put("age",userEntity.getAge());
        map.put("content",userEntity.getContent());
        String content = JSONObject.toJSONString(map);
        System.out.println("加密前：" + content);
        String encrypt = AesUtils.encrypt(content, KEY);
        System.out.println("加密后：" + encrypt);
        String decrypt = AesUtils.decrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
        System.out.println("---Controller-处理完毕--");

        String outVo=fengGaoService.sendMemberRegist();
        return outVo;
    }

    @SecurityParameter
    @PostMapping("/hello")
    public String sayHai(@RequestBody LoginReq loginReq) throws Exception {
        System.out.println("---进入Controller---");
        //1.解密报文
        System.out.println("解密前：" + loginReq.getContent());
        String decrypt = AesUtils.decrypt(loginReq.getContent(), KEY);
        System.out.println("解密后：" + decrypt);
        //2.处理解密后的字段，把他赋值给对象
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationInfoVo registrationInfoVo = objectMapper.readValue(decrypt, RegistrationInfoVo.class);
        log.info("String转换成对象："+registrationInfoVo);
        Map map = new HashMap();
        map.put("id", "id不能为空");
        map.put("name", "名字不能为空");
        map.put("height", "身高不能为空");
        String param = checkNotNull(registrationInfoVo, map);
        System.out.println(param);
        //3.调serice处理
        String outVo=fengGaoService.sendRegist(registrationInfoVo);
        System.out.println("---Controller-处理完毕--");
        return outVo;
    }
}
