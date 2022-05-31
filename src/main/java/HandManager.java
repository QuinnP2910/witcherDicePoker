public class HandManager {
    public static int[] getTwoHighestDieFrequencies(int[] dice){
        int[] twoHighestDice = new int[4];
        twoHighestDice[0] = getHighestDieFrequency(dice)[0];
        twoHighestDice[1] = getHighestDieFrequency(dice)[1];
        twoHighestDice[2] = getHighestDieFrequency(dice, twoHighestDice[1])[0];
        twoHighestDice[3] = getHighestDieFrequency(dice, twoHighestDice[1])[1];
        return twoHighestDice;
    }

    public static int[] getHighestDieFrequency(int[] dice){
        return getHighestDieFrequency(dice, 0);
    }

    public static int[] getHighestDieFrequency(int[] dice, int exclude){
        int mostFrequent = 0;
        int highestFrequency = 0;
        for(int i = 0; i < Constants.MAX_VALUE; i++){
            if(i != exclude - 1) {
                int frequency = getDieFrequency(i + 1, dice);
                if(frequency == highestFrequency && i + 1 > mostFrequent) {
                    mostFrequent = i + 1;
                }
                if(frequency > highestFrequency) {
                    mostFrequent = i + 1;
                    highestFrequency = frequency;
                }
            }
        }
        return new int[]{highestFrequency, mostFrequent};
    }

    public static int getDieFrequency(int num, int[] dice){
        int frequency = 0;
        for(int i = 0; i < Constants.NUM_DIE; i++){
            if(dice[i] == num){
                frequency++;
            }
        }
        return frequency;
    }

    public static boolean straightChecker(int[] dice){
        return straightChecker(dice, false);
    }

    public static boolean straightChecker(int[] dice, boolean sixHigh){
        boolean containsNum = false;
        int offset = 0;
        if(sixHigh){
            offset = 1;
        }
        for(int i = offset; i < 5 + offset; i++){
            for(int j = 0; j < Constants.NUM_DIE; j++){
                if (dice[j] == i + 1) {
                    containsNum = true;
                    break;
                }
            }
            if(!containsNum){
                return false;
            }
            containsNum = false;
        }
        return true;
    }

    public static Hand getHand(int[] dice){
        int[] twoHighestDieFrequencies = HandManager.getTwoHighestDieFrequencies(dice);
        if(twoHighestDieFrequencies[0] == 5) {
            return Hand.QUINTUPLE;
        } else if(twoHighestDieFrequencies[0] == 4){
            return Hand.QUADRUPLE;
        } else if(twoHighestDieFrequencies[0] == 3){
            if(twoHighestDieFrequencies[2] == 2){
                return Hand.FULL_HOUSE;
            } else {
                return Hand.TRIPLE;
            }
        } else if(twoHighestDieFrequencies[0] == 2){
            if(twoHighestDieFrequencies[2] == 2){
                return Hand.TWO_DOUBLES;
            } else {
                return Hand.DOUBLE;
            }
        } else if(HandManager.straightChecker(dice)){
            return Hand.FIVE_HIGH;
        } else if(HandManager.straightChecker(dice, true)){
            return Hand.SIX_HIGH;
        } else {
            return Hand.NOTHING;
        }
    }

    public static boolean winCheck(Hand playerOneHand, Hand playerTwoHand, int[] playerOneDice, int[] playerTwoDice){
        int playerOneHandScore = handToInt(playerOneHand);
        int playerTwoHandScore = handToInt(playerTwoHand);
        int playerOneHighestWinningDie = getHighestDieFrequency(playerOneDice)[1];
        int playerTwoHighestWinningDie = getHighestDieFrequency(playerTwoDice)[1];
        int playerOneSecondHighestDie = getTwoHighestDieFrequencies(playerOneDice)[3];
        int playerTwoSecondHighestDie = getTwoHighestDieFrequencies(playerTwoDice)[3];
        if(playerOneHandScore == playerTwoHandScore){
            if(playerOneHighestWinningDie != playerTwoHighestWinningDie){
                return playerOneHighestWinningDie > playerTwoHighestWinningDie;
            } else {
                return playerOneSecondHighestDie > playerTwoSecondHighestDie;
            }
        }
        return playerOneHandScore > playerTwoHandScore;
    }

    public static int handToInt(Hand hand){
        switch (hand){
            case QUINTUPLE:
                return 8;
            case QUADRUPLE:
                return 7;
            case FULL_HOUSE:
                return 6;
            case SIX_HIGH:
                return 5;
            case FIVE_HIGH:
                return 4;
            case TRIPLE:
                return 3;
            case TWO_DOUBLES:
                return 2;
            case DOUBLE:
                return 1;
            default:
                return 0;
        }
    }

    public enum Hand {
        NOTHING, DOUBLE, TWO_DOUBLES, TRIPLE, FIVE_HIGH, SIX_HIGH, FULL_HOUSE, QUADRUPLE, QUINTUPLE
    }
}
