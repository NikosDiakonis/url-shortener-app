package st.gulik;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Path("/")
public class UrlResource {

    @Inject
    UrlShortenerService service;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response shortenEndpoint(String urlToShorten) {

        String shortUrl = service.shortener(urlToShorten);
        return Response.ok(shortUrl).build();
    }

    @GET
    @Path("/{shortUrlKey}")
    public Response expandEndpoint(@PathParam("shortUrlKey") String shortUrlKey) throws URISyntaxException {
        Optional<String> originalUrlOpt = service.getOriginalUrl(shortUrlKey);

        if (originalUrlOpt.isPresent()) {
            return Response.status(Response.Status.FOUND)
                    .location(new URI(originalUrlOpt.get()))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("URL not found for key: " + shortUrlKey)
                    .build();
        }
    }
}