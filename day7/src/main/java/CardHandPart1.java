import org.apache.commons.lang.StringUtils;

public class CardHandPart1 implements Comparable<CardHandPart1> {
    enum CardValue {
        TWO('2', 2), THREE('3', 3), FOUR('4', 4), FIVE('5', 5), SIX('6', 6), SEVEN('7', 7), EIGHT('8', 8), NINE('9', 9), TEN('T', 10), JACK('J', 11), QUEEN('Q', 12), KING('K', 13), ACE('A', 14);

        private final char label;
        private final int value;

        CardValue(char label, int value) {
            this.label = label;
            this.value = value;
        }

        public char getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }
    }

    private String cards;
    private int bid;

    public CardHandPart1(String cards, int bid) {
        this.cards = cards;
        this.bid = bid;
    }


    public String getCards() {
        return cards;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(CardHandPart1 o) {
        return compareHands(this.cards, o.cards);
    }

    private int compareHands(String hand1, String hand2) {
        if (isFiveOfAKind(hand1) && !isFiveOfAKind(hand2)) {
            return 1;
        } else if (!isFiveOfAKind(hand1) && isFiveOfAKind(hand2)) {
            return -1;
        } else if (isFiveOfAKind(hand1) && isFiveOfAKind(hand2)) {
            return compareCardValues(hand1, hand2);
        } else if (isFourOfAKind(hand1) && !isFourOfAKind(hand2)) {
            return 1;
        } else if (!isFourOfAKind(hand1) && isFourOfAKind(hand2)) {
            return -1;
        } else if (isFourOfAKind(hand1) && isFourOfAKind(hand2)) {
            return compareCardValues(hand1, hand2);
        } else if (isFullHouse(hand1) && !isFullHouse(hand2)) {
            return 1;
        } else if (!isFullHouse(hand1) && isFullHouse(hand2)) {
            return -1;
        } else if (isFullHouse(hand1) && isFullHouse(hand2)) {
            return compareCardValues(hand1, hand2);
        } else if (isThreeOfAKind(hand1) && !isThreeOfAKind(hand2)) {
            return 1;
        } else if (!isThreeOfAKind(hand1) && isThreeOfAKind(hand2)) {
            return -1;
        } else if (isThreeOfAKind(hand1) && isThreeOfAKind(hand2)) {
            return compareCardValues(hand1, hand2);
        } else if (hasTwoPairs(hand1) && !hasTwoPairs(hand2)) {
            return 1;
        } else if (!hasTwoPairs(hand1) && hasTwoPairs(hand2)) {
            return -1;
        } else if (hasTwoPairs(hand1) && hasTwoPairs(hand2)) {
            return compareCardValues(hand1, hand2);
        } else if (isPair(hand1) && !isPair(hand2)) {
            return 1;
        } else if (!isPair(hand1) && isPair(hand2)) {
            return -1;
        } else if (isPair(hand1) && isPair(hand2)) {
            return compareCardValues(hand1, hand2);
        } else {
            return compareCardValues(hand1, hand2);
        }

    }

    public static boolean hasTwoPairs(String hand1) {
        // remove duplicates from hand
        StringBuilder handWithoutDuplicates = new StringBuilder();
        for (Character card : hand1.toCharArray()) {
            if (!handWithoutDuplicates.toString().contains(String.valueOf(card))) {
                handWithoutDuplicates.append(card);
            }
        }
        // compare every char with every other char
        int pairCount = 0;
        for (Character card : handWithoutDuplicates.toString().toCharArray()) {
            int count = StringUtils.countMatches(hand1, String.valueOf(card));
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount == 2;
    }

    public static int compareCardValues(String hand1, String hand2) {
        int index = 0;
        for (Character card : hand1.toCharArray()) {
            int cardValue1 = getCardValue(card);
            int cardValue2 = getCardValue(hand2.charAt(index));
            index++;
            if (cardValue1 > cardValue2) {
                return 1;
            } else if (cardValue1 < cardValue2) {
                return -1;
            }
        }
        return 0;
    }

    public static int getCardValue(char cardLabel) {
        for (CardValue cardValue : CardValue.values()) {
            if (cardValue.getLabel() == cardLabel) {
                return cardValue.getValue();
            }
        }
        return 0;
    }


    public static boolean isFullHouse(String hand1) {
        return isThreeOfAKind(hand1) && isPair(hand1);
    }

    private static boolean isPair(String hand) {
        // compare every char with every other char
        for (Character card : hand.toCharArray()) {
            int count = StringUtils.countMatches(hand, String.valueOf(card));
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isFourOfAKind(String hand) {
        for (Character card : hand.toCharArray()) {
            int count = StringUtils.countMatches(hand, String.valueOf(card));
            if (count == 4) {
                return true;
            }
        }
        return false;
    }


    private static boolean isThreeOfAKind(String hand1) {
        for (Character card : hand1.toCharArray()) {
            int count = StringUtils.countMatches(hand1, String.valueOf(card));
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isFiveOfAKind(String hand1) {
        for (Character card : hand1.toCharArray()) {
            int count = StringUtils.countMatches(hand1, String.valueOf(card));
            if (count == 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get if the cards are five of a kind, four of a kind, full house, three of a kind, two pairs, pair or high card
     */
    public String getCardResult() {
        String hand = this.cards;
        if (isFiveOfAKind(hand)) {
            return "five of a kind";
        } else if (isFourOfAKind(hand)) {
            return "four of a kind";
        } else if (isFullHouse(hand)) {
            return "full house";
        } else if (isThreeOfAKind(hand)) {
            return "three of a kind";
        } else if (hasTwoPairs(hand)) {
            return "two pairs";
        } else if (isPair(hand)) {
            return "pair";
        } else {
            return "high card";
        }
    }
}
