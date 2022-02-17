package com.cbadmin.app.web.manage;

import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.service.impl.util.StorageUtils;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.param.PageAndDateRangeRequest;
import com.cbmai.common.query.Page;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/manage/storage")
@RestController
@WebEncrypt
@WebDecrypt
public class StorageManageController extends BaseController {


    @ApiLog("获取存储")
    @Auth({UserType.ADMIN})
    @GetMapping
    public R<?> getStorages(@Validated PageAndDateRangeRequest request) {

        QueryCondition condition = new QueryCondition()
                .page(request, Page.Sort.byCreateTimeDesc());

        R<List<StorageV>> result = (R<List<StorageV>>) services.storageService.query(condition);

        result.getData().forEach(s -> StorageUtils.setAccessUrl(s));

        StorageUtils.clear();

        return result;
    }

}
