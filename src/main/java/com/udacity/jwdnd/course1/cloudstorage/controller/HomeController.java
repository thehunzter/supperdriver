package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final FileService fileService;
    private final NoteService noteService;

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String getHomePage(NoteForm noteForm, Model model) {
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

    @PostMapping("/home")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file, Authentication authentication
                             , Model model) throws IOException {

        int userId = fileService.getUser(authentication.getName()).getUserId();
        fileService.store(file, userId);
        model.addAttribute("files", this.fileService.getFiles());

        return "home";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam Integer fileId, Model model) {
        logger.info("Deleting file with fileId: {}", fileId);
        fileService.deleteFile(fileId);
        logger.info("File deleted. Redirecting to home.");
        model.addAttribute("files", this.fileService.getFiles());
        return "redirect:/home";
    }

    @GetMapping("/image")
    public void showImage(@RequestParam Integer fileId, HttpServletResponse response)  throws ServletException, IOException{

        FileModel fileModel = fileService.findFile(fileId);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
        response.getOutputStream().write(fileModel.getData());
        response.getOutputStream().close();
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session and clear authentication
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().invalidate();

        // Redirect to the login page
        return  "redirect:/login";
    }


    @PostMapping("/addnote")
    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
        if (noteForm.getNoteid() != null) {
            noteService.updateNote(noteForm);
        } else {
            int userId = noteService.getUser(authentication.getName()).getUserId();
            noteService.store(noteForm, userId);
        }
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam Integer noteid,  NoteForm noteForm, Model model) {
        logger.info("Deleting file with fileId: {}", noteid);
        noteService.deleteNote(noteid);
        logger.info("File deleted. Redirecting to home.");
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

}
