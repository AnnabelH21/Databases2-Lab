package com.gameshop.sales;
import com.gameshop.games.GameRepository;

public class SalesService {
    private SalesRepository salesRepo = new SalesRepository();
    private GameRepository gameRepo = new GameRepository();
    private String info;

    public SalesResult getRevenueForSelection(Integer year, int monthIndex, String monthName) {
        if (year == null) {
            return new SalesResult(0.0, "BITTE JAHR WÄHLEN!");
        }

        Double result;
        if (monthIndex == 0) {
            // No month chosen -> Get yearly revenue
            result = salesRepo.getYearlyRevenue(year);
            info = "Gesamtumsatz Jahr " + year + ": " + result + "€";
        } else {
            // Specific month
            result = salesRepo.getMonthlyRevenue(year, monthIndex);
            info = "Umsatz " + monthName + " " + year + ": " + result + "€";
        }

        return new SalesResult(result != null ? result : 0.0, info);
    }

    public void addSaleEntry(String gameId, String gameName, String amount){

        if(gameId == null ||gameName == null | amount == null){
            System.err.print("Eingaben sind nicht gültig!");
            return;
        }

        try{
            Integer gameIdConverted = Integer.parseInt(gameId);
            Integer amountConverted = Integer.parseInt(amount);
            Double price = gameRepo.findGameById(gameIdConverted).getPrice()*amountConverted;

            salesRepo.addSale(gameIdConverted,gameName, amountConverted,price);
        }catch( NumberFormatException e){
            e.printStackTrace();
        }
    }
}