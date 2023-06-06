package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("files", this.fileService.getFiles());
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session and clear authentication
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().invalidate();

        // Redirect to the login page
        return  "redirect:/login?logout";
    }

}
