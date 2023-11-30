package main.carte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Stock {

    public static List<Aliment> lireFichier(String nomFichier) throws IOException {
        Path fichier = Path.of(nomFichier);
        Charset charset = Charset.forName("windows-1252");
        List<Aliment> listeAliments = new ArrayList<>();

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
                            Aliment aliment = new Aliment(nom, quantite);
                            listeAliments.add(aliment);
                        }
                    }
                }
            }
        }

        return listeAliments;
    }

    public static void ajouterAliment(String nomFichier, Aliment aliment) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            // Écrivez les détails de l'aliment dans le fichier
            // Format : nom,quantite
            writer.write(aliment.getNom() + "," + aliment.getQuantite());
        }
    }

    public static void retirerAliment(String nomFichier, String nomAliment) throws IOException {
        List<Aliment> listeAliments = lireFichier(nomFichier);

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomFichier), Charset.forName("windows-1252"))) {
            for (Aliment aliment : listeAliments) {
                if (!aliment.getNom().equals(nomAliment)) {
                    // Réécrire l'aliment dans le fichier sauf celui correspondant à l'aliment à
                    // retirer
                    writer.write(aliment.getNom() + "," + aliment.getQuantite());
                    writer.newLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        try {
            List<Aliment> aliments = lireFichier("votre_fichier.txt");
            for (Aliment aliment : aliments) {
                System.out.println(aliment.getNom() + " - " + aliment.getQuantite());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
