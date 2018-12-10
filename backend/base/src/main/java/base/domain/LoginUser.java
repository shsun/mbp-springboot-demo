package base.domain;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * @author who
 */
public class LoginUser implements Serializable {

    private Integer id;
    private String loginName;
    private String name;

    public LoginUser(Integer id, String loginName, String name) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("loginName", loginName)
                .add("name", name)
                .toString();
    }
}
