package UniqueIdGenerator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueIdGenerator {
    private Set<Integer> generatedIds;
    private Random random;
    private int minRange;
    private int maxRange;

    public UniqueIdGenerator(int minRange, int maxRange) {
        this.generatedIds = new HashSet<>();
        this.random = new Random();
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public int generateUniqueId() {
        if (generatedIds.size() >= (maxRange - minRange + 1)) {
            throw new IllegalStateException("All possible IDs have been generated.");
        }

        int newId;
        do {
            newId = random.nextInt(maxRange - minRange + 1) + minRange;
        } while (generatedIds.contains(newId));

        generatedIds.add(newId);
        return newId;
    }


    public void reset() {
        generatedIds.clear();
    }
}

