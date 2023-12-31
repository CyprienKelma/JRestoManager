package main.launcher.employee;

import java.io.IOException;
import java.util.*;

import main.launcher.App;
import main.place.Restaurant;
import main.staff.*;

public class TeamScreen {

    // Le but est selectionner des employés de chaque type pour former l'équipe
    // On va donc les récupérer dans la liste de tout les employés (=>
    // Restaurant.getEmployésList())
    // et les ajouter dans des variables temporaires.
    // Une fois que l'équipe est formée, on va créer une instance de la classe
    // Equipe avec ces variables

    // On a donc :
    protected static Serveur serveur1Tmp = null;
    protected static Serveur serveur2Tmp = null;
    protected static Cuisinier cuisinier1Tmp = null;
    protected static Cuisinier cuisinier2Tmp = null;
    protected static Cuisinier cuisinier3Tmp = null;
    protected static Cuisinier cuisinier4Tmp = null;
    protected static Barman barmanTmp = null;
    protected static Manager managerTmp = null;

    /*
     * Note importante :
     * On aurait pu utiliser ici une HashMap pour stocker les employés temporaires.
     * Par exemple :
     * private static Map<String, Employé> employeeTmpHashMap = new HashMap<>();
     * Mais puisque le nombre d'employés est fixe, il est plus simple d'utiliser des
     * variables séparées pour chaque employé temporaire, afin d'éviter les doublons
     */

    private TeamScreen() {
        // Constructeur privé pour empêcher l'instanciation des classes "Screen"
        // Les classes portant l'inscription "Screen" sont des utilitaires. Elles n'ont
        // que des méthodes statiques d'affichage, elles ne doivent pas être instanciées
        throw new IllegalStateException("Classe Screen : utilitaire de méthodes statiques");
    }

    // Ecran : Programmer les employés pour la soirée (i.e. former l'équipe)
    public static void showTeamFormationScreen(Scanner menuScanner) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("FORMER L'EQUIPE DE CE SOIR :\n");

        print("Avant de pouvoir ouvrir le restaurant, vous devez former l'équipe de ce soir.\n");
        print("Pour former cette équipe, vous devez choisir les employés qui travailleront");
        print("durant tout le service.\n");

        print("1 - Choisir les serveurs");
        print("2 - Choisir les cuisiniers");
        print("3 - Choisir le barman");
        print("4 - Choisir le manager\n");
        print("5 - Confirmer la selection et créer l'équipe");
        print("\n6 - Page précédente");
        print("7 - Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                showSelectEmployeesScreen(menuScanner, Serveur.class, 2);
                break;
            case "2":
                showSelectEmployeesScreen(menuScanner, Cuisinier.class, 4);
                break;
            case "3":
                showSelectEmployeesScreen(menuScanner, Barman.class, 1);
                break;
            case "4":
                showSelectEmployeesScreen(menuScanner, Manager.class, 1);
                break;
            case "5":
                confirmFinalNewTeam(menuScanner);
                break;
            case "6":
                EmployeeScreen.showTeamScreen(menuScanner);
                break;
            case "7":
                App.showMainMenu();
                break;
            default:
                showTeamFormationScreen(menuScanner);
        }
    }

    // Ecran pour choisir les employés pour former l'équipe parmis ceux requis
    // On utilise un type générique pour pouvoir utiliser cette fonction pour tous
    // les types d'employés
    public static <T> void showSelectEmployeesScreen(Scanner menuScanner, Class<T> type, int numberOfEmployees)
            throws IOException {
        clearConsole();
        print("==========================================================================\n");
        if (numberOfEmployees == 1) { // Pour le barman et manager
            print("CHOISIR LE " + type.getSimpleName().toUpperCase() + " :\n");
            print("Vous devez choisir le " + type.getSimpleName().toLowerCase()
                    + " qui travaillera durant tout ce service.\n");
        } else { // Pour les serveurs et cuisiniers
            print("CHOISIR LES " + type.getSimpleName().toUpperCase() + "S :\n");
            print("Vous devez choisir " + numberOfEmployees + " " + type.getSimpleName().toLowerCase()
                    + "s qui travailleront durant tout ce service.\n");
        }

        print("RAPPEL : Conformément à la réglementation en vigueur, les employés affichés");
        print("sont ceux n'ayant pas déja travaillés 2 jours de suite.\n");

        /*
         * Selon le type d'employé (i.e. le paramètre 'type'), on va indiquer ceux
         * manquant et ceux déjà sélectionnés
         * - A SELECTIONNER : Lorsque l'employé n'est pas encore sélectionné
         * - NOM PRENOM SALAIRE : Lorsque l'employé est déjà sélectionné
         * Cliquer sur un employé déjà sélectionné permet de modifier son choix
         */

        if (Serveur.class.equals(type)) {
            if (serveur1Tmp != null) {
                print("1 - " + type.getSimpleName() + " 1 : " + serveur1Tmp.getPrenom() + " "
                        + serveur1Tmp.getNom());
            } else {
                print("1 - " + type.getSimpleName() + " 1 : A SELECTIONNER");
            }
            if (serveur2Tmp != null) {
                print("2 - " + type.getSimpleName() + " 2 : " + serveur2Tmp.getPrenom() + " "
                        + serveur2Tmp.getNom());
            } else {
                print("2 - " + type.getSimpleName() + " 2 : A SELECTIONNER");
            }
        } else if (Cuisinier.class.equals(type)) {
            if (cuisinier1Tmp != null) {
                print("1 - " + type.getSimpleName() + " 1 : " + cuisinier1Tmp.getPrenom() + " "
                        + cuisinier1Tmp.getNom());
            } else {
                print("1 - " + type.getSimpleName() + " 1 : A SELECTIONNER");
            }
            if (cuisinier2Tmp != null) {
                print("2 - " + type.getSimpleName() + " 2 : " + cuisinier2Tmp.getPrenom() + " "
                        + cuisinier2Tmp.getNom());
            } else {
                print("2 - " + type.getSimpleName() + " 2 : A SELECTIONNER");
            }
            if (cuisinier3Tmp != null) {
                print("3 - " + type.getSimpleName() + " 3 : " + cuisinier3Tmp.getPrenom() + " "
                        + cuisinier3Tmp.getNom());
            } else {
                print("3 - " + type.getSimpleName() + " 3 : A SELECTIONNER");
            }
            if (cuisinier4Tmp != null) {
                print("4 - " + type.getSimpleName() + " 4 : " + cuisinier4Tmp.getPrenom() + " "
                        + cuisinier4Tmp.getNom());
            } else {
                print("4 - " + type.getSimpleName() + " 4 : A SELECTIONNER");
            }
        } else if (Barman.class.equals(type)) {
            if (barmanTmp != null) {
                print("1 - " + type.getSimpleName() + " : " + barmanTmp.getPrenom() + " "
                        + barmanTmp.getNom());
            } else {
                print("1 - " + type.getSimpleName() + " : A SELECTIONNER");
            }
        } else if (Manager.class.equals(type)) {
            if (managerTmp != null) {
                print("1 - " + type.getSimpleName() + " : " + managerTmp.getPrenom() + " "
                        + managerTmp.getNom());
            } else {
                print("1 - " + type.getSimpleName() + " : A SELECTIONNER");
            }
        } else {
            // Renvoie une erreur :
            throw new IllegalStateException("Erreur : type d'employé inconnu dans TeamScreen.java");
        }

        print("\n--------------------------------------------------------------------------");
        print("\n" + (numberOfEmployees + 1) + " - Page précédente");
        print((numberOfEmployees + 2) + " - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        // Permet de choisir en fonction du nombre d'employé requis (ex : 4 cuisiniers)
        if (choixEcran >= 1 && choixEcran <= numberOfEmployees) {
            showEmployeeListByType(menuScanner, type, choixEcran);

        } else if (choixEcran == numberOfEmployees + 1) {

            showTeamFormationScreen(menuScanner); // Au dela de 1, on reviens à la page d'avant
        } else if (choixEcran == numberOfEmployees + 2) {

            App.showMainMenu(); // Au dela de 2, on reviens au menu principale
        } else {
            showSelectEmployeesScreen(menuScanner, type, numberOfEmployees);
        }
    }

    // Cette fonction affiche la liste des employés du type voulu. Comme ça,
    // on peut choisir lequel on ajoute parmis ceux du bon type
    public static <T> void showEmployeeListByType(Scanner menuScanner, Class<T> type, int whichOne) throws IOException {

        // On va créer une liste temporaire pour stocker les employés du type voulu
        List<Employé> tmpList = new ArrayList<>();

        clearConsole();
        print("==========================================================================\n");
        if (whichOne == 1) {
            print("Selectionner un premier " + type.getSimpleName() + "à ajouter parmis la liste :");
        } else {
            print("Selectionner un " + whichOne + "ème " + type.getSimpleName().toLowerCase() + " à ajouter parmis la liste :");
        }

        // Puisque la liste va être filtrée (on ne veut que les serveurs par exemple)
        // On va utiliser une variable j pour afficher les numéros des employés
        int j = 0;

        // if(Restaurant.getEmployésList().contains())
        // On affiche la liste des employés du type qui nous concerne
        // Par exemple n'afficher que les serveurs quand on selectionne nos 2 serveurs
        for (int i = 0; i < Restaurant.getEmployésList().size(); i++) {

            Employé employé = Restaurant.getEmployésList().get(i);

            // Vérifier que l'employé n'est pas déja dans l'équipe :
            if(tmpList.contains(employé) || isAlreadyChoosenFromFile(employé)){
                continue;
            }

            // On affiche que les employés du type qui nous concerne (i.e. du paramètre 'type')
            if (employé.getClass().equals(type)) {
            
                print((j + 1) + ") " + type.getSimpleName() + " : " + employé.getNom() + ", " + employé.getPrenom()
                        + ", "
                        + employé.getSalaire() + " euros/h net.");

                j++; // On incrémente j seulement si on affiche l'employé
                // i.e. s'il est du type qui nous concerne ET qu'il n'est pas déjà sélectionné

                tmpList.add(employé);

            }
        }

        print("\n--------------------------------------------------------------------------");
        print("Employé numéro :\n");
        int input = menuScanner.nextInt();

        // SelectedOne désigne l'employé d'indice j (où j = input - 1)
        Employé selectedOne = tmpList.get(input - 1);

        // On ajoute l'employé sélectionné dans l'équipe
        // Pour cela on l'ajoute dans sa variable temporaires
        if (Serveur.class.equals(type)) {
            if (whichOne == 1) {
                serveur1Tmp = (Serveur) selectedOne;
                notifyAddTeam(menuScanner, serveur1Tmp, 1, Serveur.class);
            } else {
                serveur2Tmp = (Serveur) selectedOne;
                notifyAddTeam(menuScanner, serveur2Tmp, 2, Serveur.class);
            }
        } else if (Cuisinier.class.equals(type)) {
            if (whichOne == 1) {
                cuisinier1Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier1Tmp, 1, Cuisinier.class);
            } else if (whichOne == 2) {
                cuisinier2Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier2Tmp, 2, Cuisinier.class);
            } else if (whichOne == 3) {
                cuisinier3Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier3Tmp, 3, Cuisinier.class);
            } else {
                cuisinier4Tmp = (Cuisinier) selectedOne;
                notifyAddTeam(menuScanner, cuisinier4Tmp, 4, Cuisinier.class);
            }
        } else if (Barman.class.equals(type)) {
            barmanTmp = (Barman) selectedOne;
            notifyAddTeam(menuScanner, barmanTmp, 1, Barman.class);
        } else if (Manager.class.equals(type)) {
            managerTmp = (Manager) selectedOne;
            notifyAddTeam(menuScanner, managerTmp, 1, Manager.class);
        } else {
            // Renvoie une erreur :
            throw new IllegalStateException("Erreur : type d'employé inconnu dans TeamScreen.java");
        }
    }

    // Fonction qui vérifie si l'employé est déjà dans l'équipe
    // => Pour éviter d'ajouter 2 fois le même employé
    public static boolean isAlreadyChoosenFromFile(Employé employé) {
        if (serveur1Tmp != null && serveur1Tmp.equals(employé) || 
            serveur2Tmp != null && serveur2Tmp.equals(employé) || 
            cuisinier1Tmp != null && cuisinier1Tmp.equals(employé) ||
            cuisinier2Tmp != null && cuisinier2Tmp.equals(employé) || 
            cuisinier3Tmp != null && cuisinier3Tmp.equals(employé) || 
            cuisinier4Tmp != null && cuisinier4Tmp.equals(employé) ||
            barmanTmp != null && barmanTmp.equals(employé) || 
            managerTmp != null && managerTmp.equals(employé)) {
            return true;
        } else {
            return false;
        }
    }

    public static <T extends Employé> void notifyAddTeam(Scanner menuScanner, Employé selectedOne, int whichOne,
            Class<T> type) throws IOException {

        clearConsole();
        print("==========================================================================\n");
        print(selectedOne.getPrenom() + " " + selectedOne.getNom() + " a été ajouté à l'équipe !\n");
        if (Barman.class.equals(type) || Manager.class.equals(type))
            print("Une fois l'équipe confirmé, il sera le " + type.getSimpleName().toLowerCase()
                    + " du service de se soir.");
        else
            print("Une fois l'équipe confirmé, il sera le " + type.getSimpleName().toLowerCase() + " N°" + whichOne
                    + " du service de ce soir\n\n\n");

        print("\n--------------------------------------------------------------------------");
        print("1 - Page précédente");
        print("2 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        // Pour rappeller la fonction précédente (showSelectEmployeesScreen)
        // On doit redonner en paramètre le nombre d'employé (qui dépend du type)
        int numberOfEmployees = 0;
        if (Serveur.class.equals(type))
            numberOfEmployees = 2;
        else if (Cuisinier.class.equals(type))
            numberOfEmployees = 4;
        else
            numberOfEmployees = 1;
        if (choixEcran == 1) {
            showSelectEmployeesScreen(menuScanner, type, numberOfEmployees);
        } else if (choixEcran == 2) {
            App.showMainMenu();
        } else {
            notifyAddTeam(menuScanner, selectedOne, whichOne, type);
        }

    }

    public static void confirmFinalNewTeam(Scanner menuScanner) throws IOException {

        // On vérifie si l'quipe est incomplète
        if (serveur1Tmp == null || serveur2Tmp == null) {
            missingTeamMembers(menuScanner, "Serveur");
        } else if (cuisinier1Tmp == null || cuisinier2Tmp == null || cuisinier3Tmp == null
                || cuisinier4Tmp == null ) {
            missingTeamMembers(menuScanner, "Cuisinier");
        } else if (barmanTmp == null) {
            missingTeamMembers(menuScanner, "Barman");
        } else if (managerTmp == null) {
            missingTeamMembers(menuScanner, "Manager");
        }

        // Ou alors s'il y a des membres en double
        twoSameTeamate(menuScanner);

        clearConsole();
        print("==========================================================================\n");
        print("CONFIRMER VOTRE ÉQUIPE :\n");

        print("Etes-vous sûr de vouloir confirmer la création d'équipe ?");
        print("Il ne sera plus possible de la modifier jusqu'au prochain service.\n");

        print("1 - Confirmer la selection et finaliser l'équipe\n");
        print("2 - Page précédente");
        print("3 - Retour au menu principal\n");

        String choixEcran = menuScanner.next();

        switch (choixEcran) {
            case "1":
                instanciateTeam();
                break;
            case "2":
                showTeamFormationScreen(menuScanner);
                break;
            case "3":
                App.showMainMenu();
                break;
            default:
                confirmFinalNewTeam(menuScanner);
        }
    }

    public static void missingTeamMembers(Scanner menuScanner, String missingMember) throws IOException {
        clearConsole();
        print("==========================================================================\n");
        print("ERREUR : L'équipe n'est pas complète !");
        print("Vous devez d'abord choisir tous les employés présent durant le service.\n");
        print("Il manque encore au moins un " + missingMember.toLowerCase() + " dans l'équipe.\n\n");

        print("\n--------------------------------------------------------------------------");
        print("1 - Page précédente");
        print("2 - Retour au menu principal\n");

        int choixEcran = menuScanner.nextInt();

        if (choixEcran == 1) {
            showTeamFormationScreen(menuScanner);
        } else if (choixEcran == 2) {
            App.showMainMenu();
        } else {
            missingTeamMembers(menuScanner, missingMember);
        }
    }

    // Fonction qui vérifie si l'équipe contient des doublons
    // prendra en paramètre les employés de l'équipe (tmp)
    public static boolean hasDuplicates(Employé... employés) {
        // n désigne le nombre d'employés dans l'équipe
        int n = employés.length;
        
        // On parcourt tous les employés de l'équipe
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                String employé1Nom = employés[i].getNom();
                String employé2Nom = employés[j].getNom();

                String employé1Prenom = employés[i].getPrenom();
                String employé2Prenom = employés[j].getPrenom();
                
                // On vérifie si les deux employés sont les mêmes
                if (employés[i] != null && employés[j] != null && employé1Nom.equals(employé2Nom) && employé1Prenom.equals(employé2Prenom)) {
                    return true;  // Si les employés sont identiques, alors il y a un doublon
                }
            }
        }
        
        return false;  // Si vraiment aucun doublon trouvé
    }

    // Utilisation de la méthode dans votre fonction existante
    public static void twoSameTeamate(Scanner menuScanner) throws IOException {
        // On vérifie si l'équipe contient des doublons
        if (hasDuplicates(serveur1Tmp, serveur2Tmp, cuisinier1Tmp, cuisinier2Tmp, cuisinier3Tmp,
                cuisinier4Tmp, barmanTmp, managerTmp)) {

            clearConsole();
            print("==========================================================================\n");
            print("ERREUR : L'équipe n'est pas valide !");
            print("Vous ne pouvez pas ajouter 2 fois le même employé dans l'équipe.\n");
            print("Veuillez vérifier votre sélection pour voir où vous avez choisi le même en double.\n\n");

            print("\n--------------------------------------------------------------------------");
            print("1 - Page précédente");
            print("2 - Retour au menu principal\n");

            int choixEcran = menuScanner.nextInt();

            if (choixEcran == 1) {
                showTeamFormationScreen(menuScanner);
            } else if (choixEcran == 2) {
                App.showMainMenu();
            } else {
                confirmFinalNewTeam(menuScanner);
            }
        }
    }


    public static void instanciateTeam() throws IOException {
        // On instancie l'équipe avec les employés temporaires
        Equipe newTeam = new Equipe(serveur1Tmp, serveur2Tmp, cuisinier1Tmp, cuisinier2Tmp, cuisinier3Tmp,
                cuisinier4Tmp,
                barmanTmp, managerTmp);

        // On définit alors l'équipe actuelle du restaurant
        Restaurant.setEquipeActuelle(newTeam);
        Restaurant.incrementeNbJourConsecutifs(newTeam);

        serveur1Tmp.setNbJoursConsecutifs(serveur1Tmp.getNbJoursConsecutifs() + 1);
        serveur2Tmp.setNbJoursConsecutifs(serveur2Tmp.getNbJoursConsecutifs() + 1);
        cuisinier1Tmp.setNbJoursConsecutifs(cuisinier1Tmp.getNbJoursConsecutifs() + 1);
        cuisinier2Tmp.setNbJoursConsecutifs(cuisinier2Tmp.getNbJoursConsecutifs() + 1);
        cuisinier3Tmp.setNbJoursConsecutifs(cuisinier3Tmp.getNbJoursConsecutifs() + 1);
        cuisinier4Tmp.setNbJoursConsecutifs(cuisinier4Tmp.getNbJoursConsecutifs() + 1);
        barmanTmp.setNbJoursConsecutifs(barmanTmp.getNbJoursConsecutifs() + 1);
        managerTmp.setNbJoursConsecutifs(managerTmp.getNbJoursConsecutifs() + 1);

        SaveTmpTeam.saveTemporaryVariablesToFile();

        // On affiche un message de confirmation
        clearConsole();
        print("==========================================================================\n");
        print("L'équipe a été créée avec succès !");
        print("Vous pouvez maintenant ouvrir le restaurant.");
        print("Pour cela, rendez-vous dans l'écran Monitoring du manager.\n\n");

        // TEST - Pas présent au rendu final :

        print("Les membres de l'équipe sont :");
        print("--------------------------------------------------------------------------");
        print("Serveur 1 : " + Restaurant.getEquipeActuelle().getServeur1().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getServeur1().getNom());
        print("Serveur 2 : " + Restaurant.getEquipeActuelle().getServeur2().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getServeur2().getNom());
        print("\nCuisinier 1 : " + Restaurant.getEquipeActuelle().getCuisinier1().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier1().getNom());
        print("Cuisinier 2 : " + Restaurant.getEquipeActuelle().getCuisinier2().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier2().getNom());
        print("Cuisinier 3 : " + Restaurant.getEquipeActuelle().getCuisinier3().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier3().getNom());
        print("Cuisinier 4 : " + Restaurant.getEquipeActuelle().getCuisinier4().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getCuisinier4().getNom());
        print("\nBarman : " + Restaurant.getEquipeActuelle().getBarman().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getBarman().getNom());
        print("\nManager : " + Restaurant.getEquipeActuelle().getManager().getPrenom() + " "
                + Restaurant.getEquipeActuelle().getManager().getNom());
        print("--------------------------------------------------------------------------\n");

        print("Appuyez sur Entrée pour revenir au menu principal...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
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