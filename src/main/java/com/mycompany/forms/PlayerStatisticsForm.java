/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.forms;

/**
 *
 * @author user
 */
public class PlayerStatisticsForm {
    private String scoredGoals;
    private String lostGoals;
    private String yellowCards;
    private String redCards;
    private String minutesPlayed;
    private String faulsCommited;

    public String getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(String scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public String getLostGoals() {
        return lostGoals;
    }

    public void setLostGoals(String lostGoals) {
        this.lostGoals = lostGoals;
    }

    public String getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(String yellowCards) {
        this.yellowCards = yellowCards;
    }

    public String getRedCards() {
        return redCards;
    }

    public void setRedCards(String redCards) {
        this.redCards = redCards;
    }

    public String getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(String minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public String getFaulsCommited() {
        return faulsCommited;
    }

    public void setFaulsCommited(String faulsCommited) {
        this.faulsCommited = faulsCommited;
    }
    
    
}
