package main.place;

import java.util.List;
import main.staff.*;

public class Restaurant {

    private boolean isOpened;
    private List<Employé> employésList;
    private List<Equipe> équipesList;
    // TODO add a stock fields (from the "Stock" class)

    private List<Transaction> listTransactions = null;

    // Need the implementation and import of "Carte" (a Class) and "Carteboissons"
    // (an enum)
    private Carte menu;
    private CarteBoissons menuBoissons;

}