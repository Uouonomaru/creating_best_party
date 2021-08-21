package com.mitsurin.tools.creating_best_party.controller;

import javax.servlet.http.HttpSession;

import com.mitsurin.tools.creating_best_party.model.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/new_account")
public class NewAccountController {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;

    @GetMapping("")
    public String newAccount(HttpSession session, ModelMap model) {
        model.addAttribute("_csrf", ENCODER.encode(session.getId()));
        return "new_account.html";
    }

    @PostMapping("")
    public String registNewAccount(
        @RequestParam String user_id, 
        @RequestParam String user_pwd1, 
        @RequestParam String user_pwd2, 
        @RequestParam String _csrf, 
        ModelMap model, 
        HttpSession session
    ) {
        if(
            user_id == null || user_id.equals("") ||
            user_pwd1 == null || user_pwd1.equals("") ||
            user_pwd2 == null || user_pwd2.equals("") ||
            _csrf == null
        ) {
            model.addAttribute("error", "すべての項目を入力してください");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "new_account.html";
        }

        if(!ENCODER.matches(session.getId(), _csrf)) {
            model.addAttribute("error", "フォームが不正です．リトライしてください");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "new_account.html";
        }

        int exist = this.userMapper.isExist(user_id);
        if(exist != 0) {
            model.addAttribute("error", "既に存在するユーザIDです");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "new_account.html";
        }

        if(!user_pwd1.equals(user_pwd2)) {
            model.addAttribute("error", "パスワードが一致していません");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "new_account.html";
        }

        boolean result = this.userMapper.registNewUser(user_id, ENCODER.encode(user_pwd1));
        if(!result) {
            model.addAttribute("error", "DBのエラーです．管理者に連絡してください");
            model.addAttribute("_csrf", ENCODER.encode(session.getId()));
            return "new_account.html";
        }
        
        model.addAttribute("_csrf", ENCODER.encode(session.getId()));
        return "login.html";
    }
}
