package com.gs.controller.FinancialManage;

import com.gs.bean.Salary;
import com.gs.common.bean.ControllerResult;
import com.gs.common.bean.Pager;
import com.gs.common.bean.Pager4EasyUI;
import com.gs.common.util.ViewExcel;
import com.gs.service.SalaryService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by GZWangBin on 2017/4/20.
 */
@Controller
@RequestMapping("/salary")
public class SalaryController{

    private Logger logger = (Logger) LoggerFactory.getLogger(SalaryController.class);

    /**
     * 收入Service
     */
    @Resource
    public SalaryService salaryService;




    @ResponseBody
    @RequestMapping(value = "queryByPager",method = RequestMethod.GET)
    public Pager4EasyUI<Salary> queryByPager(@Param("pageNumber") String pageNumber, @Param("pageSize")String pageSize) {
        logger.info("工资信息分页查询");
        Pager pager = new Pager();
        pager.setPageNo(Integer.valueOf(pageNumber));
        pager.setPageSize(Integer.valueOf(pageSize));
        pager.setTotalRecords(salaryService.count());
        List<Salary> salaries = salaryService.queryByPager(pager);
        return new Pager4EasyUI<Salary>(pager.getTotalRecords(), salaries);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public ControllerResult add(Salary salary) {
        logger.info("添加工资信息");
        salaryService.insert(salary);
        return ControllerResult.getSuccessResult("添加成功");
    }

    @ResponseBody
    @RequestMapping(value = "checkUserId", method = RequestMethod.GET)
    public boolean checkUserId(String userId) {
        Salary salary1 = salaryService.queryById(userId);
        if(salary1 != null){
            return true;
        }
        return false;
    }


    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ControllerResult update(Salary salary) {
        logger.info("修改工资信息");
        System.out.printf(salary.getUserId()+ "ddddddd" +  salary.getSalaryId() + "ccc" + salary.getPrizeSalary());
        salaryService.update(salary);
        return ControllerResult.getSuccessResult("修改成功");
    }


    @RequestMapping(value ="/export",method=RequestMethod.GET)
    public ModelAndView export(ModelMap model, HttpServletRequest request){
        List<Salary> list = salaryService.queryAll();
        ViewExcel viewExcel = new ViewExcel();
        //将查询出的list集合存入ModelMap 对象中，此时的key就是ViewExcel类中Map所对应的key
        model.put("list", list);
        return new ModelAndView(viewExcel, model);
    }


}