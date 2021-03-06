package com.diplomthesis.app.web;

import com.diplomthesis.app.ejb.DicManagment;
import com.diplomthesis.app.persistence.City;
import com.diplomthesis.app.persistence.Region;
import com.diplomthesis.app.persistence.Sight;
import com.diplomthesis.app.persistence.SightC;
import com.diplomthesis.app.utils.StaticData;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ManagedBean(name = "applicationModel")
@SessionScoped
public class ApplicationModel {

    @EJB
    private DicManagment managment;
    private List<Region> regions;
    private AddRegion addRegion;
    private AddSight addSight;
    private List<Sight> sights;
    private AddCity addCity;
    private AddSightC addSightC;

    public AddSightC getAddSightC() {
        if(addSightC==null){
            addSightC = new AddSightC();
        }
        return addSightC;
    }

    public void clear(){
        sights = null;
    }

    public AddCity getAddCity() {
        if(addCity == null){
            addCity = new AddCity();
        }
        return addCity;
    }

    public void delete(Region region){
        File file = new File(StaticData.FILE_CONTAINER + region.getImageUrl());
        if(file.exists())
            file.delete();
        managment.delete(region);
    }

    public void delete(City city){
        managment.delete(city);
    }
    public void delete(Sight sight){
        File file = new File(StaticData.FILE_CONTAINER + sight.getImageUrl());
        if(file.exists())
            file.delete();
        managment.delete(sight);
    }
    public List<Sight> getSights(Region region) {
        return managment.getSightList(region.getId());
    }

    public void setSights(List<Sight> sights) {
        this.sights = sights;
    }


    public AddSight getAddSight() {
        if (addSight==null){
            addSight = new AddSight();
        }
        return addSight;
    }

    public AddRegion getAddRegion() {
        if(addRegion==null){
            addRegion=new AddRegion();
        }
        return addRegion;
    }

    public void setAddRegion(AddRegion addRegion) {
        this.addRegion = addRegion;
    }

    public List<Region> getRegions() {
        return managment.getRegionList();
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<City> getCityList(){
        return managment.getCityList();
    }



    public class AddRegion{
        private Region region;
        public Region getRegion() {
            if(region==null){
                region=new Region();
            }
            return region;
        }

        public void setRegion(Region region) {
            this.region = region;
        }

        public String add() throws IOException {
            managment.add(region);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
            region = null;
            return "";
        }

        public void addFile(FileUploadEvent event){
            if(this.region.getImageUrl()!=null){
                File file = new File(StaticData.FILE_CONTAINER + region.getImageUrl());
                file.delete();
            }
            String n = event.getUploadedFile().getName();
            n=n.replace(".","#");
            String[]arr = n.split("#");
            UploadedFile item = event.getUploadedFile();
            String fileName = UUID.randomUUID().toString().replace("-","");
            fileName = fileName+"."+arr[arr.length-1];
            try {
                File file = new File(StaticData.FILE_CONTAINER + fileName);
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(item.getData());
                fos.flush();
                fos.close();
                this.region.setImageUrl(fileName);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class AddSight{
        private Region region;
        private Sight sight;

        public Region getRegion() {
            if (region == null){
                region = new Region();
            }
            return region;
        }

        public void setRegion(Region region) throws IOException {
            this.region = region;
            FacesContext.getCurrentInstance().getExternalContext().redirect("/welcome/sight.xhtml?faces-redirect=true");
        }

        public Sight getSight() {
            if(sight == null){
                sight = new Sight();
            }
            return sight;
        }

        public void setSight(Sight sight) {
            this.sight = sight;
        }

        public void addFile(FileUploadEvent event){
            if(this.sight.getImageUrl()!=null){
                File file = new File(StaticData.FILE_CONTAINER + sight.getImageUrl());
                file.delete();
            }
            String n = event.getUploadedFile().getName();
            n=n.replace(".","#");
            String[]arr = n.split("#");
            UploadedFile item = event.getUploadedFile();
            String fileName = UUID.randomUUID().toString().replace("-","");
            fileName = fileName+"."+arr[arr.length-1];
            try {
                File file = new File(StaticData.FILE_CONTAINER + fileName);
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(item.getData());
                fos.flush();
                fos.close();
                this.sight.setImageUrl(fileName);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        public String add() throws IOException {
            sight.setRegion(region);
            managment.add(sight);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
            sight = null;
            return "";
        }
    }


    public class AddCity{
        private City city;
        private Long regId;

        public Long getRegId() {
            return regId;
        }

        public void setRegId(Long regId) {
            this.regId = regId;
        }

        public List<Region> getRegionList(){
            return managment.getRegionList();
        }

        public City getCity() {
            if(city==null){
                city = new City();
            }
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public void add() throws IOException {
            city.setRegion(managment.getRegion(getRegId()));
            managment.add(city);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
        }
    }

    public class AddSightC{
        private City city;
        private SightC sightC;

        public SightC getSightC() {
            if (sightC==null){
                sightC = new SightC();
            }
            return sightC;
        }
        public List<SightC> getSightCList(){
            return managment.getSightListC(city.getId());
        }
        public void setSightC(SightC sightC) {
            this.sightC = sightC;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) throws IOException {
            this.city = city;
            FacesContext.getCurrentInstance().getExternalContext().redirect("/welcome/city/sight/index.xhtml?faces-redirect=true");
        }

        public void addFile(FileUploadEvent event){
            if(this.sightC.getImageUrl()!=null){
                File file = new File(StaticData.FILE_CONTAINER + sightC.getImageUrl());
                file.delete();
            }
            String n = event.getUploadedFile().getName();
            n=n.replace(".","#");
            String[]arr = n.split("#");
            UploadedFile item = event.getUploadedFile();
            String fileName = UUID.randomUUID().toString().replace("-","");
            fileName = fileName+"."+arr[arr.length-1];
            try {
                File file = new File(StaticData.FILE_CONTAINER + fileName);
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(item.getData());
                fos.flush();
                fos.close();
                this.sightC.setImageUrl(fileName);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        public String add() throws IOException {
            sightC.setCity(city);
            managment.add(sightC);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
            sightC = null;
            addSightC = null;
            return "";
        }

    }
}
