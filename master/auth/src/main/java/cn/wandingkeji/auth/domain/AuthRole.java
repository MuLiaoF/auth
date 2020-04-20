package cn.wandingkeji.auth.domain;

import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_role")
public class AuthRole extends SuperEntity<AuthRole> {

    private String name;
    private String desc;


}
