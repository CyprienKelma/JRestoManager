package main.launcher.employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import main.staff.*;
import main.launcher.employee.*;

public class SaveTmpTeam {
    // src\\main\\data\\temporaryTeam.txt

    // Sauvegarde les variables temporaires dans un fichier
    public static void saveTemporaryVariablesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src\\main\\data\\temporaryTeam.txt"))) {
            // Sauvegarde les détails de chaque employé dans une ligne du fichier
            writer.println(TeamScreen.serveur1Tmp.toString());
            writer.println(TeamScreen.serveur2Tmp.toString());
            writer.println(TeamScreen.cuisinier1Tmp.toString());
            writer.println(TeamScreen.cuisinier2Tmp.toString());
            writer.println(TeamScreen.cuisinier3Tmp.toString());
            writer.println(TeamScreen.cuisinier4Tmp.toString());
            writer.println(TeamScreen.barmanTmp.toString());
            writer.println(TeamScreen.managerTmp.toString());

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des variables temporaires : " + e.getMessage());
        }
    }

    // Charge les variables temporaires depuis un fichier
    public static void loadTemporaryVariablesFromFile() {
        Path chemin = new File("src\\main\\data\\temporaryTeam.txt").toPath();
        int tailleFichier = File.size("src\\main\\data\\temporaryTeam.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\data\\temporaryTeam.txt"))) {
            // Charge chaque employé depuis une ligne du fichier et l'assigne à la variable
            // correspondante

            TeamScreen.serveur1Tmp = (Serveur) createEmployeeFromDetails(reader.readLine());
            TeamScreen.serveur2Tmp = (Serveur) createEmployeeFromDetails(reader.readLine());
            TeamScreen.cuisinier1Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
            TeamScreen.cuisinier2Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
            TeamScreen.cuisinier3Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
            TeamScreen.cuisinier4Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
            TeamScreen.barmanTmp = (Barman) createEmployeeFromDetails(reader.readLine());
            TeamScreen.managerTmp = (Manager) createEmployeeFromDetails(reader.readLine());

        } catch (IOException e) {
            System.err.println(
                    "Erreur lors du chargement des variables temporaires depuis le fichier : " + e.getMessage());
        }
    }


    public static Employé createEmployeeFromDetails(String employeeDetails) {
        // Sépare les détails en utilisant une virgule comme séparateur
        String[] detailsArray = employeeDetails.split(",");
    
        // Récupère les détails individuels
        String type = detailsArray[0];
        String nom = detailsArray[1];
        String prenom = detailsArray[2];
        double salaire = Double.parseDouble(detailsArray[3]);
    
        // En fonction du type, crée et retourne l'instance appropriée de la classe Employé
        switch (type) {
            case "Serveur":
                return new Serveur(nom, prenom, salaire);
            case "Cuisinier":
                return new Cuisinier(nom, prenom, salaire);
            case "Barman":
                return new Barman(nom, prenom, salaire);
            case "Manager":
                return new Manager(nom, prenom, salaire);
            // Ajoute d'autres cas si nécessaire pour d'autres types d'employés
            default:
                throw new IllegalArgumentException("Type d'employé inconnu : " + type);
        }
    }
    
}