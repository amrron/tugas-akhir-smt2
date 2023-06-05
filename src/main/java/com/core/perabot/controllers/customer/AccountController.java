package com.core.perabot.controllers.customer;

import com.core.perabot.model.models.Pembeli;
import com.core.perabot.model.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
public class AccountController {

    private final UserRepository userRepository;

    public AccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/account")
    String index(Model model, HttpSession session){
        Boolean status = (Boolean) session.getAttribute("authenticated");

        if (status != null){
            Long id = (Long) session.getAttribute("id_pembeli");
            Pembeli pembeli = userRepository.findById_pembeli(id);
            model.addAttribute("pembeli", pembeli);
            return "customer/akun";
        }
        else {
            return "redirect:/auth";
        }

    }
}
