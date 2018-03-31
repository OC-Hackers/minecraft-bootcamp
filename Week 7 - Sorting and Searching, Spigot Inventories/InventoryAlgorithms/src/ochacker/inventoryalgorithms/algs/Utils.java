package ochacker.inventoryalgorithms.algs;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
	public static int randInt(int min, int max) {
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
}
