package main.carte;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StockDrink {


    public static List<Boisson> lireFichier(String nomFichier) throws IOException {
        Path fichier = Path.of(nomFichier);
        Charset charset = Charset.forName("windows-1252");
        List<Boisson> listeBoissons = new ArrayList<>();

        if (Files.exists(fichier)) {
            boolean estAccessible = Files.isRegularFile(fichier)
                    && Files.isReadable(fichier) && Files.isExecutable(fichier);

            if (estAccessible) {
                try (BufferedReader reader = Files.newBufferedReader(fichier, charset)) {
                    String ligne;
                    while ((ligne = reader.readLine()) != null) {
                        // Supposons que chaque ligne du fichier a le format "boisson,quantite"
                        String[] elements = ligne.split(",");
                        if (elements.length == 2) {
                            String nom = elements[0].trim();
                            int quantite = Integer.parseInt(elements[1].trim());
                            Boisson boisson = new Boisson(nom, quantite);
                            listeBoissons.add(boisson);
                        }
                    }
                }
            }
        }

        return listeBoissons;
    }

    public static void ajouterBoisson(String nomFichier, Boisson boisson) throws IOException {
        List<Boisson> listeBoissons = lireFichier(nomFichier);
    
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (Boisson existingBoisson : listeBoissons) {
                if (existingBoisson.getNom().equals(boisson.getNom())) {

                    // Si la boisson à ajouter est déjà présente dans la liste existante, on
                    // additionne la quantité existante avec la quantité de la boisson à ajouter
                    int nouvelleQuantite = existingBoisson.getQuantite() + boisson.getQuantite();
    
                    // Puis on écrit la boisson mise à jour avec la nouvelle quantité dans le fichier
                    writer.write(existingBoisson.getNom() + "," + nouvelleQuantite);
                    writer.newLine();
                } else {
                    
                    // Sinon, on réécrit les autres boissons tels quels dans le fichier
                    writer.write(existingBoisson.getNom() + "," + existingBoisson.getQuantite());
                    writer.newLine();
                }
            }
    
            // Si la boisson à ajouter n'était pas présente dans la liste existante, on l'ajoute à la fin du fichier
            Optional<Boisson> existingBoisson = listeBoissons.stream().filter(b -> b.getNom().equals(boisson.getNom())).findFirst();
            if (!existingBoisson.isPresent()) {
                writer.write(boisson.getNom() + "," + boisson.getQuantite());
                writer.newLine();
            }
        }
    }

    public static void retirerBoisson(String nomFichier, Map<String, Integer> boissonsQuantites) throws IOException {
        List<Boisson> listeBoissons = lireFichier(nomFichier);
    
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (Boisson existingBoisson : listeBoissons) {
                String nomBoisson = existingBoisson.getNom();
    
                if (boissonsQuantites.containsKey(nomBoisson)) {
                    // Si la boisson à supprimer est présente dans la liste existante, on
                    // soustrait la quantité existante avec la quantité de la boisson à supprimer
                    int nouvelleQuantite = existingBoisson.getQuantite() - boissonsQuantites.get(nomBoisson);
    
                    // Puis on écrit la boisson mise à jour avec la nouvelle quantité dans le fichier
                    writer.write(nomBoisson + "," + nouvelleQuantite);
                    writer.newLine();
                } else {
                    // Sinon, on réécrit les autres boissons tels quels dans le fichier
                    writer.write(nomBoisson + "," + existingBoisson.getQuantite());
                    writer.newLine();
                }
            }
        }
    }
    

    public static void retournerBoissons(String nomFichier, Map<String, Integer> quantitesRetirees) throws IOException {
        // Lire le stock actuel
        List<Boisson> stockActuel = lireFichier(nomFichier);
    
        // Créer une nouvelle liste pour stocker les aliments mis à jour
        List<Boisson> stockMisAJour = new ArrayList<>();
    
        // Restaurer les quantités retirées
        for (Boisson boisson : stockActuel) {
            String nomBoisson = boisson.getNom();
            int quantiteRetiree = quantitesRetirees.getOrDefault(nomBoisson, 0);
    
            // Créer une nouvelle instance d'aliment avec la quantité mise à jour
            int nouvelleQuantite = boisson.getQuantite() + quantiteRetiree;
            Boisson alimentMisAJour = new Boisson(nomBoisson, nouvelleQuantite);
    
            // Ajouter l'aliment mis à jour à la nouvelle liste
            stockMisAJour.add(alimentMisAJour);
        }
    
        // Écrire le stock mis à jour dans le fichier
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (Boisson boisson : stockMisAJour) {
                // Écrire dans le fichier chaque aliment avec sa quantité
                writer.write(boisson.getNom() + "," + boisson.getQuantite());
                writer.newLine();
            }
        }
    }
}
