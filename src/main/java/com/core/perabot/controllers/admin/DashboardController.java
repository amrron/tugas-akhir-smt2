package com.core.perabot.controllers.admin;

import com.core.perabot.model.models.Pembeli;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/admin")
    public String index(HttpSession session){
        Boolean status = (Boolean) session.getAttribute("admin");

        if (status != null){
            return "admin/dashboard";
        }
        else {
            return "redirect:/admin/login";
        }
    }
}
