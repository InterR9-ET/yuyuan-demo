package com.demo.demo3.utils;

import com.demo.demo3.entity.Card;
import com.demo.demo3.vo.RegistrationInfoVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类：字段转换类
 */
public class CheckNotNullUtils {
    /**
     * 方法描述：String转Object
     * @param joinVal
     * @param obj
     * @param map 不要校验的字段
     */
    public void StringToObjectConverter(String joinVal,Object obj,Map map) throws JsonProcessingException {
        //处理解密后的字段，把他赋值给对象
        ObjectMapper objectMapper = new ObjectMapper();
        RegistrationInfoVo registrationInfoVo = objectMapper.readValue("decrypt", RegistrationInfoVo.class);
    }

    /**
     * 判断一个对象中的某些字段是否为空
     *
     * @param obj 传入一个对象
     * @param map key为传入需要判断的是否为空的字段，value为传入需要提示的错误信息
     * @return 当判断出有为空的字段时，会返回错误信息，当传入的字段都不为空时，返回null
     * @throws IllegalAccessException
     */
    public static String checkNotNull(Object obj, Map<String, String> map) throws IllegalAccessException {
        Class aClass = obj.getClass();
        Field[] fs = aClass.getDeclaredFields();
        for (Field f : fs) {
            // 设置些属性是可以访问的
            f.setAccessible(true);
            if ((f != null) && (!"".equals(f))) {
                //设置权限
                f.setAccessible(true);
                Object o = f.get(obj);
                String fieldName = f.getName();
                if (map.containsKey(fieldName)) {
                    if (o == null) {
                        return map.get(fieldName);
                    }
                    if (o instanceof String) {
                        if (o == null || "".equals(((String) o).trim())) {
                            //TODO:后面改造成抛错处理
                            return map.get(fieldName);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IllegalAccessException {
        Card card = new Card();
//        card.setId(1);
        card.setName("w");
        Map map = new HashMap();
        map.put("id", "id不能为空");
        map.put("name", "名字不能为空");
        String param = checkNotNull(card, map);
        System.out.println(param);
    }
}
