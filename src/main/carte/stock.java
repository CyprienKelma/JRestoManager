package main.carte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    

    public static void retirerAliment(String nomFichier, Map<String, Integer> ingredients) throws IOException {
        List<aliment> listeAliments = lireFichier(nomFichier);
    
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (aliment existingAliment : listeAliments) {
                // Vérifier si l'aliment actuel est dans la liste d'ingrédients spécifiée
                if (ingredients.containsKey(existingAliment.getNom())) {
                    int quantiteARetirer = ingredients.get(existingAliment.getNom());
    
                    // Mise à jour de la quantité en vérifiant qu'elle ne devient pas négative
                    int nouvelleQuantite = Math.max(existingAliment.getQuantite() - quantiteARetirer, 0);
    
                    // Écrire dans le fichier le nouvel aliment avec la quantité mise à jour
                    writer.write(existingAliment.getNom() + "," + nouvelleQuantite);
                    writer.newLine();
                } else {
                    // Si ce n'est pas un ingrédient à retirer, réécrire dans le fichier
                    writer.write(existingAliment.getNom() + "," + existingAliment.getQuantite());
                    writer.newLine();
                }
            }
        }
    }
    
    
    


    /*public static void main(String[] args) {
        // Exemple d'utilisation
        carte carteInstance = new carte();

        // Initialisation des plats et boissons
        carteInstance.initialiserPlat();
        carteInstance.initialiserBoisson();

        // Exemple de plat à retirer
        String platARetirer = "Potage tomate";

        // Récupération des ingrédients du plat à retirer
        Map<String, Integer> ingredientsAretirer = carteInstance.getIngredients(platARetirer);

        System.out.println(ingredientsAretirer);
        try {
            // Appel de la fonction pour retirer l'aliment du stock
            stock.retirerAliment("src\\main\\data\\stock.txt", ingredientsAretirer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

