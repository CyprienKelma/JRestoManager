package main.carte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class stock {

    public static List<aliment> lireFichier(String nomFichier) throws IOException {
        Path fichier = Path.of(nomFichier);
        Charset charset = Charset.forName("windows-1252");
        List<aliment> listeAliments = new ArrayList<>();

        if (Files.exists(fichier)) {
            boolean estAccessible = Files.isRegularFile(fichier)
                    && Files.isReadable(fichier) && Files.isExecutable(fichier);

            if (estAccessible) {
                try (BufferedReader reader = Files.newBufferedReader(fichier, charset)) {
                    String ligne;
                    while ((ligne = reader.readLine()) != null) {
                        // Supposons que chaque ligne du fichier a le format "nom,quantite"
                        String[] elements = ligne.split(",");
                        if (elements.length == 2) {
                            String nom = elements[0].trim();
                            int quantite = Integer.parseInt(elements[1].trim());
                            aliment aliment = new aliment(nom, quantite);
                            listeAliments.add(aliment);
                        }
                    }
                }
            }
        }

        return listeAliments;
    }

    public static void ajouterAliment(String nomFichier, aliment aliment) throws IOException {
        List<aliment> listeAliments = lireFichier(nomFichier);
    
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (aliment existingAliment : listeAliments) {
                if (existingAliment.getNom().equals(aliment.getNom())) {
                    // Additionner la quantité existante avec la quantité de l'aliment à ajouter
                    int nouvelleQuantite = existingAliment.getQuantite() + aliment.getQuantite();
    
                    // Écrire l'aliment mis à jour avec la nouvelle quantité dans le fichier
                    writer.write(existingAliment.getNom() + "," + nouvelleQuantite);
                    writer.newLine();
                } else {
                    // Réécrire les autres aliments tels quels dans le fichier
                    writer.write(existingAliment.getNom() + "," + existingAliment.getQuantite());
                    writer.newLine();
                }
            }
    
            // Si l'aliment à ajouter n'était pas présent dans la liste existante, l'ajouter à la fin du fichier
            if (!listeAliments.stream().anyMatch(a -> a.getNom().equals(aliment.getNom()))) {
                writer.write(aliment.getNom() + "," + aliment.getQuantite());
                writer.newLine();
            }
        }
    }
    

    public static void retirerAliment(String nomFichier, aliment aliment) throws IOException {
        List<aliment> listeAliments = lireFichier(nomFichier);
    
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (aliment existingAliment : listeAliments) {
                if (existingAliment.getNom().equals(aliment.getNom())) {
                    int nouvelleQuantite;
                    // Additionner la quantité existante avec la quantité de l'aliment à ajouter
                    if(existingAliment.getQuantite()>aliment.getQuantite()){
                        nouvelleQuantite = existingAliment.getQuantite() - aliment.getQuantite();
                    }
                    else nouvelleQuantite = 0;
                    // Écrire l'aliment mis à jour avec la nouvelle quantité dans le fichier
                    writer.write(existingAliment.getNom() + "," + nouvelleQuantite);
                    writer.newLine();
                } else {
                    // Réécrire les autres aliments tels quels dans le fichier
                    writer.write(existingAliment.getNom() + "," + existingAliment.getQuantite());
                    writer.newLine();
                }
            }
    
            // Si l'aliment à ajouter n'était pas présent dans la liste existante, l'ajouter à la fin du fichier
            if (!listeAliments.stream().anyMatch(a -> a.getNom().equals(aliment.getNom()))) {
                writer.write(aliment.getNom() + "," + 0);
                writer.newLine();
            }
        }
    }

    
    
    
    
    
    


    /*public static void main(String[] args) {
        // Exemple d'utilisation
        try {
            aliment tomate = new aliment("tomate", 4);
            ajouterAliment("src\\main\\data\\stock.txt", tomate);
            aliment salade = new aliment("salade", 1);
            retirerAliment("src\\main\\data\\stock.txt", salade);

            List<aliment> aliments = lireFichier("src\\main\\data\\stock.txt");
            for (aliment aliment : aliments) {
                System.out.println(aliment.getNom() + " - " + aliment.getQuantite());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
