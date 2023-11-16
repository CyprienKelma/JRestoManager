package main.carte;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import main.carte.aliment;
import java.nio.file.Files;
import java.nio.file.Path;


public class stock {

    public static String LireFichier(String nomfichier) throws IOException{
        Path fichier = Path.of(nomfichier);
        Charset charset = charset.forName("windows-1252");
        if(Files.exists(fichier) == true){
            boolean estAccessible = Files.isRegularFile(fichier) 
            && Files.isReadable(fichier) && Files.isExecutable(fichier);
            if(estAccessible == true){
                


            }



        }
    }

    public static String AjouterAliment(String nomfichier) throws IOException{
        
    }

    public static String RetirerAliment(String nomfichier) throws IOException{
        
    }


    public static void main(String[] args) {
       
        
    }
}