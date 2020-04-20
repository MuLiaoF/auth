package cn.wandingkeji.auth.domain;


import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_user_per")
public class AuthUserPermission extends SuperEntity<AuthUserPermission> {

    private String userId;
    private String perId;

}
