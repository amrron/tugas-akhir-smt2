package com.core.perabot.controllers.customer;


import com.core.perabot.model.models.Pembeli;
import com.core.perabot.model.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/auth")
    public String index(Model model, HttpSession session){
        Boolean status = (Boolean) session.getAttribute("authenticated");

        if (status != null){
            return "redirect:/account";
        }
        else {
            Pembeli pembeli = new Pembeli();
            model.addAttribute("pembeli", pembeli);
            return "customer/auth";
        }
    }

    @PostMapping("/auth/register")
    public String register(@ModelAttribute("pembeli")Pembeli pembeli){
        String encryptedPassword = bcrypt.encode(pembeli.getPassword());
        pembeli.setPassword(encryptedPassword);
        userRepository.save(pembeli);
        return "redirect:/account";
    }

    @PostMapping("/auth/login")
    public String login(@ModelAttribute("pembeli") Pembeli pembeli, HttpServletRequest request){
        Optional<Pembeli> optPembeli = Optional.ofNullable(userRepository.findByNoHp(pembeli.getNo_hp()));
        if(optPembeli.isPresent()){
            Pembeli dbPembeli = optPembeli.get();
            if(bcrypt.matches(pembeli.getPassword(), dbPembeli.getPassword())){
                Boolean authenticated = true;
                Long id_pembeli = (Long) dbPembeli.getId_pembeli();
                request.getSession().setAttribute("authenticated", authenticated);
                request.getSession().setAttribute("id_pembeli", id_pembeli);
                return "redirect:/account";
            }
            else {
                return "redirect:/auth";
            }
        }
        else {
            return "redirect:/auth";
        }
    }

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
