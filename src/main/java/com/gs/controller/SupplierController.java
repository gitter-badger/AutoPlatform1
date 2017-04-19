package com.gs.controller;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 供应商管理页面跳转
 */

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    private Logger logger = (Logger) LoggerFactory.getLogger(SystemManageController.class);

    /**
     * 供应商类型管理
     * @return
     */
    @RequestMapping(value = "supplierType", method = RequestMethod.GET)
    public String supplierType() {
        logger.info("供应商类型管理");
        return "supplier/supplierType";
    }

    /**
     * 供应商管理
     * @return
     */
    @RequestMapping(value = "supplierInformation", method = RequestMethod.GET)
    public String supplierInformation() {
        logger.info("供应商管理");
        return "supplier/supplierInFormation";
    }

    /**
     * 下单统计
     */
    @RequestMapping(value = "purchaseDetail", method = RequestMethod.GET)
    public String purchaseDetail() {
        logger.info("下单统计");
        return "supplier/purchaseDetail";
    }

    /**
     * 支付统计
     */
    @RequestMapping(value = "purchaseBill", method = RequestMethod.GET)
    public String purchaseBill() {
        logger.info("支付统计");
        return "supplier/purchaseBill";
    }
}
