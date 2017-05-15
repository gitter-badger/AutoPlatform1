package com.gs.dao;

import com.gs.bean.Appointment;
import com.gs.bean.User;
import com.gs.common.bean.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *由CSWangBin技术支持
 *
 *@author CSWangBin
 *@since 2017-04-17 15:51:08
 *@des 预约dao
 */
@Repository
public interface AppointmentDAO extends BaseDAO<String, Appointment>{

    /**
     * 模糊查询
     */
    public List<Appointment> blurredQuery(@Param("pager")Pager pager, @Param("appointment")Appointment appointment);

    /**
     * 模糊查询的记录数
     */
    public int countByBlurred(@Param("appointment")Appointment appointment, @Param("user") User user);

    /**
     * 修改预约当前状态
     */
    public void updateCurrentById(@Param("currentStatus")String currentStatus,@Param("appointmentId")String appointmentId);

    /**
     * 根据当前状态查询预约记录
     */
    public List<Appointment> queryByCurrentStatus(Pager pager);

    public int countByCurrentStatus(User user);
}