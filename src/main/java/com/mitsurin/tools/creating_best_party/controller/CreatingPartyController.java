package com.mitsurin.tools.creating_best_party.controller;

import javax.servlet.http.HttpSession;

import java.util.List;

import com.mitsurin.tools.creating_best_party.security.CreatingBestPartyAuthentication;
import com.mitsurin.tools.creating_best_party.model.compatibility.Type;
import com.mitsurin.tools.creating_best_party.model.party.CreatingParty;
import com.mitsurin.tools.creating_best_party.model.party.Party;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/CreatingParty")
public class CreatingPartyController {
  @GetMapping("")
  public String page(HttpSession session){
    if(!CreatingBestPartyAuthentication.isAuthenticated(session.getId())){
      return "redirect:/login?url=/CreatingParty";
    }
    return "creating_party.html";
  }

  @GetMapping("/Result")
  public ResponseEntity<String> getResult(@RequestParam("t1") String t1, @RequestParam("t2") String t2, HttpSession session){
    if(!CreatingBestPartyAuthentication.isAuthenticated(session.getId())){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpStatus status = HttpStatus.OK;

    Type type1 = Type.getTypeByTypeName(t1);
    Type type2 = Type.getTypeByTypeName(t2);

    CreatingParty cp = new CreatingParty();
    List<Party> result = cp.calculateOptionParties(type1, type2);

    String content = Party.convertPartyList2JSON(result);

    return new ResponseEntity<>(content, headers, status);
  }
}
