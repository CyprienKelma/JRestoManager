import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Quel écran souhaitez-vous afficher?");
        System.out.println("1- Ecran prise de commande");
        System.out.println("2- Ecran cuisine");
        System.out.println("3- Ecran bar");
        System.out.println("4- Ecran Monitoring");

        Scanner scanner = new Scanner(System.in);
        int choixEcran = scanner.nextInt();

        switch (choixEcran) {
            case 1:
                // Appeler la fonction pour l'écran de prise de commande
                break;
            case 2:
                // Appeler la fonction pour l'écran de cuisine
                break;
            case 3:
                // Appeler la fonction pour l'écran de bar
                break;
            case 4:
                // Appeler la fonction pour l'écran de monitoring
                break;
            default:
                System.out.println("Choix invalide");
        }
    }
}
