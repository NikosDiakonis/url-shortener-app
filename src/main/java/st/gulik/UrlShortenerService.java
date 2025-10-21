package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;


@ApplicationScoped
public class UrlShortenerService {

    private static final String BASE_URL = "www.minimise.com/";
    //TODO: Retrieve from properties

    @Inject
    UrlRepository repository;
    @Inject
    KeyGenerator keyGenerator;
    //TODO: make private


    public String shortener(String originalUrl) {
        //TODO: rename to shorten
        if (repository.findByOriginalUrl(originalUrl).isPresent()) {
            return BASE_URL + repository.findByOriginalUrl(originalUrl).get();
        } else {
           String  miniUrlKey = keyGenerator.generateKey(originalUrl);
           repository.save(miniUrlKey,originalUrl);
            return BASE_URL + miniUrlKey;
        }
    }

    public Optional<String> getOriginalUrl(String shortUrlKey) {
        return repository.findByKey(shortUrlKey);
    }
}
