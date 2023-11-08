# Utilisation de l'image de la distribution JDK Temurin version 17 pour Ubuntu Jammy
FROM eclipse-temurin:17-jdk-jammy

# Définition du répertoire de travail de l'application dans le conteneur
WORKDIR /app

# Copie du répertoire .mvn local (contenant les configurations Maven) dans le répertoire .mvn de l'image Docker
COPY .mvn/ .mvn

# Copie des fichiers mvnw (Maven Wrapper) et pom.xml (fichier de configuration Maven) depuis le répertoire local actuel vers le répertoire racine de travail de l'image Docker
COPY mvnw pom.xml ./

# Rendre le fichier mvnw exécutable
RUN chmod +x mvnw

# Exécution de Maven pour résoudre les dépendances définies dans le fichier pom.xml sans exécuter la phase de construction
RUN ./mvnw dependency:resolve

# Définition des variables d'environnement pour Nexus URL, ID de l'artefact et sa version
ENV NEXUS_URL=http://172.17.0.2:8081/#browse/browse:maven-releases
ENV ARTIFACT_ID=DevOps_Project
ENV VERSION=2.1

# Copie du répertoire source local dans le répertoire src de l'image Docker
COPY src ./src

# Commande par défaut pour démarrer l'application Spring Boot à l'intérieur du conteneur à l'aide du Maven Wrapper
CMD ["./mvnw", "spring-boot:run"]
