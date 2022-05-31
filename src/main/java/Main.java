import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int[] playerOneDice = new int[Constants.NUM_DIE];
    private static final int[] playerTwoDice = new int[Constants.NUM_DIE];

    private static HandManager.Hand playerOneHand;
    private static HandManager.Hand playerTwoHand;

    public static boolean firstReroll = true;

    public static void main(String[] args) {
        int playerOneWins = 0;
        int playerTwoWins = 0;
        Scanner input = new Scanner(System.in);
        for(int i = 0; i < Constants.ROUNDS; i++){
            PrintManager.printRound(i + 1, playerOneWins, playerTwoWins);
            phaseOne(input);
            phaseTwo(input);
            if(!Arrays.equals(playerOneDice, playerTwoDice)) {
                if (HandManager.winCheck(playerOneHand, playerTwoHand, playerOneDice, playerTwoDice)) {
                    playerOneWins++;
                    System.out.println("Player one wins this round!");
                } else {
                    playerTwoWins++;
                    System.out.println("Player two wins this round!");
                }
            } else {
                System.out.println("Hands identical, replaying round");
                i--;
            }
        }
        PrintManager.printWin(playerOneWins > playerTwoWins);
    }

    private static void phaseOne(Scanner input){
        System.out.println("Player one's dice will now be rolled");
        rollPhaseOne(playerOneDice, input);
        System.out.println("Player two's dice will now be rolled");
        rollPhaseOne(playerTwoDice, input);
        playerOneHand = HandManager.getHand(playerOneDice);
        playerTwoHand = HandManager.getHand(playerTwoDice);
        PrintManager.printHand(playerOneHand, true);
        PrintManager.printHand(playerTwoHand, false);
    }

    private static void phaseTwo(Scanner input){
        System.out.println("Player one: Which dice would you like to re-roll?");
        if(firstReroll) {
            System.out.println("Enter a 5 digit binary string with a 1 indicating a die you would like to re-roll\n(ex. 01101)");
            firstReroll = false;
        }
        boolean[] playerOneReroll = binaryToBooleanArray(input.nextLine());
        rollDice(playerOneDice, playerOneReroll);
        PrintManager.printDice(playerOneDice);
        System.out.println("Player two: Which dice would you like to re-roll?");
        boolean[] playerTwoReroll = binaryToBooleanArray(input.nextLine());
        rollDice(playerTwoDice, playerTwoReroll);
        PrintManager.printDice(playerTwoDice);
        playerOneHand = HandManager.getHand(playerOneDice);
        playerTwoHand = HandManager.getHand(playerTwoDice);
        PrintManager.printHand(playerOneHand, true);
        PrintManager.printHand(playerTwoHand, false);
    }

    private static boolean[] binaryToBooleanArray(String binary){
        boolean[] reroll = new boolean[Constants.NUM_DIE];
        for(int i = 0; i < Constants.NUM_DIE; i++){
            reroll[i] = binary.charAt(i) == '1';
        }
        return reroll;
    }

    private static void rollPhaseOne(int[] dice, Scanner input){
        System.out.println("Press \"Enter\" to continue");
        input.nextLine();
        rollDice(dice);
        PrintManager.printDice(dice);
    }

    private static void rollDice(int[] dice){
        rollDice(dice, new boolean[]{true, true, true, true, true});
    }

    private static void rollDice(int[] dice, boolean[] reroll){
        Random random = new Random();
        for(int i = 0; i < Constants.NUM_DIE; i++){
            if(reroll[i]) {
                dice[i] = random.nextInt(6) + 1;
            }
        }
    }
}