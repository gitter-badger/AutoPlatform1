package com.gs.service.impl;

import com.gs.bean.MaintainSchedule;
import com.gs.common.bean.Pager;
import com.gs.dao.MaintainScheduleDAO;
import com.gs.service.MaintainScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jyy on 2017/5/4.
 */
@Service
public class MaintainScheduleServiceImpl implements MaintainScheduleService {
    @Resource
    private MaintainScheduleDAO maintainScheduleDAO;

    @Override
    public int insert(MaintainSchedule maintainSchedule) {
        return 0;
    }

    @Override
    public int batchInsert(List<MaintainSchedule> list) {
        return 0;
    }

    @Override
    public int delete(MaintainSchedule maintainSchedule) {
        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public int batchDelete(List<MaintainSchedule> list) {
        return 0;
    }

    @Override
    public int update(MaintainSchedule maintainSchedule) {
        return 0;
    }

    @Override
    public int batchUpdate(List<MaintainSchedule> list) {
        return 0;
    }

    @Override
    public List<MaintainSchedule> queryAll() {
        return null;
    }


    @Override
    public List<MaintainSchedule> queryAll(String status) {
        return null;
    }

    @Override
    public MaintainSchedule query(MaintainSchedule maintainSchedule) {
        return null;
    }

    @Override
    public MaintainSchedule queryById(String id) {
        return null;
    }

    @Override
    public List<MaintainSchedule> queryByPager(Pager pager) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int inactive(String id) {
        return 0;
    }

    @Override
    public int active(String id) {
        return 0;
    }

    @Override
    public List<MaintainSchedule> queryByPagerDisable(Pager pager) {
        return null;
    }

    @Override
    public int countByDisable() {
        return 0;
    }

    @Override
    public List<MaintainSchedule> blurredQuery(Pager pager, MaintainSchedule maintainSchedule) {
        return null;
    }

    @Override
    public int countByBlurred(MaintainSchedule maintainSchedule) {
        return 0;
    }
}