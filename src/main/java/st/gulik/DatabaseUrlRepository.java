package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Primary; // Βεβαιώσου ότι υπάρχει αυτό το import
import jakarta.transaction.Transactional;
import java.util.Optional;

@Primary // Η σήμανση προτίμησης
@ApplicationScoped
public class DatabaseUrlRepository implements UrlRepository { // Υλοποιεί το interface

    @Override
    @Transactional // Σημαντικό για εγγραφή/αλλαγή δεδομένων
    public void save(String key, String originalUrl) {
        UrlMapping mapping = new UrlMapping();
        mapping.shortKey = key;
        mapping.originalUrl = originalUrl;
        mapping.persist(); // Η μαγεία του Panache για INSERT
    }

    @Override
    public Optional<String> findByKey(String key) {
        // Η μαγεία του Panache για SELECT ... WHERE shortKey = ?
        return UrlMapping.<UrlMapping>find("shortKey", key)
                .firstResultOptional() // Παίρνει το πρώτο (αν υπάρχει) ως Optional<UrlMapping>
                .map(mapping -> mapping.originalUrl); // Παίρνει το originalUrl αν βρέθηκε
    }

    @Override
    public Optional<String> findByOriginalUrl(String originalUrl) {
        // Η μαγεία του Panache για SELECT ... WHERE originalUrl = ?
        return UrlMapping.<UrlMapping>find("originalUrl", originalUrl)
                .firstResultOptional()
                .map(mapping -> mapping.shortKey); // Παίρνει το shortKey αν βρέθηκε
    }

    @Override
    public boolean keyExists(String key) {
        // Η μαγεία του Panache για SELECT COUNT(*) ... WHERE shortKey = ?
        return UrlMapping.count("shortKey", key) > 0;
    }
} // <-- Βεβαιώσου ότι αυτή είναι η ΤΕΛΕΥΤΑΙΑ αγκύλη του αρχείου!