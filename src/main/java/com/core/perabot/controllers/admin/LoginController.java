package com.core.perabot.controllers.admin;

import com.core.perabot.model.models.UserAdmin;
import com.core.perabot.model.repository.AdminRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final AdminRepository adminRepository;

    public LoginController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("/admin/login")
    public String index(){
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request){
//        mencari admin berdasarkan email
        Optional<UserAdmin> optonalAdmin = Optional.ofNullable(adminRepository.findByEmail(email));
//        jika detemukan
        if (optonalAdmin.isPresent()){
            UserAdmin dbadmin = optonalAdmin.get();
//            cek password
            if(dbadmin.getPassword().equals(password)){
                Boolean authenticated = true;
//                set session jike berhasil login
                request.getSession().setAttribute("admin", authenticated);
                return "redirect:/admin";
            }
            else {
                return "redirect:/admin/login";
            }
        }
        else {
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}


