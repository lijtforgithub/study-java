package com.ljt.study.lang.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LiJingTang
 * @date 2019-12-30 10:49
 */
public class CollectorsTest {


    /**
     * Collectors.toMap底层是基于Map.merge方法来实现的，而merge中value是不能为null的，如果为null，就会抛出空指针异常
     * value 不能为空
     */
    @Test
    public void toMap() {
        LambdaTest.User user = new LambdaTest.User(1, null, 1);
        List<LambdaTest.User> list = Collections.singletonList(user);

        Map<Object, Object> map = list.stream().collect(HashMap::new, (m, v) ->
                m.put(v.getId(), v.getName()), HashMap::putAll);
        System.out.println(map);

        list.stream().collect(Collectors.toMap(LambdaTest.User::getId, LambdaTest.User::getName));
    }

}
