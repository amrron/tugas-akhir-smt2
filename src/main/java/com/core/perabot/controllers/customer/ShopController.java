package com.core.perabot.controllers.customer;

import com.core.perabot.model.models.Barang;
import com.core.perabot.model.models.Kategori;
import com.core.perabot.model.models.Keranjang;
import com.core.perabot.model.repository.BarangRepository;
import com.core.perabot.model.repository.KategoriRepository;
import com.core.perabot.model.repository.KeranjangRepository;
import com.core.perabot.model.specifications.BarangSpecifications;
import com.core.perabot.services.Services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.*;

@Controller
public class ShopController {

    @Autowired
    private Services services;
    private final BarangRepository barangRepository;
    private final KategoriRepository kategoriRepository;
    private final KeranjangRepository keranjangRepository;

    public ShopController(BarangRepository barangRepository, KategoriRepository kategoriRepository, KeranjangRepository keranjangRepository) {
        this.barangRepository = barangRepository;
        this.kategoriRepository = kategoriRepository;
        this.keranjangRepository = keranjangRepository;
    }

    @GetMapping("/shop")
    public String index(Model model, HttpServletRequest req) {
        // Dapatkan semua query param
        Map<String, String> queryParams = services.parseQueryParams(req.getQueryString());
        // Dapatkan semua produk beserta kategori
        Specification<Barang> joinKategori = BarangSpecifications.barangBesertaKategori();
        List<Barang> products = barangRepository.findAll(joinKategori);
        //
        List<Kategori> categories = kategoriRepository.findAll();

        // Terdapat 4 filter, terdapat 16 kemungkinan filter.
        // Tidak menggunakan 16 query, tetapi hanya 4 method.
        boolean filterEmpty = false;
        if(!queryParams.isEmpty()){
            // Get category parameter
            String ctg = queryParams.getOrDefault("ctg", null);
            // Get Search parameter
            String src = queryParams.getOrDefault("src", null);
            // Get price parameter
            String price = queryParams.getOrDefault("price", null);
            String[] priceParse = null;
            if(price != null){
                priceParse = URLDecoder.decode(queryParams.getOrDefault("price", null)).split("\\|");
            }
            // Get Filter Dropdown parameter
            boolean bestSeller = false;
            boolean latest = false;
            boolean lowToHigh = false;
            boolean highToLow = false;
            String fs = queryParams.getOrDefault("fs", null);
            if(fs != null){
                bestSeller = "bestSeller".equals(fs);
                latest = "latest".equals(fs);
                lowToHigh = "lowToHigh".equals(fs);
                highToLow = "highToLow".equals(fs);
            }

            Specification<Barang> spec = joinKategori.and(BarangSpecifications.cariBarangByKategori(ctg))
                    .and(BarangSpecifications.cariByNamaBarang(src))
                    .and(BarangSpecifications.cariByBetweenHarga(priceParse))
                    .and(BarangSpecifications.cariByTerlaris(bestSeller))
                    .and(BarangSpecifications.cariByTerbaru(latest))
                    .and(BarangSpecifications.hargaRendahKeTinggi(lowToHigh))
                    .and(BarangSpecifications.hargaTinggiKeRendah(highToLow));
            products = barangRepository.findAll(spec);
            if(products.isEmpty()){
                filterEmpty = true;
            }
        }

        // Count Product By category
        List<Long> productCounts = new ArrayList<>();
        for (Kategori kategori : categories) {
            Specification<Barang> spec = BarangSpecifications.hitungBarangByKategori(kategori);
            Long count = barangRepository.count(spec);
            productCounts.add(count);
        }


        // Products
        model.addAttribute("databarang", products);
        // Filter barang is empty
        model.addAttribute("filterEmpty", filterEmpty);
        // Categories
        model.addAttribute("datakategori", categories);
        // Product count
        model.addAttribute("productCounts", productCounts);

        model.addAttribute("tes", null); // debug

        return "customer/shop";
    }

    @PostMapping("/shop/addcart")
    public String addCart(@ModelAttribute("keranjang")Keranjang keranjang, HttpSession session){
        Boolean status = (Boolean) session.getAttribute("authenticated");

        if (status != null){
            Long id = (Long) session.getAttribute("id_pembeli");

            Long id_barang = keranjang.getId_barang().getId_barang();
            Barang dbBarang = barangRepository.findById_barang(Math.toIntExact(id_barang));
            Integer subtotal = dbBarang.getHarga() * keranjang.getJumlah();

            keranjang.setId_pembeli(id);
            keranjang.setSub_total(subtotal);
            keranjang.setStatus_pesan(false);

            keranjangRepository.save(keranjang);

            return "redirect:/cart";
        }
        else {
            return "redirect:/auth";
        }    }

}