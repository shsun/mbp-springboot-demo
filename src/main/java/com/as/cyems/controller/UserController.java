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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
     * http://localhost:8080/zuser/api?test=mybatisplus
     */
    @GetMapping("/api")
    public R<String> testError(String test) {
        Assert.notNull(ErrorCode.TEST, test);
        R<String> r = success(test);
        return r;
    }

    /**
     * 测试分页, size为12
     * http://localhost:8080/zuser/test
     */
    @GetMapping("/testPage")
    public IPage<User> test() {
        return userService.page(new Page<User>(0, 12), null);
    }

    /**
     * AR 部分测试
     * http://localhost:8080/zuser/test1
     */
    /*
    @GetMapping("/test1")
    public IPage<User> test1() {
        User user = new User("testAr", AgeEnum.ONE, 1);
        System.err.println("删除所有：" + user.delete(null));
        user.setRole(111L);
        user.setTestDate(new Date());
        user.setPhone(PhoneEnum.CMCC);
        user.insert();
        System.err.println("查询插入结果：" + user.selectById().toString());
        user.setName("mybatis-plus-ar");
        System.err.println("更新：" + user.updateById());
        return user.selectPage(new Page<User>(0, 12), null);
    }
    */

    /**
     * 增删改查 CRUD
     * http://localhost:8080/zuser/test2
     */
    /*
    @GetMapping("/test2")
    public User test2() {
        System.err.println("删除一条数据：" + userService.removeById(1L));
        System.err.println("deleteAll：" + userService.deleteAll());
        System.err.println("插入一条数据：" + userService.save(new User(1L, "张三", AgeEnum.TWO, 1)));
        User user = new User("张三", AgeEnum.TWO, 1);
        boolean result = userService.save(user);
        // 自动回写的ID
        Long id = user.getId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
        System.err.println("查询：" + userService.getById(id).toString());
        System.err.println("更新一条数据：" + userService.updateById(new User(1L, "三毛", AgeEnum.ONE, 1)));
        for (int i = 0; i < 5; ++i) {
            userService.save(new User(Long.valueOf(100 + i), "张三" + i, AgeEnum.ONE, 1));
        }
        IPage<User> userListPage = userService.page(new Page<User>(1, 5), new QueryWrapper<User>());
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());

        userListPage = userService.page(new Page<User>(1, 5), new QueryWrapper<User>().orderByDesc("name"));
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        return userService.getById(1L);
    }
    */

    /**
     * 插入 OR 修改
     * http://localhost:8080/zuser/test3
     */
    /*
    @ApiOperation(value = "创建用户", notes = "根据ZUser对象创建用户")
    @GetMapping("/test3")
    public User test3() {
        User user = new User(1L, "王五", AgeEnum.ONE, 1);
        user.setPhone(PhoneEnum.CT);
        userService.saveOrUpdate(user);
        return userService.getById(1L);
    }
    */

    /**
     * http://localhost:8080/zuser/add
     */
    @ApiOperation(value = "测试添加用户信息", notes = "测试事务接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleKey", value = "用户角色key", dataType = "String", required = false, paramType = "form"),
            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "form"),
            @ApiImplicitParam(name = "age", value = "用户年龄", dataType = "Integer", required = false, paramType = "form")})
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
     *
     *
     * http://localhost:8080/zuser/select_sql
     */
    @ApiOperation(value = "该示例演示的是SQL语句直接写到mapper内, 而不是写在mapping内， 一般不推荐这样的用法", notes = "测试用接口", httpMethod = "GET")
    @GetMapping("/select_sql")
    public Object getZUserBySql() {
        return userService.selectListBySQL();
    }

    /**
     *
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
     * <p>
     * 集合模式，不进行分页直接返回所有结果集：
     * http://localhost:8080/zuser/page?listMode=true
     */
    @ApiOperation(value = "测试用接口", notes = "测试用接口", httpMethod = "GET")
    @GetMapping("/page")
    public IPage page(Page page, boolean listMode) {
        if (listMode) {
            // size 小于 0 不在查询 total 及分页，自动调整为列表模式。
            // 注意！！这个地方自己控制好！！
            page.setSize(-1);
        }
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
    public void testTransactional(UserForm form) {
        User user = new User();
        user = (User) XAsReflectorUtil.merge(form, user);
        userService.save(user);
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }
}