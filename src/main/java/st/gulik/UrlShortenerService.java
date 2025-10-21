package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;


@ApplicationScoped
public class UrlShortenerService {

    @ConfigProperty(name = "url.base")
    private String baseUrl;

    @Inject
    UrlRepository repository;
    @Inject
    private KeyGenerator keyGenerator;



    public String shortener(String originalUrl) {
        //TODO: rename to shorten
        if (repository.findByOriginalUrl(originalUrl).isPresent()) {
            return baseUrl + repository.findByOriginalUrl(originalUrl).get();
        } else {
           String  miniUrlKey = keyGenerator.generateKey(originalUrl);
           repository.save(miniUrlKey,originalUrl);
            return baseUrl + miniUrlKey;
        }
    }

    public Optional<String> getOriginalUrl(String shortUrlKey) {
        return repository.findByKey(shortUrlKey);
    }
}
