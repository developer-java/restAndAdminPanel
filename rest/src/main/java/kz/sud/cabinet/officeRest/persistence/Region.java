package kz.sud.cabinet.officeRest.persistence;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "REGION")
public class Region {
    @XmlTransient
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALUE_RU")
    private String valueRu;
    @Column(name = "VALUE_KZ")
    private String valueKz;
    @Column(name = "CODE")
    private String code;
    @Column(name = "IMAGE")
    private String imageUrl;
    @Column(name = "COUNT_SIGHT")
    private Long countSight;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCountSight() {
        return countSight;
    }

    public void setCountSight(Long countSight) {
        this.countSight = countSight;
    }
}
