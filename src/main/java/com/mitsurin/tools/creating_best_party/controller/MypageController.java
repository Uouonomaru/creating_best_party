package com.mitsurin.tools.creating_best_party.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
  @GetMapping("")
  public String mypage(ModelMap model){
    return "mypage.html";
  }
}
