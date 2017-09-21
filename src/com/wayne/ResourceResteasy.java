package com.wayne;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("path2")
public class ResourceResteasy {
    @GET
    @Path("/getstring")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
    	System.out.printf("ResourceJersey sayHello called\n");
        return "Hello World";
    }
    
    @POST
    @Path("/post1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void post1(@Context HttpServletRequest req, @Context HttpServletResponse rsp) 
    		throws IOException {
    	System.out.printf("ResourceJersey post1 called\n");
    	rsp.getWriter().write("jersey_servlet_dopost1\n");
    	rsp.getWriter().flush();
    }
    
    @POST
    @Path("/post2")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void post2(@Context HttpServletRequest req, @Context HttpServletResponse rsp) 
    		throws IOException {
    	System.out.printf("ResourceJersey post2 called\n");
    	rsp.getWriter().write("jersey_servlet_dopost2\n");
    	rsp.getWriter().flush();
    }
    
    @GET
    @Path("/get1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void get1(@Context HttpServletRequest req, @Context HttpServletResponse rsp) 
    		throws IOException {
    	System.out.printf("ResourceJersey get1 called\n");
    	rsp.getWriter().write("jersey_servlet_doget1\n");
    	rsp.getWriter().flush();
    }
    
    @GET
    @Path("/get2")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void get2(@Context HttpServletRequest req, @Context HttpServletResponse rsp) 
    		throws IOException {
    	System.out.printf("ResourceJersey get2 called\n");
    	rsp.getWriter().write("jersey_servlet_doget2\n");
    	rsp.getWriter().flush();
    }
}
