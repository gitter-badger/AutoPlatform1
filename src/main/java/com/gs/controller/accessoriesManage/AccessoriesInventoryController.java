package com.gs.controller.accessoriesManage;

import ch.qos.logback.classic.Logger;
import com.gs.bean.Accessories;
import com.gs.bean.User;
import com.gs.common.bean.ComboBox4EasyUI;
import com.gs.common.bean.ControllerResult;
import com.gs.common.bean.Pager;
import com.gs.common.bean.Pager4EasyUI;
import com.gs.common.util.RoleUtil;
import com.gs.common.util.SessionUtil;
import com.gs.service.AccessoriesService;
import com.gs.service.AccessoriesTypeService;
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
 * Created by Administrator on 2017/4/20.
 */
@Controller
@RequestMapping("/accInv")
public class AccessoriesInventoryController {

    private Logger logger = (Logger) LoggerFactory.getLogger(AccessoriesInventoryController.class);

    @Resource
    private AccessoriesService accessoriesService;

    private String roles = "系统超级管理员,系统普通管理员,汽修公司管理员,汽修公司接待员";

    /**
     * 查询所有的配件库存信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public List<Accessories> queryAll(HttpSession session) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                List<Accessories> accessoriesList = accessoriesService.queryAll((User) session.getAttribute("user"));
                if (accessoriesList != null && !accessoriesList.equals("")) {
                    return accessoriesList;
                }
                return null;
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 查询全部的配件信息
     */
    @ResponseBody
    @RequestMapping(value = "queryAllAccInv",method = RequestMethod.GET)
    public List<ComboBox4EasyUI> queryAllAccInv(HttpSession session){
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                logger.info("查询所有配件信息");
                List<Accessories> accessories = accessoriesService.queryAll((User) session.getAttribute("user"));
                List<ComboBox4EasyUI> comboxs = new ArrayList<ComboBox4EasyUI>();
                for(Accessories c : accessories){
                    ComboBox4EasyUI comboBox4EasyUI = new ComboBox4EasyUI();
                    comboBox4EasyUI.setId(c.getAccId());
                    comboBox4EasyUI.setText(c.getAccName());
                    comboxs.add(comboBox4EasyUI);
                }
                return comboxs;
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryByIdAcc", method = RequestMethod.GET)
    public Pager4EasyUI<Accessories> queryByIdAccPager(HttpSession session,@Param("pageNumber") String pageNumber, @Param("pageSize") String pageSize,@Param("id")String id) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                Pager pager = new Pager();
                logger.info("分页查询所有配件");
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(accessoriesService.count((User) session.getAttribute("user")));
                List<Accessories> accessoriesList = accessoriesService.queryByIdPager(id,pager);
                return new Pager4EasyUI<Accessories>(pager.getTotalRecords(), accessoriesList);
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }


    /**
     * 分页查询配件库存信息
     */
    @ResponseBody
    @RequestMapping(value="queryByPage", method = RequestMethod.GET)
    public Pager4EasyUI queryByPager(HttpSession session,@Param("pageNumber") String pageNumber, @Param("pageSize") String pageSize) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                Pager pager = new Pager();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(accessoriesService.count((User) session.getAttribute("user")));
                logger.info("分页查询配件库存信息成功");
                List<Accessories> accessories = accessoriesService.queryByPager(pager);
                return new Pager4EasyUI<Accessories>(pager.getTotalRecords(), accessories);
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 添加添加配件到库存
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addAccInv", method = RequestMethod.POST)
    public ControllerResult addAccInv(HttpSession session,Accessories accessories) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                if(accessories!=null&&!accessories.equals("")){
                    accessoriesService.insert(accessories);
                    logger.info("添加成功");
                    return ControllerResult.getSuccessResult("添加成功");
                }else{
                    return ControllerResult.getFailResult("添加失败");
                }
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 移除库存
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "removeAccInv", method = RequestMethod.POST)
    public ControllerResult removeAccInv(HttpSession session,String accId) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                if(accId!=null&&!accId.equals("")){
                    accessoriesService.deleteById(accId);
                    logger.info("删除成功");
                    return ControllerResult.getSuccessResult("删除成功");
                }else{
                    return ControllerResult.getFailResult("删除失败");
                }
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 更新库存信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateAccInv", method = RequestMethod.POST)
    public ControllerResult updateAccInv(HttpSession session,Accessories accessories) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                if(accessories!=null&&!accessories.equals("")){
                    accessoriesService.update(accessories);
                    logger.info("修改成功");
                    return ControllerResult.getSuccessResult("修改成功");
                }else{
                    return ControllerResult.getFailResult("修改失败");
                }
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    /**
     * 查询所有被禁用的登记记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value="queryByPagerDisable", method = RequestMethod.GET)
    public Pager4EasyUI<Accessories> queryByPagerDisable(HttpSession session,@Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                logger.info("分页查询所有被禁用登记记录");
                Pager pager = new Pager();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(accessoriesService.countByDisable((User) session.getAttribute("user")));
                List<Accessories> accessories = accessoriesService.queryByPagerDisable(pager);
                return new Pager4EasyUI<Accessories>(pager.getTotalRecords(), accessories);
            }else{
                logger.info("此用户无拥有此方法角色");
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
    public ControllerResult inactive(HttpSession session,String accId, String accStatus) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                if (accId != null && !accId.equals("") && accStatus != null && !accStatus.equals("")) {
                    if (accStatus.equals("N")) {
                        accessoriesService.active(accId);
                        logger.info("激活成功");
                        return ControllerResult.getSuccessResult("激活成功");
                    } else {
                        accessoriesService.inactive(accId);
                        logger.info("禁用成功");
                        return ControllerResult.getSuccessResult("禁用成功");
                    }
                } else {
                    return ControllerResult.getFailResult("操作失败");
                }
            }else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登陆");
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "blurredQuery", method = RequestMethod.GET)
    public Pager4EasyUI<Accessories> blurredQuery(HttpSession session,HttpServletRequest request, @Param("pageNumber") String pageNumber, @Param("pageSize") String pageSize) {
        if(SessionUtil.isLogin(session)){
            if(RoleUtil.checkRoles(roles)){
                logger.info("配件库存模糊查询");
                String text = request.getParameter("text");
                String value = request.getParameter("value");
                if (text != null && !text.equals("") && value != null && !value.equals("")) {
                    Pager pager = new Pager();
                    pager.setPageNo(Integer.parseInt(pageNumber));
                    pager.setPageSize(Integer.parseInt(pageSize));
                    List<Accessories> accessoriesList = null;
                    Accessories accessories = new Accessories();
                    if (text.equals("汽车公司/配件名称/供应商/配件类型")) {
                        accessories.setCompanyId(value);
                        accessories.setAccName(value);
                        accessories.setSupplyId(value);
                        accessories.setAccTypeId(value);
                    } else if (text.equals("汽车公司")) {
                        accessories.setCompanyId(value);
                    } else if (text.equals("配件名称")) {
                        accessories.setAccName(value);
                    }else if(text.equals("供应商")){
                        accessories.setSupplyId(value);
                    }else if(text.equals("配件类型")){
                        accessories.setAccTypeId(value);
                    }
                    accessoriesList = accessoriesService.blurredQuery(pager, accessories);
                    pager.setTotalRecords(accessoriesService.countByBlurred(accessories,(User) session.getAttribute("user")));
                    return new Pager4EasyUI<Accessories>(pager.getTotalRecords(), accessoriesList);
                } else {
                    return null;
                }
            }else{
                logger.info("此用户无拥有此方法角色");
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
}
