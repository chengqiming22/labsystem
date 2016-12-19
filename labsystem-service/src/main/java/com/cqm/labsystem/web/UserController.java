package com.cqm.labsystem.web;

import com.cqm.labsystem.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qmcheng on 2016/12/15 0015.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static Map<String, User> userMap = new HashMap<String, User>();

    static {
        userMap.put("1", createUser("1"));
        userMap.put("2", createUser("2"));
        userMap.put("3", createUser("3"));
        userMap.put("4", createUser("4"));
    }

    private static User createUser(String userId) {
        User user = new User();
        user.setId(userId);
        user.setName("user" + userId);
        user.setPassword("123456");
        user.setEmail("user" + userId + "@xxx.com");
        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<User> getList() {
        return userMap.values();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(@RequestBody User user) {
        if(userMap.containsKey(user.getId())){
            return "新增用户失败";
        }
        userMap.put(user.getId(), user);
        return "新增用户成功，" + user.getName();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String update(@RequestBody User user) {
        if (!userMap.containsKey(user.getId())) {
            return "更新用户失败";
        }
        userMap.put(user.getId(),user);
        return "更新用户成功，" + user.getName();
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public Object get(@PathVariable String userId){
        if(!userMap.containsKey(userId)){
            return "用户不存在";
        }
        return userMap.get(userId);
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.DELETE)
    public String delete(@PathVariable String userId){
        if(!userMap.containsKey(userId)){
            return "用户不存在";
        }
        userMap.remove(userId);
        return "删除用户成功";
    }
}
