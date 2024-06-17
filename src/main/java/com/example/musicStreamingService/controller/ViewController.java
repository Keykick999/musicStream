package com.example.musicStreamingService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/upload")
    public String uploadAudio() {
        return "upload";
    }

    @GetMapping("/music")
    public String showMusics() {
        return "music";
    }
}
