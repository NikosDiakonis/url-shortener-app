package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class UrlRepository {

    private final Map<String, String> miniToMax = new HashMap<>();
    private final Map<String, String> maxToMini = new HashMap<>();

    public void save(String key, String originalUrl) {
        miniToMax.put(key, originalUrl);
        maxToMini.put(originalUrl, key);
    }

    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(miniToMax.get(key));
    }

    public Optional<String> findByOriginalUrl(String originalUrl) {
        return Optional.ofNullable(maxToMini.get(originalUrl));
    }

    public boolean keyExists(String key) {
        return miniToMax.containsKey(key);
    }
}

