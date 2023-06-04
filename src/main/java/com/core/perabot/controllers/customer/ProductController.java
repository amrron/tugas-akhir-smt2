package com.core.perabot.controllers.client;

import com.core.perabot.model.models.Barang;
import com.core.perabot.model.repository.BarangRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    private final BarangRepository barangRepository;


    public ProductController(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    @GetMapping("/detail-product/{id}")
    public String index(Model model, @PathVariable("id") String id) {
        Barang barang = barangRepository.findById_barang(Integer.valueOf(id));
        model.addAttribute("barang", barang);
        return "product";
    }
}
