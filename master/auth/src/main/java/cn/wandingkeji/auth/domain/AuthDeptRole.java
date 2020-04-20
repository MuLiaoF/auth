package cn.wandingkeji.auth.domain;

import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_dept_role")
public class AuthDeptRole extends SuperEntity<AuthDeptRole> {

    private String deptId;
    private String roleId;
    private String pid;
}
