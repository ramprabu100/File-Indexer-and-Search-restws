package com.rest.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Produces({ "application/xml", "application/json" })
public interface FileSearchService {
	@GET
	@Path("/search/{fileName}/")
	public  FileList searchFile(@PathParam("fileName") String query);
	
}
