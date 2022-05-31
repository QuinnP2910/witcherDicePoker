public class PrintManager {
    public static void printRound(int round, int playerOneWins, int playerTwoWins){
        System.out.println("ROUND " + round);
        System.out.println("--SCORES--");
        System.out.println("Player one: " + playerOneWins + "; Player two: " + playerTwoWins);
    }

    public static void printDice(int[] dice){
        for(int i = 0; i < Constants.NUM_DIE; i++){
            System.out.print(dice[i] + " ");
        }
        System.out.println("\n");
    }

    public static void printWin(boolean playerOneWin){
        if(playerOneWin){
            System.out.println("Player one wins!");
        } else {
            System.out.println("Player two wins!");
        }
    }

    public static void printHand(HandManager.Hand hand, boolean playerOne){
        if(playerOne){
            System.out.print("Player one has ");
        } else {
            System.out.print("Player two has ");
        }
        switch(hand){
            case QUINTUPLE:
                System.out.println("a five of a kind");
                break;
            case QUADRUPLE:
                System.out.println("a four of a kind");
                break;
            case FULL_HOUSE:
                System.out.println("a full house");
                break;
            case SIX_HIGH:
                System.out.println("a six high straight");
                break;
            case FIVE_HIGH:
                System.out.println("a five high straight");
                break;
            case TRIPLE:
                System.out.println("a three of a kind");
                break;
            case TWO_DOUBLES:
                System.out.println("two pairs");
                break;
            case DOUBLE:
                System.out.println("a pair");
                break;
            default:
                System.out.println("nothing");
                break;
        }
    }
}
