package com.core.perabot.controllers.client;

import com.core.perabot.model.models.Barang;
import com.core.perabot.model.repository.BarangRepository;
import com.core.perabot.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShopController {

    private final BarangRepository barangRepository;

    public ShopController(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    @GetMapping("/shop")
    public String index(Model model){
        List<Barang> barang = barangRepository.findAllBy(true);
        model.addAttribute("databarang", barang);
        return "shop";
    }
}
