package com.mitsurin.tools.creating_best_party.controller;

import javax.servlet.http.HttpSession;

import com.mitsurin.tools.creating_best_party.security.CreatingBestPartyAuthentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
  @GetMapping("")
  public String mypage(ModelMap model, HttpSession session){
    if(!CreatingBestPartyAuthentication.isAuthenticated(session.getId())) return "redirect:/login?url=/mypage";

    return "mypage.html";
  }
}
