package br.com.joaog.ws;

import br.com.joaog.model.Pessoa;
import br.com.joaog.service.PessoaService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@Path("pessoa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

    @Inject
    private PessoaService pessoaService;

    @GET
    @Path("listar")
    public List<Pessoa> listarPessoas() {
        return pessoaService.findAll();
    }

    @GET
    @Path("buscar/{codigo}")
    public Pessoa listarPessoas(@PathParam("codigo") Long codigo) {
        return pessoaService.findById(codigo);
    }

    @POST
    @Path("cadastrar")
    public Response salvar(Pessoa pessoa) {
        try {
            pessoaService.save(pessoa);
            return Response.ok().build();
        } catch (ConstraintViolationException cvex) {
            final Set<String> erros = cvex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
            return Response.status(Response.Status.BAD_REQUEST).entity(erros).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Instabilidade no serviço").build();
        }
    }
}

