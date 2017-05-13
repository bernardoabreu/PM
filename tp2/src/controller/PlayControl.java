package controller;

import model.*;
import view.Display;
import java.util.List;
import java.util.ArrayList;


public class PlayControl {

    private Table table;

    private Display d;

    private Play play;

    public PlayControl(Display d, List<Team> teams, int teamSize){

        this.play = new Play(teams, teamSize);
        this.d = d;
        this.table = new Table(teams.size(), teamSize);

    }


    public Team getWinnerTeam(){
        return this.play.getWinnerTeam();
    }


    public void run(int firstPlayerIndex){
        this.d.printString("Start of PlayControl\n");

//        print stuff (Cards)
        for(Team t : this.play.getTeams()){
            for(Player p : t.getPlayers()){
                System.out.println("Player " + p.getId() + " team " + t.getId());
                for(Card c : p.getHand()){
                    System.out.print(c + " ");
                }
                System.out.println();
            }
        }

        this.play.setPlayValue(PlayValue.NORMAL);

        FsmPlay chain = new FsmPlay();

        chain.setTable(this.table);
        chain.setPlayValue(this.play.getPlayValue());

        for(Player p : this.play.getPlayers()){
            chain.addPlayer(p);
        }

        chain.setFirstIndex(firstPlayerIndex);
        while (!chain.end()) {
            chain.change();
        }

        int winnerTeamId = chain.getWinnerTeamId();

        this.play.setPlayValue(chain.getPlayValue());

        if(winnerTeamId != -1){
            this.play.setWinnerTeam(this.play.getTeams().get(winnerTeamId));
//            System.out.println("playValue " + this.play.getPlayValue() + ": " + this.play.getPlayValue().getValue());
//            System.out.println("\nWinner team:  " + String.valueOf(this.play.getWinnerTeam().getId()));
            this.play.getWinnerTeam().addScore(this.play.getPlayValue().getValue());
        }

    }

}