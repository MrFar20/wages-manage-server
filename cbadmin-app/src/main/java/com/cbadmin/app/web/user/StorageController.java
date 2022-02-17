package com.cbadmin.app.web.user;


import com.cbadmin.model.param.storage.UploadFileParam;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.result.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@RequestMapping("/storage")
@RestController
public class StorageController extends BaseController {


    @PostMapping
    @ApiLog("上传文件")
    @Auth
    public R<?> uploadFile(@Validated @NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file) throws IOException {

        UserV user = getUser();

        String filename = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        UploadFileParam param = UploadFileParam.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fileName(filename)
                .bytes(bytes)
                .build();

        StorageV storage = services.storageService.uploadFile(param);

        return R.succData(storage.getId());
    }


}
