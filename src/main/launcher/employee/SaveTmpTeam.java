// package main.launcher.employee;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.*;

// import main.staff.*;

// public class SaveTmpTeam {
//     // src\\main\\data\\temporaryTeam.txt

//     // Sauvegarde les variables temporaires dans un fichier
//     public static void saveTemporaryVariablesToFile() {
//         try (PrintWriter writer = new PrintWriter(new FileWriter("temporaryVariables.txt"))) {
//             // Sauvegarde les détails de chaque employé dans une ligne du fichier
//             writer.println(TeamScreen.serveur1Tmp.toString());
//             writer.println(TeamScreen.serveur2Tmp.toString());
//             writer.println(TeamScreen.cuisinier1Tmp.toString());
//             writer.println(TeamScreen.cuisinier2Tmp.toString());
//             writer.println(TeamScreen.cuisinier3Tmp.toString());
//             writer.println(TeamScreen.cuisinier4Tmp.toString());
//             writer.println(TeamScreen.barmanTmp.toString());
//             writer.println(TeamScreen.managerTmp.toString());

//             System.out.println("Variables temporaires sauvegardées avec succès.");
//         } catch (IOException e) {
//             System.err.println("Erreur lors de la sauvegarde des variables temporaires : " + e.getMessage());
//         }
//     }

//     // Charge les variables temporaires depuis un fichier
//     public static void loadTemporaryVariablesFromFile() {
//         try (BufferedReader reader = new BufferedReader(new FileReader("temporaryVariables.txt"))) {
//             // Charge chaque employé depuis une ligne du fichier et l'assigne à la variable
//             // correspondante
//             serveur1 = createEmployeeFromDetails(reader.readLine());
//             serveur2 = createEmployeeFromDetails(reader.readLine());
//             cuisinier1 = createEmployeeFromDetails(reader.readLine());
//             cuisinier2 = createEmployeeFromDetails(reader.readLine());
//             cuisinier3 = createEmployeeFromDetails(reader.readLine());
//             cuisinier4 = createEmployeeFromDetails(reader.readLine());
//             barman = createEmployeeFromDetails(reader.readLine());
//             manager = createEmployeeFromDetails(reader.readLine());

//             System.out.println("Variables temporaires chargées avec succès depuis le fichier.");
//         } catch (IOException e) {
//             System.err.println(
//                     "Erreur lors du chargement des variables temporaires depuis le fichier : " + e.getMessage());
//         }
//     }
// }