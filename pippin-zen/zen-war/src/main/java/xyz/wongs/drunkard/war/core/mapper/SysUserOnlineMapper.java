package xyz.wongs.drunkard.war.core.mapper;

import xyz.wongs.drunkard.war.core.domain.SysUserOnline;

import java.util.List;

/**
 * 在线用户
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 23:58
 * @since 1.0.0
 */
public interface SysUserOnlineMapper {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    SysUserOnline selectOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    int deleteOnlineById(String sessionId);

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     * @return 结果
     */
    int saveOnline(SysUserOnline online);

    /**
     * 查询会话集合
     *
     * @param userOnline 会话参数
     * @return 会话集合
     */
    List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

    /**
     * 查询过期会话集合
     *
     * @param lastAccessTime 过期时间
     * @return 会话集合
     */
    List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);
}