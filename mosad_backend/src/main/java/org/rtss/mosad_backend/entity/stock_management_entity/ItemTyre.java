package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;

@Entity
public class ItemTyre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="itemId",referencedColumnName = "itemId")
    private Item item;

    private String tyreSize;
    private String pattern;
    private String vehicleType;


    public ItemTyre() {
    }


    public ItemTyre(Item item, String tyreSize, String pattern, String vehicleType) {
        this.item = item;
        this.tyreSize = tyreSize;
        this.pattern = pattern;
        this.vehicleType = vehicleType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(String tyreSize) {
        this.tyreSize = tyreSize;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
