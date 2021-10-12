package xyz.wongs.drunkard.base.persistence.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.drunkard.base.po.BasePo;
import xyz.wongs.drunkard.base.persistence.mybatis.mapper.DefineMapper;
import xyz.wongs.drunkard.base.persistence.mybatis.page.PaginationInfo;
import java.io.Serializable;
import java.util.List;

/**
 *  抽象的Service类，实现超级Service的超类
 * @Description
 * @author WCNGS@QQ.COM
 * 
 * @date 2017/12/2 13:34
 * @since 1.0.0
 */
@Transactional(readOnly = true)
public abstract class AbstractService<T extends BasePo,ID extends Serializable> implements DefineService<T,ID> {

    /**
     * @Author <a href="https://github.com/rothschil">Sam</a>
     * @Description //TODO
     * @Date 2021/7/8-12:53
     * @Param
     * @return SuperMapper<T
     **/
    protected abstract DefineMapper<T,ID> getMapper();

    @Override
    public PageInfo<T> selectPage(PaginationInfo pgInfo, T t) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = getMapper().getList(t);
        return new PageInfo<>(lt);
    }

    @Override
    public PageInfo<T> selectPageByCondition(PaginationInfo pgInfo, Object condition) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = getMapper().getListByCondition(condition);
        return new PageInfo<>(lt);
    }

    @Override
    public PageInfo<T> selectByExample(PaginationInfo pgInfo, Object example) {
        PageHelper.startPage(pgInfo.getPageNum(), pgInfo.getPageSize());
        List<T> lt = getMapper().getListByExample(example);
        return new PageInfo<>(lt);
    }

    @Override
    public int deleteByPrimaryKey(ID id) {
        return getMapper().deleteByPrimaryKey(id);
    }
    @Override
    public T selectByPrimaryKey(ID id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeyWithBlob(T t) {
        return getMapper().updateByPrimaryKeyWithBlob(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(T t) {
        return getMapper().updateByPrimaryKey(t);
    }
}