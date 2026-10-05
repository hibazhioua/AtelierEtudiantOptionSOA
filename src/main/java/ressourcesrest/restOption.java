package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {
   public OptionBusiness optB= new OptionBusiness();

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getAllOptions(@QueryParam("D") String D){
      if (D ==null) {
         List <Option>l= optB.getListeOptions();
         return Response.status(200).entity(l).build();

      }
      else {
         List <Option>l= optB.getOptionsByDomaine(D);
         return Response.status(200).entity(l).build();

      }}


   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response addOption(Option option) {
      if (optB.addOption(option)) {
         return Response.status(Response.Status.CREATED).entity(option).build();
      }
      return Response.status(Response.Status.NOT_FOUND).entity("Erreur lors de l'ajout").build();
   }

   @PUT
   @Path("{code}")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response updateOption(@PathParam("code") int code, Option updatedOption) {
      if (optB.updateOption(code, updatedOption)) {
         return Response.status(200).entity(updatedOption).build();
      }
      return Response.status(Response.Status.NOT_FOUND).entity("Option introuvable").build();
   }

   @DELETE
   @Path("{code}")
   @Produces(MediaType.TEXT_PLAIN)
   public Response deleteOption(@PathParam("code") int code) {
      if (optB.deleteOption(code)) {
         return Response.status(200).entity("Option supprimée avec succès").build();
      }
      return Response.status(Response.Status.NOT_FOUND).entity("Option non trouvée").build();
   }


}
