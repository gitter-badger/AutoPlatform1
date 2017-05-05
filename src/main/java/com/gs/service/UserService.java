package com.gs.service;

import com.gs.bean.User;
import com.gs.common.bean.Pager;
import com.gs.common.bean.Pager4EasyUI;

import java.util.Set;

import java.util.List;

/**
 * 由CSWangBin技术支持
 *
 * @author CSWangBin
 * @des
 * @since 2017-04-17 16:12:02
 */
public interface UserService extends BaseService<String, User> {

    //  分页查询全部，不分状态
    public List<User> queryByPagerAll(Pager pr);

    //  分页查询被禁用的记录
    public List<User> queryByPagerDisable(Pager pager);

    public List<User> queryEmail(String ids);

    //根据用户的email查询用户所拥有的权限。
    public Set<String> queryPermissions(String email);

    //根据用户email查询用户所拥有的角色
    public Set<String> queryRoles(String email);

    /**
     * 根据邮箱查询用户对应的id
     *
     * @param email
     * @return
     */
    public User queryByEmail(String email);


    public User queryByPhone(String userPhone);

    public int updIcon(String userId,String userIcon);

}