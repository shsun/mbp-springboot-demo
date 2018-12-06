package com.as.cyems.controller;

import java.util.Date;

import com.as.cyems.domain.BakUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.as.cyems.ErrorCode;
import com.as.cyems.domain.enums.AgeEnum;
import com.as.cyems.domain.enums.PhoneEnum;
import com.as.cyems.service.IBakUserService;

/**
 * 代码生成器，参考源码测试用例：
 * <p>
 * /mybatis-plus/src/test/java/com/baomidou/mybatisplus/test/generator/MysqlGenerator.java
 *
 *
 *  TODO
 *
 * swagger-api ref
 * https://blog.csdn.net/xupeng874395012/article/details/68946676/
 */
@Api(tags = "用户信息的增删改查")
@RestController
@RequestMapping("/bakuser")
public class BakUserController extends ApiController {

    @Autowired
    private IBakUserService userService;

    /**
     * <p>
     * 测试通用 Api Controller 逻辑
     * </p>
     * 测试地址：
     * http://localhost:8080/user/api
     * http://localhost:8080/user/api?test=mybatisplus
     */
    @GetMapping("/api")
    public R<String> testError(String test) {
        Assert.notNull(ErrorCode.TEST, test);
        return success(test);
    }

    /**
     * http://localhost:8080/user/test
     */
    @GetMapping("/test")
    public IPage<BakUser> test() {
        return userService.page(new Page<BakUser>(0, 12), null);
    }

    /**
     * AR 部分测试
     * http://localhost:8080/user/test1
     */
    @GetMapping("/test1")
    public IPage<BakUser> test1() {
        BakUser user = new BakUser("testAr", AgeEnum.ONE, 1);
        System.err.println("删除所有：" + user.delete(null));
        user.setRole(111L);
        user.setTestDate(new Date());
        user.setPhone(PhoneEnum.CMCC);
        user.insert();
        System.err.println("查询插入结果：" + user.selectById().toString());
        user.setName("mybatis-plus-ar");
        System.err.println("更新：" + user.updateById());
        return user.selectPage(new Page<BakUser>(0, 12), null);
    }

    /**
     * 增删改查 CRUD
     * http://localhost:8080/user/test2
     */
    @GetMapping("/test2")
    public BakUser test2() {
        System.err.println("删除一条数据：" + userService.removeById(1L));
        System.err.println("deleteAll：" + userService.deleteAll());
        System.err.println("插入一条数据：" + userService.save(new BakUser(1L, "张三", AgeEnum.TWO, 1)));
        BakUser user = new BakUser("张三", AgeEnum.TWO, 1);
        boolean result = userService.save(user);
        // 自动回写的ID
        Long id = user.getId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
        System.err.println("查询：" + userService.getById(id).toString());
        System.err.println("更新一条数据：" + userService.updateById(new BakUser(1L, "三毛", AgeEnum.ONE, 1)));
        for (int i = 0; i < 5; ++i) {
            userService.save(new BakUser(Long.valueOf(100 + i), "张三" + i, AgeEnum.ONE, 1));
        }
        IPage<BakUser> userListPage = userService.page(new Page<BakUser>(1, 5), new QueryWrapper<BakUser>());
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());

        userListPage = userService.page(new Page<BakUser>(1, 5), new QueryWrapper<BakUser>().orderByDesc("name"));
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        return userService.getById(1L);
    }

    /**
     * 插入 OR 修改
     * http://localhost:8080/user/test3
     */
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @GetMapping("/test3")
    public BakUser test3() {
        BakUser user = new BakUser(1L, "王五", AgeEnum.ONE, 1);
        user.setPhone(PhoneEnum.CT);
        userService.saveOrUpdate(user);
        return userService.getById(1L);
    }

    /**
     * http://localhost:8080/user/add
     */
    @GetMapping("/add")
    public Object addUser() {
        BakUser user = new BakUser("张三'特殊`符号", AgeEnum.TWO, 1);
        user.setPhone(PhoneEnum.CUCC);
        return userService.save(user);
    }

    /**
     * http://localhost:8080/user/select_sql
     */
    @GetMapping("/select_sql")
    public Object getUserBySql() {
        return userService.selectListBySQL();
    }

    /**
     * http://localhost:8080/user/select_wrapper
     */
    @GetMapping("/select_wrapper")
    public Object getUserByWrapper() {
        return userService.selectListByWrapper(new QueryWrapper<BakUser>()
                .lambda().like(BakUser::getName, "tom")
                .or(e -> e.like(BakUser::getName, "jim")));
    }

    /**
     * <p>
     * 参数模式分页
     * </p>
     * <p>
     * 7、分页 size 一页显示数量  current 当前页码
     * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
     * <p>
     * 集合模式，不进行分页直接返回所有结果集：
     * http://localhost:8080/user/page?listMode=true
     */

//    @ApiOperation(value = "测试用接口", notes = "测试用接口", httpMethod = "GET")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "form"),
//            @ApiImplicitParam(name = "id", value = "id", dataType = "int", required = false, paramType = "form")})
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
     * 测试事物
     * http://localhost:8080/user/test_transactional<br>
     * 访问如下并未发现插入数据说明事物可靠！！<br>
     * http://localhost:8080/user/test<br>
     * <br>
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test_transactional")
    public void testTransactional() {
        BakUser user = new BakUser(1000L, "测试事物", AgeEnum.ONE, 3);
        userService.save(user);
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }
}