package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Random;

@ApplicationScoped
public class KeyGenerator {

    @Inject
    UrlRepository repository;

    public String generateKey(String originalUrl) {
        int urlToInteger = originalUrl.hashCode();
        String baseKey = Integer.toHexString(urlToInteger);
        return ensureUniqueness(baseKey);

    }

    private String ensureUniqueness(String key) {
        String currentKey = key;
        while (repository.keyExists(currentKey)) {
            String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            int index = random.nextInt(chars.length());
            currentKey = currentKey + chars.charAt(index);
        }
        return currentKey;
    }
}
