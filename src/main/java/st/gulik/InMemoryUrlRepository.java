package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class InMemoryUrlRepository implements UrlRepository {

    private final Map<String, String> miniToMax = new HashMap<>();
    private final Map<String, String> maxToMini = new HashMap<>();

    @Override
    public void save(String key, String originalUrl) {
        miniToMax.put(key, originalUrl);
        maxToMini.put(originalUrl, key);
    }

    @Override
    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(miniToMax.get(key));
    }

    @Override
    public Optional<String> findByOriginalUrl(String originalUrl) {
        return Optional.ofNullable(maxToMini.get(originalUrl));
    }

    @Override
    public boolean keyExists(String key) {
        return miniToMax.containsKey(key);
    }
    }