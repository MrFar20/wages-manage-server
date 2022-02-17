package com.cbadmin.app.web.manage;

import com.cbadmin.model.param.worker.AddWorker;
import com.cbadmin.model.param.worker.UpdateWorker;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.param.PageAndDateRangeRequest;
import com.cbmai.common.query.Page;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.core.param.DeleteByStringIds;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/worker")
@WebDecrypt
@WebEncrypt
public class WorkerManageController extends BaseController {

    @GetMapping
    @Auth()
    @ApiLog("获取人员")
    public R<?> getWorkers(PageAndDateRangeRequest request) {
        request.setPagesize(99999999);
        request.setPage(0);
        QueryCondition condition = new QueryCondition()
                .page(request, Page.Sort.byCreateTimeAsc());

        R result = services.workerService.query(condition);

        return result;
    }

    @Auth
    @PostMapping("/update")
    @ApiLog("更新人员信息")
    public R<?> updateWorker(@RequestBody @Validated UpdateWorker param) {
        services.workerService.updateWorker(param);
        return R.succ();
    }

    @Auth
    @PostMapping
    @ApiLog("添加人员信息")
    public R<?> addWorker(@RequestBody @Validated AddWorker param) {
        services.workerService.addWorker(param);
        return R.succ();
    }

    @Auth
    @PostMapping("/delete")
    @ApiLog("删除人员信息")
    public R<?> delete(@RequestBody @Validated DeleteByStringIds ids) {
        services.workerService.deleteByIds(ids);
        return R.succ();
    }


}
