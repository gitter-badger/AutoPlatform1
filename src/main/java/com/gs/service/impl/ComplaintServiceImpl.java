package com.gs.service.impl;

import com.gs.bean.Complaint;
import com.gs.common.bean.Pager;
import com.gs.dao.ComplaintDAO;
import com.gs.service.ComplaintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoQiao on 2017/4/16.
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Resource
    private ComplaintDAO complaintDAO;

    public int insert(Complaint complaint) {
        return complaintDAO.insert(complaint);
    }

    public int batchInsert(List<Complaint> list) {
        return complaintDAO.batchInsert(list);
    }

    public int delete(Complaint complaint) {
        return complaintDAO.delete(complaint);
    }

    public int deleteById(String id) {
        return complaintDAO.deleteById(id);
    }

    public int batchDelete(List<Complaint> list) {
        return complaintDAO.batchDelete(list);
    }

    public int update(Complaint complaint) {
        return complaintDAO.update(complaint);
    }

    public int batchUpdate(List<Complaint> list) {
        return complaintDAO.batchUpdate(list);
    }

    public List<Complaint> queryAll() {
        return complaintDAO.queryAll();
    }

    public List<Complaint> queryAll(String status) {
        return complaintDAO.queryAll(status);
    }

    public Complaint query(Complaint complaint) {
        return complaintDAO.query(complaint);
    }

    public Complaint queryById(String id) {
        return complaintDAO.queryById(id);
    }

    public List<Complaint> queryByPager(Pager pager) {
        return complaintDAO.queryByPager(pager);
    }

    public int count() {
        return complaintDAO.count();
    }

    public int inactive(String id) {
        return complaintDAO.inactive(id);
    }

    public int active(String id) {
        return complaintDAO.active(id);
    }
}