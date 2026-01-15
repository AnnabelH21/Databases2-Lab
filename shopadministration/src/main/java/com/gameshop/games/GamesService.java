package com.gameshop.games;

public class GamesService {
    private GameRepository gameRepo = new GameRepository();
    public void saveNewGameToDB(String name, String priceRaw, String inventory){

        if (name == null || name.isEmpty() || priceRaw == null|| inventory == null) {
            System.err.println("Ungültige Eingabe!");
            return;
        }
        try {
            // Correct types
            double price = Double.parseDouble(priceRaw.replace(",", "."));
            int stock = Integer.parseInt(inventory);

            // Add to DB
            gameRepo.addGame(name, price, stock);

        } catch (NumberFormatException e) {
            System.err.println("Preis ist keine gültige Zahl!");
        }
    }

}
