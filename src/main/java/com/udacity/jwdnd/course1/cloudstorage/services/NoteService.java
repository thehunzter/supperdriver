package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public void store(NoteForm noteForm, Integer userId) {
        NoteModel noteModel = new NoteModel(null, noteForm.getNotetitle(), noteForm.getNotedescription(), userId);
        noteMapper.insertNote(noteModel);
    }

    public NoteModel noteModel(Integer noteId) {
        return noteMapper.findNoteById(noteId);
    }

    public void deleteNote(Integer noteId)  {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(NoteForm noteForm) {
        NoteModel noteModel = new NoteModel(noteForm.getNoteid(), noteForm.getNotetitle(), noteForm.getNotedescription(), noteForm.getUserid());
        noteMapper.updateNote(noteModel);
    }

    public List<NoteModel> getNotes() {
        return noteMapper.getAllNotes();
    }

    public UserModel getUser(String username) {
        return userMapper.getUser(username);
    }
}
