package com.core.perabot.controllers.client;

import com.core.perabot.model.models.Barang;
import com.core.perabot.model.repository.BarangRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GalleryController {

    private final BarangRepository barangRepository;

    public GalleryController(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    @GetMapping("/gallery")
    public String index(Model model){
        List<Barang> barang = barangRepository.getAll();
        model.addAttribute("barang", barang);
        return "customer/gallery";
    }
}
