package cn.wandingkeji.auth.domain;

import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;


@Data
@TableName("tbl_auth_role_per")
public class AuthRolePermission extends SuperEntity<AuthRolePermission> {

    private String deptRoleId;
    private String perId;
    private String roleId;
    private String deptId;

}
