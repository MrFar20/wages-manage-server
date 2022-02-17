package com.cbadmin.app.web.manage;

import com.cbadmin.model.param.camp.AddCamp;
import com.cbadmin.model.param.camp.UpdateCamp;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.param.PageAndDateRangeRequest;
import com.cbmai.common.query.Page;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.core.param.DeleteByLongIds;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/camp")
@WebEncrypt
@WebDecrypt
public class CampManageController extends BaseController {

//    @Auth
    @GetMapping("/all")
    @ApiLog("获取所有阵营")
    public R<?> getAll() {
        return R.succData(
                services.campService.getAllCampsLabelValue()
        );
    }

    @GetMapping
    @Auth()
    @ApiLog("获取阵营")
    public R<?> getWorkers(PageAndDateRangeRequest request) {
        request.setPagesize(99999999);
        request.setPage(0);
        QueryCondition condition = new QueryCondition()
                .page(request, Page.Sort.byCreateTimeAsc());

        R result = services.campService.query(condition);

        return result;
    }

    @Auth
    @PostMapping("/update")
    @ApiLog("更新阵营信息")
    public R<?> updateCamp(@RequestBody @Validated UpdateCamp param) {
        services.campService.updateCamp(param);
        return R.succ();
    }

    @Auth
    @PostMapping
    @ApiLog("添加阵营信息")
    public R<?> addCamp(@RequestBody @Validated AddCamp param) {
        services.campService.addCamp(param);
        return R.succ();
    }

    @Auth
    @PostMapping("/delete")
    @ApiLog("删除阵营信息")
    public R<?> delete(@RequestBody @Validated DeleteByLongIds ids) {
        services.campService.deleteCamps(ids);
        return R.succ();
    }

}
