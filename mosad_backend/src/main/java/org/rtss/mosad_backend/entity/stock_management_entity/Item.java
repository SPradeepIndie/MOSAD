package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId",nullable = false,unique = true)
    private Long itemId;
    @Column(name = "itemName")
    private String itemName;
    @Column(name = "itemDescription")
    private String itemDescription;
    @Column(name = "companyPrice")
    private double companyPrice;
    @Column(name = "reatailPrice")
    private double retailPrice;
    @Column(name = "discount")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ItemBranch> itemBranches;





}
