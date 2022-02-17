package com.cbadmin.app.web.manage;

import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.param.PageAndDateRangeRequest;
import com.cbmai.common.query.Page;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.systemconfig.param.UpdateConfigParam;
import com.cbmai.systemconfig.service.ConfigService;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author by: zsxmlv
 * @createTime: 2021-03-22 15:03:33
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/manage/config")
@WebDecrypt
@WebEncrypt
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping
    @ApiLog("获取系统配置")
    @Auth(UserType.ADMIN)
    public R<?> query(@Validated PageAndDateRangeRequest request) {
        QueryCondition queryCondition = new QueryCondition()
                .page(request, Page.Sort.byCreateTimeAsc());
        return configService.query(queryCondition);
    }

    @PutMapping
    @ApiLog("更新系统配置")
    @Auth(UserType.ADMIN)
    public R<?> updateConfig(@Validated @RequestBody UpdateConfigParam param) {
        configService.updateConfig(param);
        return R.succ("更新成功");
    }
}
