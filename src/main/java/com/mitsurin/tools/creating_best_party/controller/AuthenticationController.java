package com.mitsurin.tools.creating_best_party.controller;

import javax.servlet.http.HttpSession;

import com.mitsurin.tools.creating_best_party.model.UserMapper;
import com.mitsurin.tools.creating_best_party.security.CreatingBestPartyAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static final String logoutSuccesssUrl = "/";

    @Autowired
    private UserMapper userMapper;

    private String url;

    @GetMapping("/login")
    public String login(@RequestParam String url, ModelMap model, HttpSession session) {
        if(url == null || url.equals("")) {
            model.addAttribute("error", "ログイン成功時の遷移先をGETで指定してください");
        } else {
            this.url = url;
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
        }

        return "login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam String user_id, @RequestParam String user_pwd, @RequestParam String _csrf, HttpSession session, ModelMap model) {
        if(
            user_id == null || user_id.equals("") ||
            user_pwd == null || user_pwd.equals("") ||
            _csrf == null
        ) {
            model.addAttribute("error", "すべての情報を入力してください");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "login.html";
        }

        if(!ENCODER.matches(session.getId(), _csrf)) {
            model.addAttribute("error", "フォームが不正です．リトライしてください");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "login.html";
        }

        boolean result = CreatingBestPartyAuthentication.login(user_id, user_pwd, session.getId(), this.userMapper);

        if(!result) {
            model.addAttribute("error", "入力情報が誤っています");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "login.html";
        }

        return "redirect:" + this.url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        CreatingBestPartyAuthentication.logout(session.getId());
        
        return "redirect:" + AuthenticationController.logoutSuccesssUrl;
    }
}
