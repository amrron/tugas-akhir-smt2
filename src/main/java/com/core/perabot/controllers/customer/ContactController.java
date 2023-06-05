package com.core.perabot.controllers.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String index(){
        return "customer/contact";
    }

    @PostMapping("/contact/send")
    public String contact(@RequestParam("nama") String nama, @RequestParam("no_hp") String no_hp, @RequestParam("pesan") String pesan){
        String text = "Hallo saya " + nama + " dengan nomer hp " + no_hp + ". Pesan:%0A" + pesan;
        return "redirect:https://wa.me/6285648234765?text=" + text;
    }
}
