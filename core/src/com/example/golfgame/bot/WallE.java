package com.example.golfgame.bot;

import com.example.golfgame.GolfGame;
import com.example.golfgame.bot.botsbehaviors.AdvancedBot;
import com.example.golfgame.bot.botsbehaviors.DQLBot;
import com.example.golfgame.bot.botsbehaviors.RuleBasedBot;
import com.example.golfgame.bot.neuralnetwork.DQLNeuralNetwork;

public class WallE {

    private volatile GolfGame game;
    private BotBehavior botBehavior;
    private RuleBasedBot ruleBasedBot;
    private AdvancedBot advancedBot;
    private DQLBot dqlBot;

    public WallE(GolfGame game) {
        this.game = game;
        this.ruleBasedBot = new RuleBasedBot();
        this.advancedBot = new AdvancedBot();
        DQLNeuralNetwork mainNetwork = new DQLNeuralNetwork(new int[]{4, 20,20, 2});
        DQLNeuralNetwork targetNetwork = new DQLNeuralNetwork(new int[]{4, 20,20, 2});
        this.dqlBot = new DQLBot(mainNetwork, targetNetwork, 1000, 1.0, 0.995, 0.1, 0.99, 32,"neuralnetworkinformation/mainNetwork.ser","neuralnetworkinformation/targetNetwork.ser");
        this.botBehavior = ruleBasedBot; // Default behavior
    }

    public void setDirection() {
        float adjustedAngle = botBehavior.setDirection(game);
        game.getGolfGameScreen().setCameraAngle(adjustedAngle);
    }

    public synchronized void hit() {
        botBehavior.hit(game);
    }

    public void setBotBehavior(BotBehavior botBehavior) {
        this.botBehavior = botBehavior;
    }

    public void switchToRuleBased() {
        setBotBehavior(ruleBasedBot);
    }

    public void switchToAdvanced() {
        setBotBehavior(advancedBot);
    }

    public void switchToDQL() {
        setBotBehavior(dqlBot); 
    }

    public BotBehavior getBotBehavior(){
        return botBehavior;
    }
}