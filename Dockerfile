# Bruk et lettvektsbilde med både Java og Node
FROM node:18-slim

# Installer Java (OpenJDK)
RUN apt-get update && \
    apt-get install -y default-jdk && \
    apt-get clean;

# Sett arbeidskatalog inne i containeren
WORKDIR /app

# Kopier alle prosjektfiler til containeren
COPY . .

# Kompiler Java-koden under bygging
RUN javac src/*.java

# Kommandoen som kjører når containeren starter
# Vi kjører testene, så transformasjonen, så rapporten
CMD ["sh", "-c", "java -cp src RequirementTest && java -cp src ModelExtractor && node scripts/report_generator.js"]