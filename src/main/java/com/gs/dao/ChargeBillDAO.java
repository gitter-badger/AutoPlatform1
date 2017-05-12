package com.gs.dao;

import com.gs.bean.ChargeBill;
import com.gs.bean.User;
import com.gs.common.bean.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*由CSWangBin技术支持
*
*@author CSWangBin
*@since 2017-04-17 15:55:59
*@des 收费单据dao
*/
@Repository
public interface ChargeBillDAO extends BaseDAO<String, ChargeBill>{
    /**
     * 模糊查询
     */
    public List<ChargeBill> blurredQuery(@Param("pager")Pager pager, @Param("chargeBill")ChargeBill chargeBill);

    /**
     * 模糊查询的记录数
     */
    public int countByBlurred(@Param("chargeBill")ChargeBill chargeBill,@Param("user")User user);
}