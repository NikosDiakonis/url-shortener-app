package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class UrlShortenerService {

    private static final String BASE_URL = "www.minimise.com/";

    @Inject
    UrlRepository repository;

    public String shortener(String originalUrl) {
        if (repository.findByOriginalUrl(originalUrl).isPresent()) {
            return BASE_URL + repository.findByOriginalUrl(originalUrl).get();
        } else {
            int urlToInteger = originalUrl.hashCode();
            String miniUrl = Integer.toHexString(urlToInteger);
            miniUrl = getUniqueUrlKey(miniUrl);

            String miniFinal = BASE_URL + miniUrl;
            repository.save(miniUrl, originalUrl);
            return miniFinal;
        }
    }


    private String getUniqueUrlKey(String miniUrl) {
        String currentKey = miniUrl;
        while (repository.keyExists(currentKey)) {
            String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            int index = random.nextInt(chars.length());
            currentKey = currentKey + chars.charAt(index);
        }
        return currentKey;
    }


    public Optional<String> getOriginalUrl(String shortUrlKey) {
        return repository.findByKey(shortUrlKey);
    }
}

