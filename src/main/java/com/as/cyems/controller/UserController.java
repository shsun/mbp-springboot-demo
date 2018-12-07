package com.as.cyems.controller;

import base.utils.XAsReflectorUtil;
import com.as.cyems.ErrorCode;
import com.as.cyems.domain.User;
import com.as.cyems.form.UserForm;
import com.as.cyems.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "user表的增删改查")
@RestController
@RequestMapping("/user")
public class UserController extends ApiController {

    @Autowired
    private IUserService userService;

    /**
     * <p>
     * 测试通用 Api Controller 逻辑
     * </p>
     * 测试地址：
     * http://localhost:8080/zuser/api
     * http://localhost:8080/zuser/api?message=mybatisplus
     */
    @ApiOperation(value = "测试ok", notes = "该测试用于对swagger有一个感性的认识, 没有任何业务含义", httpMethod = "GET")
    @GetMapping("/api_ok_string")
    public R<String> testOkString(String message) {
        Assert.notNull(ErrorCode.TEST, message);
        R<String> r = R.ok(message);
        return r;
    }

    @ApiOperation(value = "测试ok", notes = "该测试用于对swagger有一个感性的认识, 没有任何业务含义", httpMethod = "GET")
    @GetMapping("/api_ok_map")
    public R<Map<String, Object>> testOkMap(String message) {
        Assert.notNull(ErrorCode.TEST, message);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "老孙");
        map.put("age", 55);
        map.put("sex", 1);
        R<Map<String, Object>> r = R.ok(map);
        return r;
    }

    @ApiOperation(value = "测试ok", notes = "该测试用于对swagger有一个感性的认识, 没有任何业务含义", httpMethod = "GET")
    @GetMapping("/api_ok_list")
    public R<List<User>> testOkList(String message) {
        Assert.notNull(ErrorCode.TEST, message);
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(new User("老孙_" + i, "" + i));
        }
        R<List<User>> r = R.ok(list);
        return r;
    }


    @ApiOperation(value = "测试failed", notes = "该测试用于对swagger有一个感性的认识, 没有任何业务含义", httpMethod = "GET")
    @GetMapping("/api_failed")
    public R<String> testError(String message) {
        Assert.notNull(ErrorCode.TEST, message);
        R<String> r = R.failed(message);
        return r;
    }


    /**
     * 测试分页, size为12
     * http://localhost:8080/zuser/test
     */
    @GetMapping("/testPage")
    public IPage<User> test() {
        IPage<User> p = userService.page(new Page<User>(0, 12), null);
        return p;
    }

    /**
     * insert&select 部分测试
     * http://localhost:8080/zuser/test1
     */
    @ApiOperation(value = "测试insert&select", notes = "先删除全部,再新增,再更新", httpMethod = "GET")
    @GetMapping("/test1")
    public IPage<User> test1() {
        User user = new User();
        System.err.println("删除所有：" + user.delete(null));
        user.setName("test1");
        user.setRoleKey("1");
        user.insert();
        System.err.println("查询插入结果：" + user.selectById().toString());
        //
        user.setName("mybatis-plus-ar");
        System.err.println("更新：" + user.updateById());
        IPage<User> p = user.selectPage(new Page<User>(0, 12), null);
        return p;
    }

    /**
     * 增删改查 CRUD
     * http://localhost:8080/zuser/test2
     */
    @ApiOperation(value = "增删改查 CRUD", notes = "增删改查 CRUD", httpMethod = "GET")
    @GetMapping("/test2")
    public User test2() {
        //
        System.err.println("删除一条数据：" + userService.removeById(1L));
        System.err.println("deleteAll：" + userService.deleteAll());

        // save
        User u1 = new User();
        u1.setName("test2");
        u1.setRoleKey("2");
        System.err.println("插入一条数据：" + userService.save(u1));

        // save
        User u2 = new User();
        u2.setName("test3");
        u2.setRoleKey("1");
        boolean result = userService.save(u2);

        // 自动回写的ID
        Long id = u2.getTestId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + u2.toString());
        System.err.println("查询：" + userService.getById(id).toString());

        User u3 = new User();
        u3.setTestId(1L);
        u3.setName("三毛");
        u3.setRoleKey("1");
        System.err.println("更新一条数据：" + userService.updateById(u3));

        //
        for (int i = 0; i < 5; ++i) {
            User tt = new User();
            // tt.setTestId(Long.valueOf(100 + i));
            tt.setName("三毛_" + i);
            tt.setRoleKey("1");
            userService.save(tt);
        }
        IPage<User> userListPage = userService.page(new Page<User>(1, 5), new QueryWrapper<User>());
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());

        userListPage = userService.page(new Page<User>(1, 5), new QueryWrapper<User>().orderByDesc("name"));
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        return userService.getById(1L);
    }


    /**
     * 插入 OR 修改
     * http://localhost:8080/zuser/test3
     */

    @ApiOperation(value = "创建用户", notes = "根据ZUser对象创建用户")
    @GetMapping("/test3")
    public User test3() {
        User user = new User();
        user.setName("王五");
        user.setRoleKey("1");
        userService.saveOrUpdate(user);
        return userService.getById(1L);
    }


    /**
     * http://localhost:8080/zuser/add
     */
    @ApiOperation(value = "测试添加用户信息", notes = "测试事务接口", httpMethod = "POST")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "roleKey", value = "用户角色key", dataType = "String", required = false, paramType = "form"),
//            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "age", value = "用户年龄", dataType = "Integer", required = false, paramType = "form")})
    @PostMapping("/add")
    public boolean addZUser(@RequestBody UserForm form) {
        User user = new User();
        user = (User) XAsReflectorUtil.merge(form, user);
        boolean b = userService.save(user);
        //R<User> r = b ? R.ok(user) : R.failed(ErrorCode.TEST);
        //return r;
        return b;
    }

    /**
     * http://localhost:8080/zuser/select_sql
     */
    @ApiOperation(value = "该示例演示的是SQL语句直接写到mapper内, 而不是写在mapping内， 一般不推荐这样的用法", notes = "测试用接口", httpMethod = "GET")
    @GetMapping("/select_sql")
    public Object getZUserBySql() {
        return userService.selectListBySQL();
    }

    /**
     * http://localhost:8080/zuser/select_wrapper
     */
    @ApiOperation(value = "该示例演示的是查询条件写在代码内， 不写在mapping内", notes = "测试用接口", httpMethod = "GET")
    @GetMapping("/select_wrapper")
    public Object getZUserByWrapper() {
        return userService.selectListByWrapper(new QueryWrapper<User>()
                .lambda().like(User::getName, "二")
                .or(e -> e.like(User::getName, "一")));
    }

    /**
     * <p>
     * 参数模式分页
     * </p>
     * <p>
     * 7、分页 size 一页显示数量  current 当前页码
     * 方式一：http://localhost:8080/zuser/page?size=1&current=1<br>
     */
    @ApiOperation(value = "测试用接口", notes = "测试用接口", httpMethod = "GET")
    @GetMapping("/page")
    public IPage page(Page page) {
        return userService.page(page, null);
    }

    /**
     * 测试事务(创建新用户)<br>
     * http://localhost:8080/zuser/test_transactional_name<br>
     * 访问如下并未发现插入数据说明事务功能ok<br>
     */
    @ApiOperation(value = "测试事务接口----仅传递用户姓名", notes = "测试事务接口", httpMethod = "GET")
    @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true)
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test_transactional_name")
    public void testTransactional(String name) {
        UserForm form = new UserForm();
        form.setName(name);
        testTransactional(form);
    }

    /**
     * 测试事务(创建新用户)<br>
     * http://localhost:8080/zuser/test_transactional_form<br>
     * 访问如下并未发现插入数据说明事务功能ok<br>
     */
    @ApiOperation(value = "测试事务接口----传递用户多项信息", notes = "测试事务接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleKey", value = "用户角色key", dataType = "String", required = false, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "form"),
            @ApiImplicitParam(name = "age", value = "用户年龄", dataType = "Integer", required = false, paramType = "form")})
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/test_transactional_form")
    public void testTransactional(@RequestBody UserForm form) {
        User user = new User();
        user = (User) XAsReflectorUtil.merge(form, user);
        userService.save(user);
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }
}
