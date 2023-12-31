package main.launcher.employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.staff.*;
import main.place.*;

/*
 * Classe qui s'occupe de la gestion de la sauvegarde des employés et des équipes
 */
public class SaveEmployee {

    // Sauvegarde la liste des employés dans le fichier lorsqu'on la met à jour
    // Par exemple lorsqu'on ajoute ou supprime un employé
    public static void saveEmployeeListToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src\\main\\data\\employeeList.txt"))) {
            for (Employé employé : Restaurant.getEmployésList()) {
                // Ecrit une ligne par employé dans le fichier sous la forme :
                // "Type d'employé, nom, prénom, salaire"
                writer.println(employé.getClass().getSimpleName() + ", " +
                        employé.getNom() + ", " +
                        employé.getPrenom() + ", " +
                        employé.getSalaire() + ", " +
                        employé.getNbJoursConsecutifs());
            }
            System.out.println("Liste d'employés sauvegardée avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la liste d'employés : " + e.getMessage());
        }
    }

    // Charge la liste des employés depuis le fichier, lors du lancement de l'application
    public static void loadEmployeeListFromTheFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\data\\employeeList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // On divise la ligne en 4 parties
                // "Type d'employé, nom, prénom, salaire"
                String[] parts = line.split(", "); // Regex d'une virgule suivie d'un espace
                String type = parts[0]; // Type d'employé
                String nom = parts[1]; // Nom
                String prenom = parts[2]; // Prénom
                double salaire = Double.parseDouble(parts[3]); // Salaire
                int nbrJoursConsecutifs = Integer.parseInt(parts[4]);

                // On ajoute ensuite l'employé, que l'on instancie en fonction de son type
                switch (type) {
                    case "Serveur":
                        Restaurant.getEmployésList().add(new Serveur(nom, prenom, salaire, nbrJoursConsecutifs));
                        break;
                    case "Cuisinier":
                        Restaurant.getEmployésList().add(new Cuisinier(nom, prenom, salaire, nbrJoursConsecutifs));
                        break;
                    case "Barman":
                        Restaurant.getEmployésList().add(new Barman(nom, prenom, salaire, nbrJoursConsecutifs));
                        break;
                    case "Manager":
                        Restaurant.getEmployésList().add(new Manager(nom, prenom, salaire, nbrJoursConsecutifs));
                        break;
                    default:
                        System.err.println("ERREUR : Type d'employé inconnu : " + type);
                }
            }
        } catch (IOException e) {
            System.err
                    .println("ERREUR : Lors du chargement de la liste d'employés depuis : " + e.getMessage());
        }
    }
}