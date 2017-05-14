package com.gs.controller.accessoriesManage;

import ch.qos.logback.classic.Logger;
import com.gs.bean.*;
import com.gs.common.bean.ComboBox4EasyUI;
import com.gs.common.bean.ControllerResult;
import com.gs.common.bean.Pager;
import com.gs.common.bean.Pager4EasyUI;
import com.gs.common.util.RoleUtil;
import com.gs.common.util.SessionUtil;
import com.gs.common.util.UUIDUtil;
import com.gs.service.AccessoriesBuyService;
import com.gs.service.AccessoriesService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 王怡 配件采购
 * Created by Administrator on 2017/4/18.
 */
@Controller
@RequestMapping("/accBuy")
public class AccessoriesBuyController {

    @Resource
    private AccessoriesBuyService accessoriesBuyService;

    @Resource
    private AccessoriesService accessoriesService;

    private Logger logger = (Logger) LoggerFactory.getLogger(AccessoriesBuyController.class);
    /**
     * 查询全部的配件信息
     */
    @ResponseBody
    @RequestMapping(value = "queryAllAccBuy", method = RequestMethod.GET)
    public List<ComboBox4EasyUI> queryAllAccBuy(HttpSession session) {
        if (SessionUtil.isLogin(session)) {
            String roles="汽修公司管理员,汽修公司采购员,系统管理员";
            if (RoleUtil.checkRoles(roles)) {
                logger.info("查询所有配件分类信息");
                List<AccessoriesBuy> accessoriesBuys = accessoriesBuyService.queryAll((User) session.getAttribute("user"));
                List<ComboBox4EasyUI> comboxs = new ArrayList<ComboBox4EasyUI>();
                for (AccessoriesBuy c : accessoriesBuys) {
                    ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
                    comboBox4EasyUI.setId(c.getAccBuyId());
                    comboBox4EasyUI.setText(c.getAccBuyId());
                    comboxs.add(comboBox4EasyUI);
                }
                return comboxs;
            } else {
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        } else {
            logger.info("请先登录");
            return null;
        }
    }

    /**
     * 分页查询配件采购信息
     */
    @ResponseBody
    @RequestMapping(value = "queryByPage", method = RequestMethod.GET)
    public Pager4EasyUI<AccessoriesBuy> queryByPager(HttpSession session, @Param("pageNumber") String pageNumber, @Param("pageSize") String pageSize) {
        if (SessionUtil.isLogin(session)) {
            String roles="汽修公司管理员,汽修公司采购员,系统管理员";
            if (RoleUtil.checkRoles(roles)) {
                logger.info("分页查询所有的配件采购记录");
                Pager pager = new Pager();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(accessoriesBuyService.count((User) session.getAttribute("user")));
                pager.setUser((User) session.getAttribute("user"));
                List<AccessoriesBuy> accessories = accessoriesBuyService.queryByPager(pager);
                return new Pager4EasyUI<AccessoriesBuy>(pager.getTotalRecords(), accessories);

            } else {
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        } else {
            logger.info("请先登录");
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "addAccBuy", method = RequestMethod.POST)
    public ControllerResult addAccBuy(HttpSession session, AccessoriesBuy accessoriesBuy, @Param("accName") String accName) {
        if (SessionUtil.isLogin(session)) {
            String roles="汽修公司管理员,汽修公司采购员";
            if (RoleUtil.checkRoles(roles)) {
                if (accessoriesBuy != null && !accessoriesBuy.equals("")) {
                    if (accessoriesBuy.getAccId() != null && !accessoriesBuy.getAccId().equals("")) {
                        accessoriesBuy.setAccBuyDiscount(1.0);
                        accessoriesBuyService.insert(accessoriesBuy);
                        accessoriesService.updateCount(accessoriesBuy.getAccBuyCount(), accessoriesBuy.getAccId());
                        return ControllerResult.getSuccessResult("更新数量成功");
                    } else {
                        if (accName != null && !accName.equals("")) {
                            Accessories a = accessoriesService.queryByName(accName);
                            if (a != null) {
                                accessoriesBuy.setAccBuyDiscount(1.0);
                                accessoriesBuy.setAccId(a.getAccId());
                                accessoriesBuyService.insert(accessoriesBuy);
                                accessoriesService.updateCount(accessoriesBuy.getAccBuyCount(), accessoriesBuy.getAccId());
                                return ControllerResult.getSuccessResult("更新数量成功");
                            } else {
                                Accessories acc = new Accessories();
                                String uuid = UUIDUtil.uuid();
                                acc.setAccId(uuid);
                                acc.setAccName(accName);
                                acc.setCompanyId(accessoriesBuy.getCompanyId());
                                acc.setAccTotal(accessoriesBuy.getAccBuyCount());
                                acc.setAccPrice(accessoriesBuy.getAccBuyPrice());
                                acc.setAccBuyedTime(accessoriesBuy.getAccBuyTime());
                                acc.setAccCommodityCode("");
                                acc.setAccSalePrice(accessoriesBuy.getAccBuyPrice());
                                acc.setAccIdle(accessoriesBuy.getAccBuyCount());
                                acc.setSupplyId(accessoriesBuy.getSupplyId());
                                acc.setAccTypeId(accessoriesBuy.getAccTypeId());
                                accessoriesService.insert(acc);
                                accessoriesBuy.setAccId(uuid);
                                accessoriesBuyService.insert(accessoriesBuy);
                                return ControllerResult.getSuccessResult("添加成功");
                            }
                        }
                    }
                }
                return ControllerResult.getFailResult("添加失败，请输入必要的信息");
            } else {
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        } else {
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 修改采购记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateAccBuy", method = RequestMethod.POST)
    public ControllerResult updateAccBuy(HttpSession session,AccessoriesBuy accessoriesBuy) {
        if(SessionUtil.isLogin(session)){
            String roles="汽修公司管理员,汽修公司采购员";
            if(RoleUtil.checkRoles(roles)){
                if (accessoriesBuy != null && !accessoriesBuy.equals("")) {
                    accessoriesBuyService.update(accessoriesBuy);
                    logger.info("修改成功");
                    return ControllerResult.getSuccessResult("修改成功");
                } else {
                    return ControllerResult.getFailResult("修改失败");
                }
            }else{
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 查询所有被禁用的登记记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryByPagerDisable", method = RequestMethod.GET)
    public Pager4EasyUI<AccessoriesBuy> queryByPagerDisable(HttpSession session, @Param("pageNumber") String pageNumber, @Param("pageSize") String pageSize) {
        if(SessionUtil.isLogin(session)){
            String roles="汽修公司管理员,汽修公司采购员,系统管理员";
            if(RoleUtil.checkRoles(roles)){
                logger.info("分页查询所有被禁用登记记录");
                Pager pager = new Pager();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(accessoriesBuyService.countByDisable((User) session.getAttribute("user")));
                List<AccessoriesBuy> accessoriesBuys = accessoriesBuyService.queryByPagerDisable(pager);
                return new Pager4EasyUI<AccessoriesBuy>(pager.getTotalRecords(), accessoriesBuys);
            }else{
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 对状态的激活和启用，只使用一个方法进行切换。
     */
    @ResponseBody
    @RequestMapping(value = "statusOperate", method = RequestMethod.POST)
    public ControllerResult inactive(HttpSession session,String accBuyId, String accBuyStatus) {
        if(SessionUtil.isLogin(session)){
            String roles="汽修公司管理员,汽修公司采购员";
            if(RoleUtil.checkRoles(roles)){
                if (accBuyId != null && !accBuyId.equals("") && accBuyStatus != null && !accBuyStatus.equals("")) {
                    if (accBuyStatus.equals("N")) {
                        accessoriesBuyService.active(accBuyId);
                        logger.info("激活成功");
                        return ControllerResult.getSuccessResult("激活成功");
                    } else {
                        accessoriesBuyService.inactive(accBuyId);
                        logger.info("禁用成功");
                        return ControllerResult.getSuccessResult("禁用成功");
                    }
                } else {
                    return ControllerResult.getFailResult("操作失败");
                }
            }else{
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "blurredQuery", method = RequestMethod.GET)
    public Pager4EasyUI<AccessoriesBuy> blurredQuery(HttpSession session, HttpServletRequest request, @Param("pageNumber") String pageNumber, @Param("pageSize") String pageSize) {
        if(SessionUtil.isLogin(session)){
            String roles="汽修公司管理员,汽修公司采购员,系统管理员";
            if(RoleUtil.checkRoles(roles)){
                logger.info("配件采购记录模糊查询");
                String text = request.getParameter("text");
                String value = request.getParameter("value");
                if (text != null && !text.equals("") && value != null && !value.equals("")) {
                    Pager pager = new Pager();
                    pager.setPageNo(Integer.parseInt(pageNumber));
                    pager.setPageSize(Integer.parseInt(pageSize));
                    List<AccessoriesBuy> accessoriesBuys = null;
                    AccessoriesBuy accessoriesBuy = new AccessoriesBuy();
                    if (text.equals("汽车公司/配件名称")) {
                        accessoriesBuy.setCompanyId(value);
                        accessoriesBuy.setAccId(value);
                    } else if (text.equals("汽车公司")) {
                        accessoriesBuy.setCompanyId(value);
                    } else if (text.equals("配件名称")) {
                        accessoriesBuy.setAccId(value);
                    }
                    accessoriesBuys = accessoriesBuyService.blurredQuery(pager, accessoriesBuy);
                    pager.setTotalRecords(accessoriesBuyService.countByBlurred(accessoriesBuy, (User) session.getAttribute("user")));
                    return new Pager4EasyUI<AccessoriesBuy>(pager.getTotalRecords(), accessoriesBuys);
                } else {
                    return null;
                }
            }else{
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @RequestMapping(value = "queryByCondition")
    public List<AccessoriesBuy> queryByCondition(HttpSession session,String start, String end, String type) {
        if(SessionUtil.isLogin(session)){
            String roles="汽修公司管理员,汽修公司采购员,汽修公司销售员,汽修公司库管人员,系统管理员";
            if(RoleUtil.checkRoles(roles)){
                List<AccessoriesBuy> list = null;
                User user = (User) session.getAttribute("user");
                if (type != null && !type.equals("")) {
                    if (type.equals("year")) {
                        list = accessoriesBuyService.queryByCondition(start, end, user.getCompanyId(), "year");
                    } else if (type.equals("quarter")) {
                        list = accessoriesBuyService.queryByCondition(start, end, user.getCompanyId(), "quarter");
                    } else if (type.equals("month")) {
                        list = accessoriesBuyService.queryByCondition(start, end, user.getCompanyId(), "month");
                    } else if (type.equals("week")) {
                        list = accessoriesBuyService.queryByCondition(start, end, user.getCompanyId(), "week");
                    } else if (type.equals("day")) {
                        list = accessoriesBuyService.queryByCondition(start, end, user.getCompanyId(), "day");
                    }
                }
                return list;
            }else{
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryByPayCondition")
    public List<AccessoriesBuy> queryByPayCondition(HttpSession session,String start, String end, String type) {
        if(SessionUtil.isLogin(session)){
            String roles="汽修公司管理员,汽修公司采购员,汽修公司销售员,汽修公司库管人员,系统管理员";
            if(RoleUtil.checkRoles(roles)){
                List<AccessoriesBuy> list = null;
                User user = (User) session.getAttribute("user");
                if (type != null && !type.equals("")) {
                    if (type.equals("year")) {
                        list = accessoriesBuyService.queryByPayCondition(start, end, user.getCompanyId(), "year");
                    } else if (type.equals("quarter")) {
                        list = accessoriesBuyService.queryByPayCondition(start, end, user.getCompanyId(), "quarter");
                    } else if (type.equals("month")) {
                        list = accessoriesBuyService.queryByPayCondition(start, end, user.getCompanyId(), "month");
                    } else if (type.equals("week")) {
                        list = accessoriesBuyService.queryByPayCondition(start, end, user.getCompanyId(), "week");
                    } else if (type.equals("day")) {
                        list = accessoriesBuyService.queryByPayCondition(start, end, user.getCompanyId(), "day");
                    }
                }
                return list;
            }else{
                logger.info("此用户无法拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }
}
