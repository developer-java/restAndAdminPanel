package com.diplomthesis.app.persistence;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "SIGHT")
public class Sight {
    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALUE_RU")
    private String valueRu;
    @Column(name = "VALUE_KZ")
    private String valueKz;
    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE")
    private String imageUrl;
    @Column(name = "WORK_TIME")
    private String workTime;
    @Column(name = "IS_PAYMENT")
    private boolean payment;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "RATTING")
    private int ratting;
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "ADDRESS")
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueRu() {
        return valueRu;
    }

    public void setValueRu(String valueRu) {
        this.valueRu = valueRu;
    }

    public String getValueKz() {
        return valueKz;
    }

    public void setValueKz(String valueKz) {
        this.valueKz = valueKz;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRatting() {
        return ratting;
    }

    public void setRatting(int ratting) {
        this.ratting = ratting;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Transient
    public Category[] getCategoryList(){
        Category[] categories = Category.values();
        return categories;
    }
    public enum Category{
        ALL("Все"),HOTEL("Отель"),RESTAURANT("Рестораны"),MUSEUM("Музей"),MONUMENT("Монументы"),HISTORICAL_OBJECTS("Исторические обьекты"),RELIGIOUS_OBJECTS("Религиозные объекты"),REST_ZONE("Зона отдыха"),SANATORIUM("Санаторий");
        private String value;
        private int resId;

        Category(String value) {
            this.value = value;
        }

        Category(int resId) {
            this.resId = resId;
        }

        public String getValue() {
            return value;
        }

        public int getResId() {
            return resId;
        }
    }
}
