package br.com.ness.skywars.game.chest;

public enum ChestClass {

    BASIC("Basico"), COMPLEMENT("Complemento"), FEAST("Feast");

    private final String name;

    ChestClass(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
