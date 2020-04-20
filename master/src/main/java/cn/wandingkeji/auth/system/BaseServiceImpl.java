package cn.wandingkeji.auth.system;


import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.contants.ResultEnum;
import cn.wandingkeji.auth.utils.ConstantUtils;
import cn.wandingkeji.auth.utils.CopyUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 创建人: Hjx
 * Date: 2019/1/11
 * Description:
 */
public abstract class BaseServiceImpl<M extends BaseDao<T>, T extends SuperEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    protected ResultData page(Page<T> page, Wrapper<T> wrapper) {
        List<T> entityList =  baseMapper.selectPage(page, wrapper);
        page.setRecords(entityList);
        return ConstantUtils.TableSuccess(page);
    }

    public ResultData page(T t, Page<T> page) {
        return this.page(page, new EntityWrapper<>());
    }

    public ResultData joinPage(Map<String, Object> map, Page<T> page) {
        List<T> entityList = baseMapper.joinPage(map, page);
        page.setRecords(entityList);
        return ConstantUtils.TableSuccess(page);
    }

    public ResultData mapPage(T t, Page<Map<String, Object>> page) {
        List<Map<String, Object>> listMap = baseMapper.mapPage(t, page);
        page.setRecords(listMap);
        return ConstantUtils.TableSuccess(page);
    }

    public ResultData joinMapPage(Map<String, Object> map, Page<Map<String, Object>> page) {
        List<Map<String, Object>> listMap = baseMapper.joinMapPage(map, page);
        page.setRecords(listMap);
        return ConstantUtils.TableSuccess(page);
    }

    protected ResultData list(Wrapper<T> wrapper) {
        return ConstantUtils.Success(baseMapper.selectList(wrapper));
    }

    @Override
    public ResultData list(T t) {
        return this.list(new EntityWrapper<>());
    }

    @Override
    public ResultData findById(String id) {
        T t = baseMapper.selectById(id);
        if (t == null) {
            return ConstantUtils.Error(ResultEnum.SUPER_ERROR_SELECT);
        }
        return ConstantUtils.Success(t);
    }

    @Override
    public ResultData delById(String id) {
        int count = baseMapper.deleteById(id);
        if (count == 0) {
            return ConstantUtils.Error(ResultEnum.SUPER_ERROR_DELETE);
        }
        return ConstantUtils.Success(id);
    }
    @Override
    public ResultData maintain(T t) {
        if (t == null) {
            return ConstantUtils.Error(ResultEnum.SUPER_ERROR_SELECT);
        }
        int count;
        if (StringUtils.isEmpty(t.getId())) {
            t.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            count = baseMapper.insert(t);
            if (count == 0) {
                return ConstantUtils.Error(ResultEnum.SUPER_ERROR_INSERT);
            }
        } else {
            T persisEntity = baseMapper.selectById(t.getId());
            CopyUtils.copyProperties(t, persisEntity);
            count = baseMapper.updateById(persisEntity);
            if (count == 0) {
                return ConstantUtils.Error(ResultEnum.SUPER_ERROR_UPDATE);
            }
        }
        return ConstantUtils.Success(t.getId());
    }

    @Override
    public ResultData findOne(T t) {
        T persis = baseMapper.selectOne(t);
        if (persis == null) {
            return ConstantUtils.Error(ResultEnum.SUPER_ERROR_SELECT);
        }
        return ConstantUtils.Success(persis);
    }
}
