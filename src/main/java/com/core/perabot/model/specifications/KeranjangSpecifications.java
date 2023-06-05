package com.core.perabot.model.specifications;

import com.core.perabot.model.models.Barang;
import com.core.perabot.model.models.Keranjang;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class KeranjangSpecifications {

    public static Specification<Keranjang> keranjangBarang(){
        return (root, query, criteriaBuilder) -> {
            Join<Keranjang, Barang> joinBarang = root.join("id_barang", JoinType.LEFT);
            return null;
        };
    }

    public static Specification<Keranjang> cariKeranjangByIdPembeli(Long id){
        return (root, query, criteriaBuilder) -> {
            if(id != null){
                return criteriaBuilder.equal(root.get("id_pembeli"), id);
            }
            return null;
        };
    }

    public static Specification<Keranjang> cariKeranjangByStatusPesan(Boolean status_pesan){
        return (root, query, criteriaBuilder) -> {
            if(status_pesan != null){
                return criteriaBuilder.equal(root.get("status_pesan"), status_pesan);
            }
            return null;
        };
    }

}
