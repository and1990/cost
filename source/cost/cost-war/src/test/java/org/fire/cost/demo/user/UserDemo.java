package org.fire.cost.demo.user;

import org.fire.cost.dao.UserDao;
import org.fire.cost.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:applicationContext.xml"})
public class UserDemo {
    @Resource
    private UserDao userDao;

    @Test
    public void add() {
        User user = new User();
        user.setUserName("刘腾飞");
        user.setLoginName("and1990");
        user.setUserAge(23);
        user.setUserAddress("昌平区");
        user.setUserEmail("123@123.com");
        user.setCreateTime(new Date());
        userDao.save(user);

        System.out.println("hello world");
    }
}
