package kz.sud.cabinet.officeRest.persistence;

import org.codehaus.jackson.annotate.JsonIgnore;

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
    @JsonIgnore
    private Region paternId;
    @Column(name = "DESCRIPTION_RU")
    private String descriptionRu;
    @Column(name = "DESCRIPTION_KZ")
    private String descriptionKz;
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
    @Column(name = "ADDRESS_RU")
    private String addressRu;
    @Column(name = "ADDRESS_KZ")
    private String addressKz;
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    public Sight() {
    }

    public Sight(Long id,String valueRu, String valueKz, String descriptionRu, String descriptionKz, String imageUrl, String workTime, boolean payment, String price, int ratting, String contact, String addressRu, String addressKz, Category category) {
        this.id = id;
        this.valueRu = valueRu;
        this.valueKz = valueKz;
        this.descriptionRu = descriptionRu;
        this.descriptionKz = descriptionKz;
        this.imageUrl = imageUrl;
        this.workTime = workTime;
        this.payment = payment;
        this.price = price;
        this.ratting = ratting;
        this.contact = contact;
        this.addressRu = addressRu;
        this.addressKz = addressKz;
        this.category = category;
    }

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

    public Region getPaternId() {
        return paternId;
    }

    public void setPaternId(Region paternId) {
        this.paternId = paternId;
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

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionKz() {
        return descriptionKz;
    }

    public void setDescriptionKz(String descriptionKz) {
        this.descriptionKz = descriptionKz;
    }

    public String getAddressRu() {
        return addressRu;
    }

    public void setAddressRu(String addressRu) {
        this.addressRu = addressRu;
    }

    public String getAddressKz() {
        return addressKz;
    }

    public void setAddressKz(String addressKz) {
        this.addressKz = addressKz;
    }

    public enum Category{
        HISTORICAL_OBJECTS("Исторические обьекты"),REST_ZONE("Зона отдыха"),SANATORIUM("Санаторий");
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
