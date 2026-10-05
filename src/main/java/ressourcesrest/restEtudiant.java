package ressourcesrest;

import entities.Etudiant;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class restEtudiant {
    public EtudiantBusiness etdB = new EtudiantBusiness();

    // GET : liste de tous les étudiants OU recherche par identifiant (?id=...)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants(@QueryParam("id") String id) {
        if (id == null) {
            List<Etudiant> l = etdB.getAllEtudiants();
            return Response.status(200).entity(l).build();
        } else {
            Etudiant e = etdB.getEtudiantByIdentifiant(id);
            if (e != null) {
                return Response.status(200).entity(e).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Etudiant non trouve").build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Données de l'étudiant ou option absentes")
                    .build();
        }
        try {
            if (etdB.addEtudiant(etudiant)) {
                return Response.status(Response.Status.CREATED).entity(etudiant).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Option associée inexistante")
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de l'ajout : " + ex.getMessage())
                    .build();
        }
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant updatedEtudiant) {
        if (etdB.updateEtudiant(id, updatedEtudiant)) {
            return Response.status(200).entity(updatedEtudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Etudiant introuvable").build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etdB.deleteEtudiant(id)) {
            return Response.status(200).entity("Etudiant supprime avec succes").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Etudiant non trouve").build();
    }

}