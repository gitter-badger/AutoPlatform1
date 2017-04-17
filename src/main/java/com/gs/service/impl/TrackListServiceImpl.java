package com.gs.service.impl;

import com.gs.bean.TrackList;
import com.gs.common.bean.Pager;
import com.gs.dao.TrackListDAO;
import com.gs.service.TrackListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoQiao on 2017/4/16.
 */
@Service
public class TrackListServiceImpl implements TrackListService {

    @Resource
    private TrackListDAO trackListDAO;

    public int insert(TrackList trackList) {
        return trackListDAO.insert(trackList);
    }

    public int batchInsert(List<TrackList> list) {
        return trackListDAO.batchInsert(list);
    }

    public int delete(TrackList trackList) {
        return trackListDAO.delete(trackList);
    }

    public int deleteById(String id) {
        return trackListDAO.deleteById(id);
    }

    public int batchDelete(List<TrackList> list) {
        return trackListDAO.batchDelete(list);
    }

    public int update(TrackList trackList) {
        return trackListDAO.update(trackList);
    }

    public int batchUpdate(List<TrackList> list) {
        return trackListDAO.batchUpdate(list);
    }

    public List<TrackList> queryAll() {
        return trackListDAO.queryAll();
    }

    public List<TrackList> queryAll(String status) {
        return trackListDAO.queryAll(status);
    }

    public TrackList query(TrackList trackList) {
        return trackListDAO.query(trackList);
    }

    public TrackList queryById(String id) {
        return trackListDAO.queryById(id);
    }

    public List<TrackList> queryByPager(Pager pager) {
        return trackListDAO.queryByPager(pager);
    }

    public int count() {
        return trackListDAO.count();
    }

    public int inactive(String id) {
        return trackListDAO.inactive(id);
    }

    public int active(String id) {
        return trackListDAO.active(id);
    }
}