package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


public class NoteController {

//    private final NoteService noteService;
//
//    public NoteController(NoteService noteService) {
//        this.noteService = noteService;
//    }
//
//    @GetMapping("/note")
//    public String getNoteTab(NoteForm noteForm, Model model) {
//        model.addAttribute("notes", this.noteService.getNotes());
//        return "note";
//    }
//
//    @PostMapping("/addnote")
//    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
//        int userId = noteService.getUser(authentication.getName()).getUserId();
//        noteService.store(noteForm, userId);
//        model.addAttribute("notes", this.noteService.getNotes());
//        return "note";
//    }
}
