package com.youxu.shardingjdbcdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youxu.shardingjdbcdemo.entity.Course;
import com.youxu.shardingjdbcdemo.entity.User;
import com.youxu.shardingjdbcdemo.mapper.CourseMapper;
import com.youxu.shardingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Resource
    private CourseMapper courseMapper;

    //====================水平分表======================
    @Test
    void addCourse() {
        for(int i = 0; i < 10; i++){
            Course course = new Course();
            course.setCname("java"+i);
            course.setUserId(100L+i);
            course.setCstatus("Normal");
            courseMapper.insert(course);
        }
    }

    @Test
    void queryCourse(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("cstatus","Normal");
        map.put("user_id",100L);
        List<Course> courses = courseMapper.selectByMap(map);
        courses.forEach(System.out::println);
    }

    //====================水平分库======================
    @Test
    void addCourseDb(){
        for(int i = 0; i < 10; i++){
            Course course = new Course();
            course.setCname("java"+i);
            course.setUserId(101L);
            course.setCstatus("Normal");
            courseMapper.insert(course);
        }
    }

    @Test
    void queryCourseDb(){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",481232113694146561L);
        queryWrapper.eq("user_id",101);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }

    //====================垂直分库======================
    @Resource
    private UserMapper userMapper;
    @Test
    void addUser(){
        for(int i = 0; i < 10; i++){
            User user = new User();
            user.setUsername("youxu");
            user.setUstatus("ok");
            userMapper.insert(user);
        }
    }
}
