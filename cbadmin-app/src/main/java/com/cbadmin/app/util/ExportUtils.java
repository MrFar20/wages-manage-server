package com.cbadmin.app.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.excel.EasyExcel;
import com.cbadmin.app.model.export.WorkerExport;
import com.cbadmin.model.vo.StorageV;
import com.cbadmin.model.vo.WorkerV;
import com.cbmai.core.converter.ObjectConvertTool;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出工具
 */
public class ExportUtils {

    public static void main(String[] args) {
        File file = ZipUtil.zip("/Users/mrwangx/storage");
        System.out.println(file.getAbsolutePath());
    }

    private ExportUtils() {}


    /**
     * 导出excel
     * @param data
     * @param outputStream
     */
    public static void exportExcel(List<WorkerExport> data, OutputStream outputStream) {
        EasyExcel.write(outputStream, WorkerExport.class).sheet("工资表").doWrite(data);
    }

    /**
     * 导出图片
     * @param storage
     * @return
     */
    public static File exportPhoto(List<StorageV> storage, List<WorkerV> workers) {

        Map<String, StorageV> storageMap = new HashMap<>();

        storage.forEach(s -> storageMap.put(s.getId(), s));

        /**
         * 目录结构
         *
         *  uuid
         *      - 图片
         *          - 图片不存在的人员.txt
         *          - ....
         * */
        File dir = new File(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "-" + UUID.fastUUID().toString(true) ,"人员信息");
        dir.mkdir();

        File photoDir = new File(dir, "人员图片");
        photoDir.mkdir();

        StringBuilder nameBuilder = new StringBuilder("");
        File notExsitsPhotoNameTxt = new File(photoDir, "图片不存在的人员.txt");


        //复制图片
        workers.forEach(w -> {

            StorageV s = storageMap.get(w.getPhotoId());

            if (s != null) {

                File srcPhoto = new File(s.getFileUrl());
                if (srcPhoto.exists()) {

                    //复制图片 名字
                    FileUtil.copy(srcPhoto, new File(photoDir, w.getName() + "." + s.getFileSuffix()), true);

                } else {
                    nameBuilder.append(w.getName() + ", ");
                }

            } else {
                nameBuilder.append(w.getName() + ", ");
            }

        });

        //图片不存在
        String notExsitsPhotoNames = nameBuilder.toString();
        if (!"".equals(notExsitsPhotoNames)) {
            FileUtil.writeString(nameBuilder.toString(), notExsitsPhotoNameTxt, Charset.defaultCharset());
        }



        return ZipUtil.zip(dir);
    }


    public static File exportPhotoAndExcel(List<StorageV> storage, List<WorkerV> workers) {

        List<WorkerExport> exports = ObjectConvertTool.convert(workers, WorkerExport.class);

        int i = 1;
        for (WorkerExport export : exports) {
            export.setNo(i);
            i++;
        }


        Map<String, StorageV> storageMap = new HashMap<>();

        storage.forEach(s -> storageMap.put(s.getId(), s));

        /**
         * 目录结构
         *
         *  uuid
         *      - 图片
         *          - 图片不存在的人员.txt
         *          - ....
         * */
        File dir = new File(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "-" + UUID.fastUUID().toString(true), "人员信息");
        dir.mkdirs();

        File photoDir = new File(dir, "人员图片");
        photoDir.mkdir();

        StringBuilder nameBuilder = new StringBuilder("");
        File notExsitsPhotoNameTxt = new File(photoDir, "图片不存在的人员.txt");


        //复制图片
        workers.forEach(w -> {

            StorageV s = storageMap.get(w.getPhotoId());

            if (s != null) {

                File srcPhoto = new File(s.getFileUrl());
                if (srcPhoto.exists()) {

                    //复制图片 名字
                    FileUtil.copy(srcPhoto, new File(photoDir, w.getName() + "." + s.getFileSuffix()), true);

                } else {
                    nameBuilder.append(w.getName() + ", ");
                }

            } else {
                nameBuilder.append(w.getName() + ", ");
            }

        });

        //图片不存在
        String notExsitsPhotoNames = nameBuilder.toString();
        if (!"".equals(notExsitsPhotoNames)) {
            FileUtil.writeString(nameBuilder.toString(), notExsitsPhotoNameTxt, Charset.defaultCharset());
        }

        EasyExcel.write(new File(dir, "工资表.xlsx"), WorkerExport.class).sheet("工资表").doWrite(exports);


        return ZipUtil.zip(dir);
    }

}
