package kz.sud.cabinet.officeRest.web;

import kz.sud.cabinet.officeRest.ejb.DicManagment;
import kz.sud.cabinet.officeRest.persistence.Region;
import kz.sud.cabinet.officeRest.utils.StaticData;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@ManagedBean(name = "applicationModel")
@ViewScoped
public class ApplicationModel {

    @EJB
    private DicManagment managment;
    private List<Region> regions;
    private AddRegion addRegion;

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
        if(regions==null){
            regions = managment.getRegionList();
        }
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
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
}
