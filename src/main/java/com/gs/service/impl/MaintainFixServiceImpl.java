package com.gs.service.impl;

import com.gs.bean.MaintainFix;
import com.gs.bean.User;
import com.gs.common.bean.Pager;
import com.gs.dao.MaintainFixDAO;
import com.gs.service.MaintainFixService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
*由CSWangBin技术支持
*
*@author CSWangBin
*@since 2017-04-17 16:02:41
*@des 维修保养项目Service实现
*/
@Service
public class MaintainFixServiceImpl implements MaintainFixService {

	@Override
	public List<MaintainFix> queryByPagerAll(Pager pager) {
		return maintainFixDAO.queryByPagerAll(pager);
	}

	@Override
	public List<MaintainFix> queryByPagerDisableService(Pager pager) {
		return maintainFixDAO.queryByPagerDisableService(pager);
	}

	@Override
	public int countByDisableService() {
		return maintainFixDAO.countByDisableService();
	}

	@Resource
	private MaintainFixDAO maintainFixDAO;

	@Override
	public List<MaintainFix> queryByPagerMaintain(Pager pager) {
		return maintainFixDAO.queryByPagerMaintain(pager);
	}

	public int insert(MaintainFix maintainFix) { return maintainFixDAO.insert(maintainFix); }
	public int batchInsert(List<MaintainFix> list) { return maintainFixDAO.batchInsert(list); }
	public int delete(MaintainFix maintainFix) { return maintainFixDAO.delete(maintainFix); }
	public int deleteById(String id) {
        return maintainFixDAO.deleteById(id);
    }
	public int batchDelete(List<MaintainFix> list) { return maintainFixDAO.batchDelete(list); }
	public int update(MaintainFix maintainFix) { return maintainFixDAO.update(maintainFix); }
	public int batchUpdate(List<MaintainFix> list) { return maintainFixDAO.batchUpdate(list); }

	@Override
	public List<MaintainFix> queryAll(User user) {
		return maintainFixDAO.queryAll(user);
	}

	@Override
	public List<MaintainFix> queryAll(String status) {
		return maintainFixDAO.queryAll(status);
	}


	public List<MaintainFix> queryByStatus(String status) { return maintainFixDAO.queryAll(status); }
	public MaintainFix query(MaintainFix maintainFix) { return maintainFixDAO.query(maintainFix); }
	public MaintainFix queryById(String id) { return maintainFixDAO.queryById(id); }
	public List<MaintainFix> queryByPager(Pager pager) { return maintainFixDAO.queryByPager(pager); }

	@Override
	public int count(User user) {
		return maintainFixDAO.count(user);
	}

	public int inactive(String id) { return maintainFixDAO.inactive(id); }
	public int active(String id) { return maintainFixDAO.active(id); }

	public List<MaintainFix> queryByPagerDisable(Pager pager) {
		return maintainFixDAO.queryByPagerDisable(pager);
	}

	@Override
	public int countByDisable(User user) {
		return maintainFixDAO.countByDisable(user);
	}

	public int countByDisable() {
		return maintainFixDAO.countByDisable();
	}

	public List<MaintainFix> blurredQuery(Pager pager, MaintainFix maintainFix) {
		return null;
	}

	@Override
	public int countByBlurred(MaintainFix maintainFix, User user) {
		return 0;
	}

	public int countByBlurred(MaintainFix maintainFix) {
		return 0;
	}

}