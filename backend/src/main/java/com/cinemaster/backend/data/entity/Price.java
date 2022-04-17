package com.cinemaster.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

    @Column(name = "standard_price")
    private Double standardPrice;

    @Column(name = "premium_price")
    private Double premiumPrice;

    @Column(name = "vip_price")
    private Double vipPrice;

    public Double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Double getPremiumPrice() {
        return premiumPrice;
    }

    public void setPremiumPrice(Double premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }
}
