package com.core.perabot.controllers.customer;

import com.core.perabot.model.models.Barang;
import com.core.perabot.model.models.Keranjang;
import com.core.perabot.model.models.Pembeli;
import com.core.perabot.model.repository.BarangRepository;
import com.core.perabot.model.repository.KeranjangRepository;
import com.core.perabot.model.specifications.KeranjangSpecifications;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private final KeranjangRepository keranjangRepository;
    private final BarangRepository barangRepository;

    public CartController(KeranjangRepository keranjangRepository, BarangRepository barangRepository) {
        this.keranjangRepository = keranjangRepository;
        this.barangRepository = barangRepository;
    }

    @GetMapping("/cart")
    public String index(HttpSession session, Model model){
        Boolean status = (Boolean) session.getAttribute("authenticated");

        if (status != null){
            Long id = (Long) session.getAttribute("id_pembeli");

            Specification<Keranjang> joinBarang = KeranjangSpecifications.keranjangBarang();

            List<Keranjang> keranjangs = keranjangRepository.findAll(joinBarang);

            Specification<Keranjang> spec = joinBarang.and(KeranjangSpecifications.cariKeranjangByIdPembeli(id)).and(KeranjangSpecifications.cariKeranjangByStatusPesan(false));

            List<Keranjang> barangdikeranjang = keranjangRepository.findAll(spec);

            Integer total = keranjangRepository.getTotal(id);

            Keranjang keranjang = new Keranjang();

            model.addAttribute("barangdikeranjang", barangdikeranjang);
            model.addAttribute("total", total);
            model.addAttribute("keranjang", keranjang);

            return "customer/cart";
        }
        else {
            return "redirect:/auth";
        }
    }

    @PostMapping("/cart/update")
    public String updateCart(@ModelAttribute("keranjang") Keranjang keranjang){
        Integer jumlah = keranjang.getJumlah();
        Long id = keranjang.getId_keranjang();
        Barang barang = (Barang) keranjang.getId_barang();
        Integer harga = barang.getHarga();

        Optional<Keranjang> optionalKeranjang = keranjangRepository.findById(id);
        if(optionalKeranjang.isPresent()){
            Keranjang updateKeranjang = optionalKeranjang.get();

            updateKeranjang.setJumlah(jumlah);
            updateKeranjang.setSub_total(jumlah * harga);
            keranjangRepository.save(updateKeranjang);
        }
//        keranjangRepository.updateJumlah(jumlah, id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/delete")
    public String deleteCart(@ModelAttribute("id_keranjang") Keranjang keranjang){
        keranjangRepository.deleteById(Long.valueOf(keranjang.getId_keranjang()));
        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String chechout(Model model, HttpSession session){
        Boolean status = (Boolean) session.getAttribute("authenticated");

        if (status != null){
            Long id = (Long) session.getAttribute("id_pembeli");

            String pesan = "Hallo, saya ingin membeli: %0A";

            List<Keranjang> listkeranjang = keranjangRepository.findById_pembeli(id);
            for (Keranjang keranjang: listkeranjang){
                pesan += "- " + keranjang.getJumlah() + " buah " + keranjang.getId_barang().getNama_barang() + "%0A";
                keranjang.setStatus_pesan(true);
                keranjangRepository.save(keranjang);
            }

            pesan += "Apakah semua barang tersebut tersedia? Terima Kasih.";

            return "redirect:https:/wa.me/6285648234765?text=" + pesan;
        }
        else {
            return "redirect:/auth";
        }
    }
}