package cn.wandingkeji.auth.domain;

import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;


@Data
@TableName("tbl_auth_dept")
public class AuthDept extends SuperEntity<AuthDept> {

    private String deptName;
    private String pid;

}
