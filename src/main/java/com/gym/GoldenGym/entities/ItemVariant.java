package com.gym.GoldenGym.entities;

import java.util.Set;

import com.gym.GoldenGym.utils.DateUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ItemVariant extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String variantQuantity;
    private double variantPrice;
    private double offerPrice;
    private String offerStartDateTime;//format yyyy-MM-dd HH:mm:ss
    private String offerEndDateTime;//format yyyy-MM-dd HH:mm:ss
    @ManyToOne
    private Item item;
    @OneToMany
    private Set<FileEntity> images;

    public void setOfferStartDateTime(String offerStartDateTime) {
        if(offerStartDateTime != null) {
            try {
                //try to parse it to check if correct format
                this.offerStartDateTime = DateUtils.getDate(offerStartDateTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setOfferEndDateTime(String offerEndDateTime) {
        if(offerEndDateTime != null) {
            try {
                //try to parse it to check if correct format
                this.offerEndDateTime = DateUtils.getDate(offerEndDateTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
