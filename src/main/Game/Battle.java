package main.Game;

import main.Entities.Monster;
import main.Entities.Player;
import main.Game.BattleExceptions.PlayerLostException;
import main.Game.BattleExceptions.PlayerRanException;
import main.Game.BattleExceptions.PlayerWonException;
import main.Items.Effects.StatusEffects.Bleed;
import main.Items.Effects.WeaponEffects.WeaponEffectList;
import main.Items.UseableItems.UseableItem;
import main.TextConstants;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Battle {

    private final Game g;

    enum BattleCommands{
        ATTACK("ATTACK"),
        BLOCK("BLOCK"),
        USE("USE"),
        RUN("RUN"),
        NULL("NULL");

        BattleCommands(String command) {
        }

        static BattleCommands parse(String command){
            switch(command.toUpperCase()){
                case "ATTACK" -> {
                    return ATTACK;
                }
                case "BLOCK" ->{
                    return BLOCK;
                }
                case "USE" ->{
                    return USE;
                }
                case "RUN" ->{
                    return RUN;
                }
                default ->{
                    return null;
                }
            }
        }
    }

    public int turnCount = 0;

    protected Player player;
    protected Monster monster;
    private final Scanner sc = new Scanner(System.in);
    private int xpAwarded;

    public Battle(Player player, Monster monster, Game game) throws PlayerLostException, PlayerWonException, PlayerRanException {
        this.player = player;
        this.monster = monster;
        this.g = game;
        this.xpAwarded = this.xpGainCalc();

        System.out.println(TextConstants.BATTLE_START);

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        battleLoop();
    }

    private void battleLoop() throws PlayerLostException, PlayerWonException, PlayerRanException {
        String input = null;

        while(this.turnChangeCheck(input)){
            turnCount++;
            System.out.println(this.monster.getStats());

            System.out.println("\n" + this.player.getStats());

            System.out.println("Your Move.\n");

            System.out.println(TextConstants.BATTLE_OPTIONS);
            input = "";
            input = sc.nextLine().trim();
            attackCommandParser(input);
        }
        if(!input.equalsIgnoreCase("run")) handleBattleOutcome();

    }

    private void handleBattleOutcome() throws PlayerLostException, PlayerWonException {
        if(player.getHp() <= 0){
            throw new PlayerLostException();
        }
        if(monster.getHp() <= 0){
            System.out.println("\n"+"GAINED: " + this.xpAwarded + "XP");
            this.player.levelUp(this.xpAwarded);
            throw new PlayerWonException();
        }
    }

    private void attackCommandParser(String input) throws PlayerRanException {
        boolean commandLock = true;

        input = input.trim().toUpperCase();

        while(commandLock){
            if(BattleCommands.parse(input) != null){
                switch(BattleCommands.parse(input)){
                    case ATTACK -> {
                        handleBattleInteraction(false);
                        commandLock = false;
                    }
                    case BLOCK -> {
                        handleBattleInteraction(true);
                        commandLock = false;
                    }
                    case USE -> {
                        handleUSEBattleCommand();
                        commandLock = false;
                    }
                    case RUN -> {
                        input = "RUN";
                        handleRUNBattleCommand();
                        commandLock = false;
                    }
                    default -> {
                        input = "";
                        System.out.println("Incorrect Battle Command!");
                    }

                }
            }
            else{
                commandLock = false;
                input = "";
                System.out.println("Incorrect Battle Command!");
            }
        }
    }

    private void handleRUNBattleCommand() throws PlayerRanException {
        /*
         * In case of the user responding with "RUN"
         * He will have a random chance to not be able to get away
         */

        Random randomSeed = new Random();

        Random random = new Random(randomSeed.nextLong());

        if(random.nextInt(11) % 3 == 0){
            System.out.println("Couldn't get away!"); //Small chance you couldn't get away safely
            this.turnChangeCheck("RUN");
        }else{
            System.out.println("Got away safely!");

            g.locationID = g.previousLocationID; //Sets it to where you would run back to
            throw new PlayerRanException();
        }
    }


    private void handleBattleInteraction(boolean blocks) { //Meant to handle the battle interraction
        if(monster.getHp() > 0){
            if(!blocks){
                if(monster.getInitiative() > player.getInitiative() && !player.getEquipedWeapon().hasEffect(WeaponEffectList.QUICKDRAW)){ //judging attack order based on initiative
                    monster.attack(player);
                    if(player.getHp() > 0){ //Makes sure that the player doesn't strike the monster after they're dead
                        player.attack(monster);
                        if(player.getEquipedWeapon().hasEffect(WeaponEffectList.EDGED)){
                            monster.getStatusEffects().add(new Bleed(3, //Bleed effects generally applied for 3 turns when it comes to EDGED
                                    player.getEquipedWeapon().getEffect(WeaponEffectList.EDGED).getValue())); //Grabs the effect value and applies it as the bleed.
                        }
                    }

                } else{
                    player.attack(monster);
                    if(monster.getHp() > 0){
                        monster.attack(player); //Makes sure that the monster doesn't strike the player after it's dead (Much more annoying than when the former happens)
                    }
                }
            }else{
                int oldArmourVal = player.getArmour();

                int blockValue = Math.min(20,player.getMAXHP() / 4);
                //Block armour value will be equal to a quarter of the player's Maximum Health (Maximum Health implementing soon)
                //Or up to 20 ARMOUR, whichever comes last
                //System.out.println("DEBUG: player armour = " + blockValue);

                player.setArmour(blockValue);

                if(monster.getInitiative() > player.getInitiative()){ //judging attack order based on initiative
                    monster.attack(player);

                } else{
                    if(monster.getHp() > 0){
                        monster.attack(player); //Makes sure that the monster doesn't strike the player after it's dead (Much more annoying than when the former happens)
                    }
                }

                player.setArmour(oldArmourVal);
            }
        }
    }

    private void displayPlayerUseableItems(){
        System.out.println("\n" +TextConstants.INVENTORY_VIEW + "\n");

        if(player.getInventory().size() != 0){
            for(int i = 0; i < player.getInventory().size(); i++){
                if(player.getInventory().get(i) instanceof UseableItem){
                    System.out.println(i + " | " + player.getInventory().get(i).getName());
                }
            }
        }else{
            System.out.println("NO USEABLE ITEMS");
        }

        System.out.println("\n" + TextConstants.EQUALS_SEPERATOR + "\n");
    }

    private void handleUSEBattleCommand() {

        this.displayPlayerUseableItems(); //Displays useable items.

        while(true){

            try{
                int input = 0;
                System.out.print("Enter Item Number: ");
                input = Integer.parseInt(sc.nextLine().trim());

                if(player.getInventory().get(input) instanceof UseableItem){
                    player.getInventory().get(input).use(player);
                }
                player.getInventory().remove(player.getInventory().get(input));


                return; //FLAG: CHANGE NULL TO MONSTER LATER ON ALONG WITH CHECK FOR OFFENSIVE/DEFENSIVE USEABLE ITEM
            } catch(IndexOutOfBoundsException e){
                System.out.println("\n" + "Number does not correspond to inventory item! Try again!");
            } catch(InputMismatchException e){
                System.out.println("\n" + "(Enter a number!)");
            }
        }


    }

    private boolean turnChangeCheck(String lastInput) {
        /*
        Meant to do all the turn change checks. Such as HP > 0 or any Damage Over Time effects [Future plan]
         */

        for(int i = 0; i < player.getStatusEffects().size(); i++){
            if(player.getStatusEffects().get(i).getDuration() < 0) {
                player.getStatusEffects().remove(player.getStatusEffects().get(i));
            }
            else{
                player.getStatusEffects().get(i).tick(player);
            };

        }

        for(int i = 0; i < monster.getStatusEffects().size(); i++){
            if(monster.getStatusEffects().get(i).getDuration() < 0) {
                monster.getStatusEffects().remove(monster.getStatusEffects().get(i));
            }else{
                monster.getStatusEffects().get(i).tick(monster);
            }

        }

        return player.getHp() > 0 && monster.getHp() > 0;
    }

    private int xpGainCalc(){

        if(this.monster != null){
            return this.monster.getHp() * 3;
        }
        return 0;
    }


    public int getTurnCount() {
        return turnCount;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }
}
