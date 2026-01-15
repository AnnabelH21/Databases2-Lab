package com.gameshop.sales;

import java.util.List;

public class SalesService {
    private final SalesRepository salesRepo = new SalesRepository();
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
}