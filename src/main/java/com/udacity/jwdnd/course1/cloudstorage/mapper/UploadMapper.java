package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UploadMapper {

    @Select("SELECT * FROM FILES")
    List<FileModel>  getAllFiles();

    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    FileModel  findFileById(Integer id);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)"
            + " VALUES (#{filename}, #{contenttype}, #{filesize}, #{userId}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addToFiles(FileModel fileModel);

    @Delete("DELETE FROM FILES WHERE fileId = #{id}")
    void delete(Integer id);
}
