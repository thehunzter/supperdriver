package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    List<NoteModel> getAllNotes();

    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    NoteModel  findNoteById(Integer id);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)"
            + " VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(NoteModel noteModel);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    void deleteNote(Integer id);

    @Update("UPDATE NOTES " +
            "SET notetitle = #{notetitle}, notedescription= #{notedescription} " +
            "WHERE noteid = #{noteid}")
    void updateNote(NoteModel noteModel);
}
