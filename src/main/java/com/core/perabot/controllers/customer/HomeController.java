package com.core.perabot.controllers.client;

//import com.core.perabot.model.models.Pembeli;
//import com.core.perabot.model.repository.UserRepository;
//import com.core.perabot.services.ImageService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import com.core.perabot.model.models.Barang;
import com.core.perabot.model.models.Kategori;
import com.core.perabot.model.repository.BarangRepository;
import com.core.perabot.model.repository.KategoriRepository;
import com.core.perabot.model.repository.KeranjangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;

@Controller
public class HomeController {

    private final KategoriRepository kategoriRepository;
    private final BarangRepository barangRepository;

    public HomeController(KategoriRepository kategoriRepository, BarangRepository barangRepository) {
        this.kategoriRepository = kategoriRepository;
        this.barangRepository = barangRepository;
    }

//    private final UserRepository userRepository;
//    private final ImageService imageService;
//
//    public HomeController(UserRepository userRepository, ImageService imageService) {
//        this.userRepository = userRepository;
//        this.imageService = imageService;
//    }

    @GetMapping("/")
    public String index(Model model, HttpSession session){
//        List<Pembeli> users = userRepository.findByNamaPembeli("Eky");

        List<Kategori> kategori = kategoriRepository.findAll();
        List<Barang> terlaris = barangRepository.getTerlaris();

//
//        model.addAttribute("data1", users);
        model.addAttribute("kategori", kategori);
        model.addAttribute("terlaris", terlaris);
        return "customer/home";
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            String imageUrl = imageService.uploadImage(file);
//            return ResponseEntity.ok(imageUrl);
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
//        }
//    }
}
