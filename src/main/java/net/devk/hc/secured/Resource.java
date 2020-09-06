package net.devk.hc.secured;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/")
public class Resource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("public")
	public Message getPublic(@Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		return new Message("public");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("secured")
	public Message getSecured(@Context SecurityContext sc) {
		return new Message(sc.getUserPrincipal().getName());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("admin")
	public Message getAdmin() {
		return new Message("admin");
	}

}