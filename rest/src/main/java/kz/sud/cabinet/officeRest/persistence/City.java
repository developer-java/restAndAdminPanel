package kz.sud.cabinet.officeRest.persistence;

import javax.persistence.*;

@Entity
@Table(name = "CITY")
public class City {
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
}
