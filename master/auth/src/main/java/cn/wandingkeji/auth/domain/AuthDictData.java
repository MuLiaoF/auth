package cn.wandingkeji.auth.domain;

import cn.wandingkeji.auth.system.SuperEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("tbl_auth_dict_data")
public class AuthDictData extends SuperEntity<AuthDictData> {

    private String dictCode;
    private String dictSort;
    private String dictLabel;
    private String dictValue;
    private String dictType;
    private String cssClass;
    private String isDefault;
    private String status;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;

}
