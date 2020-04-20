package cn.wandingkeji.auth.domain;


import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_per")
public class AuthPermission extends SuperEntity<AuthPermission> {

    private String id;
    private String name;
    private String url;
    private String desc;
    private String filterType;
    private String pid;
    private String topCode;

}
