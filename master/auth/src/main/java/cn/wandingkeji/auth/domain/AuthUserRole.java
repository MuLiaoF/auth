package cn.wandingkeji.auth.domain;


import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_user_role")
public class AuthUserRole extends SuperEntity<AuthUserRole> {

    private String userId;
    private String deptRoleId;

}
