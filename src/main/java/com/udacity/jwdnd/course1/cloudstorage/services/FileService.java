package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    UploadMapper uploadMapper;
    UserMapper userMapper;

    public FileService(UploadMapper uploadMapper, UserMapper userMapper) {
        this.uploadMapper = uploadMapper;
        this.userMapper = userMapper;
    }

    public void store(MultipartFile file, Integer userId) throws IOException {

        String fileName = file.getOriginalFilename();
        FileModel fileModel = new FileModel(null, fileName,  file.getContentType(),
                file.getSize() + "", userId, file.getBytes());
        uploadMapper.addToFiles(fileModel);

    }

    public void deleteFile(Integer fileId) {
        uploadMapper.delete(fileId);
    }

    public FileModel findFile(Integer fileId) {
        return uploadMapper.findFileById(fileId);
    }

    public List<FileModel> getFiles() {
        return uploadMapper.getAllFiles();
    }

    public UserModel getUser(String username) {
        return userMapper.getUser(username);
    }
}
