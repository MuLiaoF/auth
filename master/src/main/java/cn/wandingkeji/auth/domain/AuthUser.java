package cn.wandingkeji.auth.domain;


import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_user")
public class AuthUser extends SuperEntity<AuthUser> {

    private String id;
    private String name;
    private String userId;
    private String loginAct;
    private String salt;
    private String loginPwd;
}
