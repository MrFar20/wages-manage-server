package com.cbadmin.app.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.cbadmin.app.model.export.WorkerExport;
import com.cbadmin.app.util.ExportUtils;
import com.cbadmin.common.constant.enums.ExportType;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.model.vo.WorkerV;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbmai.common.util.BizAssert;
import com.cbmai.core.converter.ObjectConvertTool;
import com.cbmai.web.RequestUtils;
import com.cbmai.web.annotation.WebDecrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    @ApiLog("导出人员信息")
    @WebDecrypt
    @PostMapping("/workers")
    public void exportWorkers(@RequestParam("ids") String idsStr, @RequestParam String token, @RequestParam(defaultValue = "工资表") String fileName, @RequestParam String type) throws IOException {

        authService.getUser(
                authService.decryptToken(token, true)
        );


        ExportType et = ExportType.getExportType(type);

        BizAssert.notNull(et, "不存在的导出类型");

        HttpServletResponse response = RequestUtils.servletResponse();

        List<String> ids = JSON.parseArray(idsStr, String.class);
        switch (et) {
            case EXCEL: {

                List<WorkerV> workers = services.workerService.getByIds(ids);

                List<WorkerExport> exports = ObjectConvertTool.convert(workers, WorkerExport.class);

                int i = 1;
                for (WorkerExport export : exports) {
                    export.setNo(i);
                    i++;
                }


                writeFileHeader(response, fileName + ".xlsx");

                ExportUtils.exportExcel(exports, response.getOutputStream());
                break;
            }

            case PHOTO: {
                List<WorkerV> workers = services.workerService.getByIds(ids);
                List<StorageV> storages = services.storageService.getByIds(workers
                        .stream()
                        .collect(ArrayList::new, (arr, v) -> arr.add(v.getPhotoId()), (l, r) -> l.addAll(r))
                );
                File file = ExportUtils.exportPhoto(storages, workers);

                writeFileHeader(response, fileName + ".zip");

                FileUtil.writeToStream(file, response.getOutputStream());
                break;
            }

            case PHOTO_AND_EXCEL: {

                List<WorkerV> workers = services.workerService.getByIds(ids);


                List<StorageV> storages = services.storageService.getByIds(workers
                        .stream()
                        .collect(ArrayList::new, (arr, v) -> arr.add(v.getPhotoId()), (l, r) -> l.addAll(r))
                );


                File file = ExportUtils.exportPhotoAndExcel(storages, workers);

                writeFileHeader(response, fileName + ".zip");

                FileUtil.writeToStream(file, response.getOutputStream());
            }
        }

    }


    private void writeFileHeader(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String encfileName = URLUtil.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + encfileName);
    }

}
