package main.launcher.employee;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import main.launcher.App;
import main.place.Restaurant;
import main.staff.*;

/*
 * Classe qui gère l'écran de gestion des employés (5)
 */
public class EmployeeScreen {

    private EmployeeScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires. Elles n'ont
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Ecran principal de gestion des employés (5)
    public static void showTeamScreen(Scanner menuScanner) throws IOException {
        clearConsole(); // Nettoie la console pour une meilleure lisibilité
        print("==========================================================================\n");
        print("GESTION DES EMPLOYÉS\n");
        print("1 - Afficher la liste des employés");
        print("2 - Ajouter/Supprimer un employé");
        print("3 - Programmer les employés pour la soirée (i.e. former l'équipe)");

        print("\n4 - Retour au menu principal\n");
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                showEmployeeList(menuScanner);
                break;
            case "2":
                showAddRemoveEmployeeScreen(menuScanner);
                break;
            case "3":
                if(Restaurant.isOpen()){
                    showCannotCreateTeamScreen(menuScanner);
                }
                TeamScreen.showTeamFormationScreen(menuScanner);
                break;
            case "4":
                App.showMainMenu();
                break;
            default:
                showTeamScreen(menuScanner);
        }
    }

    public static void showCannotCreateTeamScreen(Scanner menuScanner) throws IOException{
        clearConsole();
        print("==========================================================================\n");
        print("Désolé, il est impossible de former une équipe :");
        print("Le restaurant est actuellement ouvert.");
        print("Veuillez fermer le restaurant pour former l'équipe du service suivant\n");

        print("--------------------------------------------------------------------------");
        print("1 - Revenir à la page précédente");
        print("2 - Revenir au menu principale\n\n");
        String input = menuScanner.next();

        if (input.equals("1")) {
            showTeamScreen(menuScanner);
        } else if (input.equals("2")) {
            App.showMainMenu();
        } else {
            showCannotCreateTeamScreen(menuScanner);
        }
    }

    // Sous-écran A : Ajouter/Supprimer un employé
    public static void showAddRemoveEmployeeScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER/SUPPRIMER UN EMPLOYÉ :\n");
        print("1 - Ajouter un employé");
        print("2 - Supprimer un employé");

        print("\n3 - Page précédente");
        print("4 - Retour au menu principal\n");
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                showAddEmployee(menuScanner);
                break;
            case "2":
                showRemoveEmployee(menuScanner);
                break;
            case "3":
                showTeamScreen(menuScanner);
                break;
            case "4":
                App.showMainMenu();
                break;
            default:
                showAddRemoveEmployeeScreen(menuScanner);

        }
    }

    // Sous-écran A1 : Ajouter un employé
    private static void showAddEmployee(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER UN EMPLOYÉ :\n");
        print("Quel type d'employé voulez-vous ajouter ?\n");

        print("1 - Serveur");
        print("2 - Cuisinier");
        print("3 - Barman");
        print("4 - Manager");
        print("\n5 - Page précédente");
        print("6 - Retour au menu principal\n");
        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                addNewEmployee(menuScanner, "Serveur");
                break;
            case "2":
                addNewEmployee(menuScanner, "Cuisinier");
                break;
            case "3":
                addNewEmployee(menuScanner, "Barman");
                break;
            case "4":
                addNewEmployee(menuScanner, "Manager");
                break;
            case "5":
                showAddRemoveEmployeeScreen(menuScanner);
                break;
            case "6":
                App.showMainMenu();
                break;
            default:
                showAddEmployee(menuScanner);
        }

    }

    public static void showEmployeeList(Scanner menuScanner) throws IOException {

        if (Restaurant.getEmployésList().isEmpty()) {
            clearConsole();
            print("==========================================================================\n");
            print("La liste des employés est vide.\n");
            print("1 - Revenir à la page précédente");
            print("2 - Revenir au menu principale\n\n");
            int input = menuScanner.nextInt();

            if (input == 1) {
                showTeamScreen(menuScanner);
            } else {
                App.showMainMenu();
            }
        }

        clearConsole();
        print("==========================================================================\n");
        print("Liste de tout les employés :\n");
        print("\n--------------------------------------------------------------------------");

        for (int i = 0; i < Restaurant.getEmployésList().size(); i++) {

            // L'employé d'indice i
            Employé employé = Restaurant.getEmployésList().get(i);

            // Le type d'employé (Manager, Serveur, ect...)
            String type = employé.getClass().getSimpleName();

            // On affiche chaque employé d'indice i
            print((i + 1) + ") " + type + " : " + employé.getNom() + ", " + employé.getPrenom() + ", "
                    + employé.getSalaire() + " euros/h net.");
        }
        print("--------------------------------------------------------------------------");
        print("\n1 - Revenir à la page précédente");
        print("2 - Revenir au menu principale");
        int input = menuScanner.nextInt();

        if (input == 1) {
            showTeamScreen(menuScanner);
        } else {
            App.showMainMenu();
        }
    }

    //Ajoute un nouvelle employé au restaurant
    private static void addNewEmployee(Scanner menuScanner, String typeEmployé) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("AJOUTER UN EMPLOYÉ :\n");

        print("Veuillez entrer le nom de l'employé :\n");
        String nom = menuScanner.next();

        print("\nVeuillez entrer le prénom de l'employé :\n");
        String prenom = menuScanner.next();

        print("\nVeuillez entrer le salaire de l'employé (x euros/heure net) :\n");
        // On prend une valeur de type String, pour éviter des erreurs fréquentes
        // lorsque l'on utilise un scanner pour une valeur de type double
        String tmpSalaire = menuScanner.next();

        // On convertie ensuite cette valeur tmpSalaire(String) en salaire(double) pour
        // respecter l'appel des constructeurs de Serveur, Cuisinier, etc...
        double salaire = Double.parseDouble(tmpSalaire);

        switch (typeEmployé) {

            // Dans le cas ou l'on ajoute un serveur
            case "Serveur":
                Serveur serveur = new Serveur(nom, prenom, salaire);
                Restaurant.getEmployésList().add(serveur);

                // Sauvegarde la modification dans le fichier (data/employeeList.txt)
                SaveEmployee.saveEmployeeListToFile();

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;

            // Dans le cas ou l'on ajoute un cuisinier
            case "Cuisinier":
                Cuisinier cuisinier = new Cuisinier(nom, prenom, salaire);
                Restaurant.getEmployésList().add(cuisinier);

                // Sauvegarde la modification dans le fichier (data/employeeList.txt)
                SaveEmployee.saveEmployeeListToFile();

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;

            // Pour un barman
            case "Barman":
                Barman barman = new Barman(nom, prenom, salaire);
                Restaurant.getEmployésList().add(barman);

                // Sauvegarde la modification dans le fichier (data/employeeList.txt)
                SaveEmployee.saveEmployeeListToFile();

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;

            // Et enfin pour un manager
            case "Manager":
                Manager manager = new Manager(nom, prenom, salaire);
                Restaurant.getEmployésList().add(manager);

                // Sauvegarde la modification dans le fichier (data/employeeList.txt)
                SaveEmployee.saveEmployeeListToFile();

                showConfirmationEmployeeChange(menuScanner, typeEmployé, true);
                break;
            default:
                throw new IllegalArgumentException("Classe TeamScreen - Type d'employé inatendu : " + typeEmployé);
        }
    }

    // Sous-écran A2 : Supprimer un employé
    private static void showRemoveEmployee(Scanner menuScanner) throws IOException {

        List<Employé> employésList = Restaurant.getEmployésList();

        if (employésList.isEmpty()) {
            clearConsole();
            print("==========================================================================\n");
            print("Désolé, impossible de supprimer un employé.");
            print("La liste des employés est vide.\n");
            print("1 - Revenir à la page précédente\n");
            int input = menuScanner.nextInt();

            if (input == 1) {
                showAddRemoveEmployeeScreen(menuScanner);
            } else {
                showRemoveEmployee(menuScanner);
            }
        }

        clearConsole();
        print("==========================================================================\n");
        print("SUPPRIMER UN EMPLOYÉ :\n");
        print("Veuillez entrer le numéro de l'employé que vous souhaitez supprimer : \n\n");

        for (int i = 0; i < employésList.size(); i++) {

            // L'employé d'indice i
            Employé employé = employésList.get(i);

            // Le type d'employé (Manager, Serveur, ect...)
            String type = employé.getClass().getSimpleName();

            // On affiche chaque employé d'indice i
            print((i + 1) + ") " + employé.getNom() + ", " + employé.getPrenom() + ", "
                    + employé.getSalaire() + " euros/h net.");
        }

        print("\n--------------------------------------------------------------------------");
        print("Employé numéro :\n");
        int input = menuScanner.nextInt();

        // SelectedOne désigne l'employé d'indice i (i = input - 1)
        Employé selectedOne = employésList.get(input - 1);
        String selectedOneType = selectedOne.getClass().getSimpleName();

        // On retire l'employé d'indice i = input - 1 (puisque l'utilisateur vois la
        // liste des i + 1)
        employésList.remove(selectedOne);

        // Sauvegarde la modification dans le fichier (data/employeeList.txt)
        SaveEmployee.saveEmployeeListToFile();

        // Enfin on affiche un message de confirmation pour confirmer que
        showConfirmationEmployeeChange(menuScanner, selectedOneType, false);
    }

    // Affiche un message de confirmation, suivit d'un menu pour choisir la suite
    // Précise en paramètre si l'on ajoute un employé (isAdd = true) ou le supprime
    // (isAdd = false). En fonction des cas les menus seront différents
    public static void showConfirmationEmployeeChange(Scanner menuScanner, String typeEmployé, boolean isAdd) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        if (isAdd) {
            print("Le " + typeEmployé + " a bien été ajouté à la liste des employés !\n");
        } else {
            print("Le " + typeEmployé + " a bien été supprimé de la liste des employés !\n");
        }
        print("Que voulez vous faire maintenant :\n");
        if (isAdd) {
            print("1 - Ajouter un autre employé");
        } else {
            print("1 - Supprimer un autre employé");
        }
        print("2 - Revenir à la page précédente");
        print("3 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        if (choixEcran == 1 && isAdd)
            showAddEmployee(menuScanner);
        else if (choixEcran == 1 && !isAdd)
            showRemoveEmployee(menuScanner);
        else if (choixEcran == 2)
            showAddRemoveEmployeeScreen(menuScanner);
        else if (choixEcran == 3)
            App.showMainMenu();
    }

    // utiliser pour afficher un texte plus rapidement
    public static void print(String text) {
        System.out.println(text);
    }

    // Fonction qui permet de nettoyer la console pour une meilleure lisibilité
    public static void clearConsole() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}