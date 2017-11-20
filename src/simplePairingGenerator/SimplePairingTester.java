package simplePairingGenerator;

public class SimplePairingTester {

	static String[] rockerList = RockerInfoManager.rockerList;
	static int[] lastYear = RockerInfoManager.lastYearPairById;

	private static void testSimplePairer(int numTimes) {

		for (int i = 0; i < numTimes; i++) {
			if (i > 0 && i % 100 == 0) {
				System.out.println("" + i + " tests completed successfully");
			}
			if (!testSingle()) {
				System.out.println("Test Failed After " + (i + 1) + " Iterations");
				return;
			}
		}
		System.out.println("All " + numTimes + " Tests Passed");
	}

	private static boolean testSingle() {
		int[] pairing = SimpleGenerator.generatePairingsThroughRandomWalk();
		while (pairing == null) {
			pairing = SimpleGenerator.generatePairingsThroughRandomWalk();
		}
		if (pairingIsInvalid(pairing)) {
			System.out.println("Invalid Pairing found");
			System.out.println(SimpleGenerator.createStringOutputForBuyingMapping(pairing, rockerList));
			return false;
		}
		return true;
	}

	private static boolean pairingIsInvalid(int[] pairing) {
		for (int i = 0; i < pairing.length; i++) {
			int buyer = i;
			int getter = pairing[i];
			if (lastYear[buyer] == getter) {
				System.out.println("Pairing of " + rockerList[buyer] + " and " + rockerList[getter]
						+ " is invalid. Repeats last year");
				return true;
			}
			if (pairing[getter] == buyer) {
				System.out.println("Pairing of " + rockerList[buyer] + " and " + rockerList[getter]
						+ " is invalid. No reciprocal gifting");
				return true;
			}
		}
		return false;
	}

	public static void main(String args[]) {
		// since the function is random we have to run a lot of tests.
		testSimplePairer(100000);
	}
}
