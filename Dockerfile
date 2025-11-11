# 1. Ξεκίνα από μια βάση που έχει ΜΟΝΟ Java 21 Runtime
FROM eclipse-temurin:21-jre-jammy

# 2. Φτιάξε έναν φάκελο "app" μέσα στο κουτί
WORKDIR /app

# 3. Αντίγραψε τα αρχεία που έφτιαξες (τώρα σωστά!)
COPY build/quarkus-app/lib/ ./lib/
COPY build/quarkus-app/quarkus-run.jar .



# 4. Πες στο Docker ότι η εφαρμογή "ακούει" στην πότα 8080
EXPOSE 8080

# 5. Η εντολή που θα τρέξει όταν ξεκινήσει το κουτί
CMD ["java", "-Dquarkus.http.host=0.0.0.0", "-jar", "quarkus-run.jar"]