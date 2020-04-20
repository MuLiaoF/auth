package cn.wandingkeji.auth.system;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 创建人: Hjx
 * Date: 2018/6/13
 * Description:
 */
@Getter
@Setter
public class SuperEntity<T extends Model> extends Model<T>{

    private static final long serialVersionUID = -3948018191144479861L;
    /** 主键. */
    private String id;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
