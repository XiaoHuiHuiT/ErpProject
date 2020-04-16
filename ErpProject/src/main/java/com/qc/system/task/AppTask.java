package com.qc.system.task;

import com.qc.system.utils.FileUploadAndDownUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 定时任务
 */
@EnableScheduling
@Component
public class AppTask {

    @Value("${upload.upload-root-path}")
    private String uploadRootPath = "F:/upload";

    @Scheduled(cron = "0 30 23 * * ? ")
    public void cleanImageRemoveTemp(){
        // 1.得到文件夹名称
        String dirName = FileUploadAndDownUtil.getDirNameByCurrentDate();

        // 2.得到要扫描的文件夹
        File file = new File(uploadRootPath + "/" + dirName);

        if(file.exists()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                if(file1.getName().endsWith("_temp")){
                    file1.delete();
                }
            }
        }

    }

}