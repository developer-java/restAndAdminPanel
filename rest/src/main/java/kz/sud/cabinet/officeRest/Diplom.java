package kz.sud.cabinet.officeRest;

import kz.sud.cabinet.officeRest.ejb.DicManagment;
import kz.sud.cabinet.officeRest.persistence.Coment;
import kz.sud.cabinet.officeRest.persistence.Region;
import kz.sud.cabinet.officeRest.persistence.Sight;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/dip")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class Diplom {

    private DicManagment baseManagementEjb;
    public DicManagment getBaseManagementEjb() {
        if(baseManagementEjb == null) {
            try {
                InitialContext ctx = new InitialContext();
                baseManagementEjb = (DicManagment)ctx.lookup("java:global/officeRest/DicManagment");
            }
            catch(NamingException ne) {
                ne.printStackTrace();
            }
        }
        return baseManagementEjb;
    }
    @GET
    @Path("/regions")
    public List<Region> getRegionsAll() {
        List<Region> list = getBaseManagementEjb().getRegionList();
        return list;
    }
    @GET
    @Path("/sight/{id}")
    public List<Sight> getSightList(@PathParam(value = "id")Long id){
        List<Sight> list = getBaseManagementEjb().getSightList(id);
        return list;
    }
    @GET
    @Path("/sight/")
    public List<Sight> getSightListAll(){
        List<Sight> list = getBaseManagementEjb().getSightList();
        return list;
    }

    @GET
    @Path("/sight/category/{category}")
    public List<Sight> getSightListAll(@PathParam(value = "category")String category){
        List<Sight> list = null;
        if(category.equals("ALL")){
            list = getBaseManagementEjb().getSightList();
        }else {
            list = getBaseManagementEjb().getSightList(Sight.Category.valueOf(category));
        }
        return list;
    }

    @GET
    @Path("/coment/{sightId}/{message}/{date}")
    public List<Coment> addComent(@PathParam(value = "sightId")Long id, @PathParam(value = "message") String message,@PathParam(value = "date") String date){
        Coment coment = new Coment();
        coment.setDate(date);
        coment.setMessage(message);
        coment.setSightId(id);
        getBaseManagementEjb().add(coment);
        return getBaseManagementEjb().getComents(id);
    }

    @GET
    @Path("/coment/{id}")
    public List<Coment> getComentList(@PathParam(value = "id")Long id){
        List<Coment> list = getBaseManagementEjb().getComentList(id);
        return list;
    }

}
