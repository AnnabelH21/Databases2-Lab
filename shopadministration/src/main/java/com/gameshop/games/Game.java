package com.gameshop.games;

public class Game {
    private int id;
    private String name;
    private double price;
    private int inventory;

    public Game(int id, String name, double price, int inventory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    public Game(){}

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getInventory() { return inventory; }

    // Setter
    public void setPrice(double price) { this.price = price; }
    public void setInventory(int inventory) { this.inventory = inventory; }
}