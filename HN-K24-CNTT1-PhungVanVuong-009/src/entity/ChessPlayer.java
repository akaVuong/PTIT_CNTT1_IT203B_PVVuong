package entity;

import java.util.Scanner;
import java.util.*;

interface IChessPlayer {
    void inputData(Scanner scanner);
    void displayData();
}

public class ChessPlayer implements IChessPlayer {
    private String playerId;
    private String playerName;
    private int age;
    private int elo;

    public ChessPlayer(){}

    public ChessPlayer(String playerId, String playerName, int age, int elo) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.age = age;
        this.elo = elo;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    @Override
    public void inputData(Scanner scanner){
        Scanner sc = new Scanner(System.in);
        System.out.println("nhập Id: ");
        this.playerId = scanner.nextLine();

        System.out.println("nhập tên: ");
        this.playerName = scanner.nextLine();

        System.out.println("nhập trình độ: ");
        this.elo = Integer.parseInt(scanner.nextLine());

        System.out.println(" nhập tuổi: ");
        this.age = Integer.parseInt(scanner.nextLine());
        if(age <18){
            System.out.println("tuổi không hợp lệ");
        }
    }

    @Override
    public void displayData(){
        System.out.println("Id: " + playerId +" ,tên: "+ playerName + " ,tuổi: " +age + " ,trình độ:"+elo);
    }
}
