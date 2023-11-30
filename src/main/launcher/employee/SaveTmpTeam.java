package main.launcher.employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import main.staff.*;
import main.launcher.employee.*;

public class SaveTmpTeam {
    // src\\main\\data\\temporaryTeam.txt

    // Sauvegarde les variables temporaires dans un fichier
    public static void saveTemporaryVariablesToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("src\\main\\data\\temporaryTeam.txt", false);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(TeamScreen.serveur1Tmp);
            objectOutputStream.writeObject(TeamScreen.serveur2Tmp);
            objectOutputStream.writeObject(TeamScreen.cuisinier1Tmp);
            objectOutputStream.writeObject(TeamScreen.cuisinier2Tmp);
            objectOutputStream.writeObject(TeamScreen.cuisinier3Tmp);
            objectOutputStream.writeObject(TeamScreen.cuisinier4Tmp);
            objectOutputStream.writeObject(TeamScreen.barmanTmp);
            objectOutputStream.writeObject(TeamScreen.managerTmp);

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des variables temporaires : " + e.getMessage());
        }
    }

    // Charge les variables temporaires depuis un fichier
    public static void loadTemporaryVariablesFromFile() throws IOException, ClassNotFoundException {
        Path chemin = new File("src\\main\\data\\temporaryTeam.txt").toPath();
        
        long tailleFichier = Files.size(chemin);

        try(FileInputStream fileInputStream = new FileInputStream("src\\main\\data\\temporaryTeam.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
        //try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\data\\temporaryTeam.txt"))) {
            // Charge chaque employé depuis une ligne du fichier et l'assigne à la variable
            // correspondante
            
            if(tailleFichier == 0) {
                TeamScreen.serveur1Tmp = new Serveur("Nom", "Prénom", 0);
                TeamScreen.serveur2Tmp = new Serveur("Nom", "Prénom", 0);
                TeamScreen.cuisinier1Tmp = new Cuisinier("Nom", "Prénom", 0);
                TeamScreen.cuisinier2Tmp = new Cuisinier("Nom", "Prénom", 0);
                TeamScreen.cuisinier3Tmp = new Cuisinier("Nom", "Prénom", 0);
                TeamScreen.cuisinier4Tmp = new Cuisinier("Nom", "Prénom", 0);
                TeamScreen.barmanTmp = new Barman("Nom", "Prénom", 0);
                TeamScreen.managerTmp = new Manager("Nom", "Prénom", 0);
            } else{
                // TeamScreen.serveur1Tmp = (Serveur) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.serveur2Tmp = (Serveur) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.cuisinier1Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.cuisinier2Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.cuisinier3Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.cuisinier4Tmp = (Cuisinier) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.barmanTmp = (Barman) createEmployeeFromDetails(reader.readLine());
                // TeamScreen.managerTmp = (Manager) createEmployeeFromDetails(reader.readLine());

                TeamScreen.serveur1Tmp = (Serveur) objectInputStream.readObject();
                TeamScreen.serveur2Tmp = (Serveur) objectInputStream.readObject();
                TeamScreen.cuisinier1Tmp = (Cuisinier) objectInputStream.readObject();
                TeamScreen.cuisinier2Tmp = (Cuisinier) objectInputStream.readObject();
                TeamScreen.cuisinier3Tmp = (Cuisinier) objectInputStream.readObject();
                TeamScreen.cuisinier4Tmp = (Cuisinier) objectInputStream.readObject();
                TeamScreen.barmanTmp = (Barman) objectInputStream.readObject();
                TeamScreen.managerTmp = (Manager) objectInputStream.readObject();
            }

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