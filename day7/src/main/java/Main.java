import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "src/resources/input.txt";
        Path path = Path.of(filePath);
        List<CardHandPart1> handsPart1 = new ArrayList<>();
        List<CardHandPart2> handsPart2 = new ArrayList<>();
        int resultPart1 = 0;
        int resultPart2 = 0;

        for (String line : Files.readAllLines(path)) {
            String[] parts = line.split(" ");
            String cards = parts[0];
            int bid = Integer.parseInt(parts[1]);
            CardHandPart1 hand = new CardHandPart1(cards, bid);
            CardHandPart2 handForPart2 = new CardHandPart2(cards, bid);
            handsPart2.add(handForPart2);
            handsPart1.add(hand);

        }
        System.out.println("Part1 result: " + resultPart1);

        System.out.println("\n\n Part 1 \n\n");
        handsPart1.sort(null);
        // position is rank of hand, starting at 1
        int positionPart1 = 0;
        for (CardHandPart1 hand : handsPart1) {
            System.out.println(positionPart1 + " " + hand.getCards() + " " + hand.getBid());
            System.out.println(hand.getCardResult());
            positionPart1++;
            resultPart1 += positionPart1 * hand.getBid();
        }

        System.out.println("\n\n Part 2 \n\n");
        handsPart2.sort(null);
        int positionPart2 = 0;
        for (CardHandPart2 hand : handsPart2) {
            System.out.println(positionPart2 + " " + hand.getCards() + " " + hand.getBid());
            System.out.println(hand.getCardResult());
            positionPart2++;
            resultPart2 += positionPart2 * hand.getBid();
        }


        System.out.println("Part2 result: " + resultPart2);

    }
}