package com.iot.util.myUtil;

import com.iot.model.ImgData;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * 保存图片
 */
@Component
public class MyImageUtil {
    private final String UPLOAD_FOLDER = "/var/www/html/images";
    //private final String UPLOAD_FOLDER = "G:/images";
    private final String URL = "http://114.115.243.22/images";
    @Autowired
    private MyStringUtil myStringUtil;

    public String saveImage(int sensorId, MultipartFile file) throws Exception {
        String img = "";
        if (file.isEmpty()) {
            throw new DescribeException(ExceptionEnum.IMAGE_NOT_NULL);
        }
        //byte[] bytes = file.getBytes();
        try {
            //得到原来图片的后缀名
            String originalFileName = file.getOriginalFilename();
            String extensionName = originalFileName.substring(originalFileName.lastIndexOf("."));
            // 新的图片文件名 = 获取时间戳+6位随机数+"."图片扩展名
            String newFileName = System.currentTimeMillis() + myStringUtil.createEmailVerifyCode() + extensionName;

            //路径操作，按sensor分文件夹
            String senId = String.valueOf(sensorId);
            Path path = Paths.get(UPLOAD_FOLDER + "/" + senId);

            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(path);
            }
            String imgSrc = path.toString() + "/" + newFileName;
            img = URL + "/" + senId + "/" + newFileName;
            System.out.println("上传图像imgSrc：" + imgSrc);
            System.out.println("上传图像img：" + img);
            file.transferTo(new File(imgSrc));

        } catch (Exception e) {
            throw new DescribeException(ExceptionEnum.UPLOAD_IMG_ERROR);
        }
        return img;
    }

    public void deleteImage(List<ImgData> imgs) {
        try {
            Iterator<ImgData> iterator = imgs.iterator();
            while (iterator.hasNext()) {
                ImgData it = iterator.next();
                //it.getImg()是路径
                String img = it.getImg();
                String imgSrc = img.replace(URL, UPLOAD_FOLDER);
                System.out.println("删除图像imgSrc：" + imgSrc);

                File file = new File(imgSrc);
                file.delete();
            }
        } catch (Exception e) {
            throw new DescribeException(ExceptionEnum.DELETE_IMG_ERROR);
        }
    }

}

