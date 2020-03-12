package com.iot.service.impl;

import com.iot.dao.ImgDataMapper;
import com.iot.framework.aop.SystemServiceLog;
import com.iot.model.ImgData;
import com.iot.model.User;
import com.iot.model.acceptParam.DataConditionParam;
import com.iot.service.ImgDataService;
import com.iot.service.SensorService;
import com.iot.util.exception.DescribeException;
import com.iot.util.exception.ExceptionEnum;
import com.iot.util.myUtil.MyStringUtil;
import com.iot.util.myUtil.MyVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ImgDataServiceImpl implements ImgDataService {

    @Autowired
    private MyVerificationUtil myVerificationUtil;
    @Autowired
    private ImgDataMapper imgDataMapper;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private MyStringUtil myStringUtil;

    private final String UPLOAD_FOLDER = "/var/www/html/images";
    //private final String UPLOAD_FOLDER = "G:/images";
    private final String URL = "http://114.115.243.22:88/images";

    @Override
    public List<ImgData> queryImgBySensorId(DataConditionParam dataConditionParam) {
        return imgDataMapper.queryImgBySensorId(dataConditionParam);
    }

    @Override
    @SystemServiceLog(logAction = "uploadImg", logContent = "新增一条图像数据")
    public synchronized Map uploadImg(String apiKey, int sensorId, MultipartFile file) throws Exception {
        User user = myVerificationUtil.verifyApiKeyInUserLinkSensorId(apiKey, sensorId);
        Map<String, User> result = new HashMap<String, User>();
        result.put("user", user);

        String img = "";
        try {
            if (file.isEmpty()) {
                throw new DescribeException(ExceptionEnum.IMAGE_NOT_NULL);
            }

            byte[] bytes = file.getBytes();

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
            img=URL +"/"+senId+"/"+ newFileName;
            System.out.println("上传图像imgSrc："+imgSrc);
            System.out.println("上传图像img："+img);
            file.transferTo(new File(imgSrc));

        } catch (Exception e) {
            throw new DescribeException(ExceptionEnum.UPLOAD_IMG_ERROR);
        }

        ImgData imgData = new ImgData();
        imgData.setSensorId(sensorId);
        imgData.setImg(img);
        imgDataMapper.insertSelective(imgData);
        return result;
    }

    @Override
    public void deleteImgs(int[] ids) {
        List<ImgData> imgs = imgDataMapper.selectByPrimaryKey(ids);
        Iterator<ImgData> iterator = imgs.iterator();
        while (iterator.hasNext()) {
            ImgData it = iterator.next();
            //it.getImg()是路径
            String img=it.getImg();
            String imgSrc=img.replace(URL,UPLOAD_FOLDER);
            System.out.println("删除图像imgSrc："+imgSrc);

            File file = new File(imgSrc);
            file.delete();
        }

        imgDataMapper.deleteImgs(ids);
        return;
    }

}
