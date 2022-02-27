package br.com.ness.skywars.game;

public enum GameStatus {

    WAITING("Esperando"), PREPARING("Preparando"), STARTING("Começando"), PLAYING("Jogando"), FINISHING("Finalizando");


    //WAITING - Menos de 2 players
    //PREPARING - 2 players e menos do maximo de players
    //STARTING - Maximo de players
    //PLAYING - jogando
    //FINISHING - finalizando a partida

    private final String status;

    GameStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
