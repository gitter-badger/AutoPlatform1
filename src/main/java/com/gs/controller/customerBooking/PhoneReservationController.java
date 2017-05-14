package com.gs.controller.customerBooking;

import ch.qos.logback.classic.Logger;
import com.gs.bean.Appointment;
import com.gs.bean.User;
import com.gs.common.bean.ControllerResult;
import com.gs.common.bean.Pager;
import com.gs.common.bean.Pager4EasyUI;
import com.gs.common.util.RoleUtil;
import com.gs.common.util.SessionUtil;
import com.gs.service.AppointmentService;
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
import java.util.Date;
import java.util.List;

/**
 * 电话预约controller，杨健勇
 */
@Controller
@RequestMapping("/appointment")

public class PhoneReservationController {

    private Logger logger = (Logger) LoggerFactory.getLogger(PhoneReservationController.class);

    @Resource
    private AppointmentService appointmentService;

    /**
     * 查询所有预约记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value="queryByPager", method = RequestMethod.GET)
    public Pager4EasyUI<Appointment> queryByPager(HttpSession session, @Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize) {
        if (SessionUtil.isLogin(session)){
             String roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽修公司接待员";
             if (RoleUtil.checkRoles(roles)){
                 logger.info("分页查询所有预约记录");
                 Pager pager = new Pager();
                 pager.setPageNo(Integer.valueOf(pageNumber));
                 pager.setPageSize(Integer.valueOf(pageSize));
                 pager.setTotalRecords(appointmentService.count((User)session.getAttribute("user")));
                 pager.setUser((User)session.getAttribute("user"));
                 List<Appointment> appointments = appointmentService.queryByPager(pager);
                 return new Pager4EasyUI<Appointment>(pager.getTotalRecords(), appointments);
             }else {
                 logger.info("此用户无拥有此方法角色");
                 return null;
             }
        } else {
            logger.info("请先登录");
            return null;
        }
    }
    /**
     * 查询所有被禁用的登记记录
     * @return
     */
    @ResponseBody
    @RequestMapping(value="queryByPagerDisable", method = RequestMethod.GET)
    public Pager4EasyUI<Appointment> queryByPagerDisable(HttpSession session,@Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize) {
        if (SessionUtil.isLogin(session)) {
            String roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽修公司接待员";
            if (RoleUtil.checkRoles(roles)) {
                logger.info("分页查询所有被禁用登记记录");
                Pager pager = new Pager();
                pager.setPageNo(Integer.valueOf(pageNumber));
                pager.setPageSize(Integer.valueOf(pageSize));
                pager.setTotalRecords(appointmentService.countByDisable((User) session.getAttribute("user")));
                List<Appointment> appointments = appointmentService.queryByPagerDisable(pager);
                return new Pager4EasyUI<Appointment>(pager.getTotalRecords(), appointments);
            } else{
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else {
            logger.info("请先登录");
            return null;
        }
    }
    /**
     *
     * 添加电话预约
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public ControllerResult add(Appointment appointment,HttpSession session){
        if (SessionUtil.isLogin(session)){
            String roles = "公司超级管理员,公司普通管理员,汽修公司接待员";
            if (RoleUtil.checkRoles(roles)) {
                User user = (User)session.getAttribute("user");
                logger.info("添加电话预约");
                if (appointment != null) {
                    appointment.setCompanyId("c515f5d623e011e7a97af832e40312b3");
                    appointmentService.insert(appointment);
                    return ControllerResult.getSuccessResult("添加电话预约成功");
                } else {
                    return ControllerResult.getFailResult("添加电话预约失败");
                }
            } else {
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else {
            logger.info("请先登录");
            return ControllerResult.getNotLoginResult("添加电话预约无效，请重新登录");
        }
    }

    /**
     *
     * 修改电话预约
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ControllerResult update(Appointment appointment,HttpSession session){
        if (SessionUtil.isLogin(session)) {
            String roles = "公司超级管理员,公司普通管理员,汽车公司接待员";
            if (RoleUtil.checkRoles(roles)) {
                logger.info("修改电话预约");
                User user = (User)session.getAttribute("user");
                appointment.setCompanyId("c515f5d623e011e7a97af832e40312b3");
                appointmentService.update(appointment);
                return ControllerResult.getSuccessResult("修改电话预约成功");
            } else {
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        } else {
            logger.info("请先登录");
            return ControllerResult.getNotLoginResult("登录信息无效，请重新登录");
        }
    }

    /**
     * 对状态的激活和启用，只使用一个方法进行切换。
     */
    @ResponseBody
    @RequestMapping(value = "statusOperate", method = RequestMethod.POST)
    public ControllerResult inactive(String appointmentId, String appoitmentStatus, HttpSession session) {
        if (SessionUtil.isLogin(session)){
            String roles = "公司超级管理员,公司普通管理员,汽修公司接待员";
            if (RoleUtil.checkRoles(roles)) {
                if (appointmentId != null && !appointmentId.equals("") && appoitmentStatus != null && !appoitmentStatus.equals("")) {
                    if (appoitmentStatus.equals("N")) {
                        appointmentService.active(appointmentId);
                        logger.info("激活电话成功");
                        return ControllerResult.getSuccessResult("激活电话预约成功");
                    } else {
                        appointmentService.inactive(appointmentId);
                        logger.info("禁用电话预约成功");
                        return ControllerResult.getSuccessResult("禁用电话预约成功");
                    }
                } else {
                    return ControllerResult.getFailResult("操作失败");
                }
            }else {
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登录");
            return ControllerResult.getNotLoginResult("登录信息无效，请重新登录");
        }
    }

    /**
     * 登记记录模糊查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="blurredQuery", method = RequestMethod.GET)
    public Pager4EasyUI<Appointment> blurredQuery(HttpSession session,HttpServletRequest request, @Param("pageNumber")String pageNumber, @Param("pageSize")String pageSize) {
        if (SessionUtil.isLogin(session)) {
            String roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽修公司接待员";
            if (RoleUtil.checkRoles(roles)) {
                logger.info("预约记录模糊查询");
                Pager pager = new Pager();
                String text = request.getParameter("text");
                String value = request.getParameter("value");
                if (text != null && text != "") {

                    pager.setPageNo(Integer.valueOf(pageNumber));
                    pager.setPageSize(Integer.valueOf(pageSize));
                    List<Appointment> appointments = null;
                    Appointment appointment = new Appointment();
                    if (text.equals("车主姓名/车主电话/汽车公司/汽车车牌号")) { // 当多种模糊搜索条件时
                        appointment.setUserName(value);
                        appointment.setCompanyId(value);
                        appointment.setCarPlate(value);
                        appointment.setUserPhone(value);
                    } else if (text.equals("车主姓名")) {
                        appointment.setUserName(value);
                    } else if (text.equals("汽车公司")) {
                        appointment.setCompanyId(value);
                    } else if (text.equals("汽车车牌号")) {
                        appointment.setCarPlate(value);
                    } else if (text.equals("车主电话")) {
                        appointment.setUserPhone(value);
                    }
                    appointments = appointmentService.blurredQuery(pager, appointment);
                    pager.setTotalRecords(appointmentService.countByBlurred(appointment,(User)session.getAttribute("user")));
                    System.out.print(appointments);
                    return new Pager4EasyUI<Appointment>(pager.getTotalRecords(), appointments);
                } else {
                    pager.setTotalRecords(appointmentService.count((User)session.getAttribute("user")));
                    List<Appointment> appointments = appointmentService.queryByPager(pager);
                    return new Pager4EasyUI<Appointment>(pager.getTotalRecords(), appointments);
                }
            }else {
                logger.info("此用户无拥有此方法角色");
                return null;
            }
        }else{
            logger.info("请先登录");
            return null;
        }
    }
    /**
     * 时间格式化
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
