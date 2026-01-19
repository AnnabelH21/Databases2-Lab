package com.gameshop.games;

import java.io.IOException;
import java.sql.Timestamp;

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

    public void archiveGame(String gameId){

        if(gameId == null){
            System.err.println("Ungültige Eingabe!");
            return;
        }
        try{
            int intGameId = Integer.parseInt(gameId);
            gameRepo.deleteGame(intGameId);
        }catch(NumberFormatException e){
            System.err.println("Id ist keine gültige Zahl");
        }
    }

    public void changePrice(String gameId, String fixedPrice, String salePercentage){

        try{
            int gameIdConverted = Integer.parseInt(gameId);
            double fixedConverted;
            double percentageConverted;
            //Either price change by new price or by percentage
            if(fixedPrice == null && salePercentage != null){
                percentageConverted = Double.parseDouble(salePercentage);
                gameRepo.updateGamePricePercent(gameIdConverted,percentageConverted);
            }
            if(salePercentage == null && fixedPrice != null){
                fixedConverted = Double.parseDouble(fixedPrice);
                gameRepo.updateGamePriceFixed(gameIdConverted, fixedConverted);
            }

        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

}
