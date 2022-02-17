import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cbadmin.app.CbadminApplication;
import com.cbadmin.app.TestService;
import com.cbadmin.dao.entity.Worker;
import com.cbadmin.dao.repo.WorkerRepo;
import com.cbadmin.model.param.storage.UploadFileParam;
import com.cbadmin.model.param.worker.AddWorker;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.service.Services;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CbadminApplication.class)
@Slf4j
public class Test {

    @Autowired
    private Services services;

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private TestService testService;

    @Value("${system.storage.local.root}")
    private String storagePath;

    private String photoId = "de77b25c2d434f3b8846e284aa58530f.jpeg";

    @org.junit.Test
    public void test2() {

        File photoDir = new File("/Users/mrwangx/Downloads/全部图片");

        List<Worker> workers = workerRepo.findAll();

        log.info("==> worker人数:{}", workers.size());

        Map<String, Worker> workerMap = new HashMap<>();

        workers.forEach(worker -> workerMap.put(worker.getName(), worker));


        Stream.of(photoDir.listFiles()).forEach(photo -> {

            String name = photo.getName().substring(0, photo.getName().lastIndexOf("."));
            String ext = photo.getName().substring(photo.getName().lastIndexOf("."));
            Worker worker = workerMap.get(name);

            if (worker != null) {

                log.info("==> name:{}, ext:{}, worker:{}", name, ext, worker);

                String filename = UUID.fastUUID().toString(true) + ext;

                UploadFileParam param = UploadFileParam.builder()
                        .userId("697441417462546432")
                        .username("useradmin")
                        .fileName(photo.getName())
                        .bytes(FileUtil.readBytes(photo))
                        .build();

                StorageV storage = services.storageService.uploadFile(param);


                worker.setPhotoId(storage.getId());

                testService.saveWorker(worker);

            }

        });


    }

    //    @org.junit.Test
    public void test1() {
        List<AddWorker> adds = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);
        EasyExcel.read("/Users/mrwangx/Downloads/工资总表(1).xlsx", new ReadListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
//                log.info("==> read, class:{}\n  {}", o.getClass(), JSON.toJSONString(o, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat));
                Map<Integer, String> map = (Map<Integer, String>) o;
                String first = map.get(0);
                if (first != null && first.matches("^[0-9]+$")) {
                    AddWorker add = new AddWorker();
                    add.setName(map.get(1));
                    add.setIdNumber(map.get(2));
                    add.setBankcardNumber(map.get(3));
                    add.setBankOrg(map.get(4));
                    add.setContractInfo(map.get(5));
                    add.setPhotoId(photoId);
                    add.setCampId(null);
                    adds.add(add);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                log.info("==data: {}", JSON.toJSONString(adds, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat));
                log.info("==> dataSize:{}", adds.size());
                adds.forEach(e -> {
                    try {
                        services.workerService.addWorker(e);
                    } catch (Exception ex) {
                        log.error("", ex);
                    }
                });
            }
        }).sheet(0).doRead();

    }

}
