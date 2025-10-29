package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.DateUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class ServiceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private double offerPrice;
    private String offerStartDateTime;
    private String offerEndDateTime;
    @OneToOne
    private FileEntity image;
    @ManyToOne
    private User assignedTo;


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
