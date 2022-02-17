package com.cbadmin.app.web;

import com.cbadmin.model.SystemConfig;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbmai.common.result.R;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @createTime: 2021-08-17 16:27:46
 * @created by: mrwangx
 * @description:
 */
@RequestMapping("/system")
@RestController
@Slf4j
public class SystemController extends BaseController {


    @PostMapping("/init")
    @ApiLog("初始化系统")
    public R<?> init() {
        services.systemService.init();
        return R.succ();
    }

    @GetMapping("/config")
    @WebDecrypt
    @WebEncrypt
    public R<SystemConfig> config() {
        return R.succData(
                services.systemService.systemConfig()
        );
    }

    @GetMapping("/time")
    public R<Long> time() {
        return R.succData(System.currentTimeMillis());
    }

}
