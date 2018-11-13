package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Zhiwei on 2018/10/24.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
