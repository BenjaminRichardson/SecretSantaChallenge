package multiSeasonPairingGenerator;

import java.util.*;
import simplePairingGenerator.RockerInfoManager;

public class MultiPairingTester {

	public static void main(String args[]) {
		MultiPairingTester mpt = new MultiPairingTester();
		System.out.println("Starting Test");
		int numberOfTests = 100000;

		Validator validator = mpt.new Validator(RockerInfoManager.rockerList, RockerInfoManager.lastYearPairById, 4);
		MultiplePairingGenerator mg = new MultiplePairingGenerator(RockerInfoManager.rockerList,
				RockerInfoManager.lastYearPairById);

		for (int i = 0; i < numberOfTests; i++) {
			if ((i + 1) % 100 == 0) {
				System.out.println((i + 1) + " Tests completed successfully");
			}
			// setup for new test
			mg = new MultiplePairingGenerator(RockerInfoManager.rockerList, RockerInfoManager.lastYearPairById);
			validator = mpt.new Validator(RockerInfoManager.rockerList, RockerInfoManager.lastYearPairById, 4);

			for (int j = 0; j < 6; j++) {
				String output = mg.generateNextPairing();
				if (!validator.validateOutput(output)) {
					System.out.println("ERROR, invalid input detected:\n" + output);
					return;
				}
			}
		}
	}

	// Simple testing class. Stores the last few seasons and makes sure there
	// are not repeats
	private class Validator {

		private HashMap<String, Queue<String>> invalidPairings;
		private int invalidMaxSize;

		public Validator(String[] names, int[] invalid, int invalidMaxSize) {
			this.invalidMaxSize = invalidMaxSize;

			invalidPairings = new HashMap<String, Queue<String>>();
			for (int i = 0; i < names.length; i++) {
				String invalidName = names[invalid[i]];
				Queue<String> newList = new LinkedList<String>();
				newList.add(invalidName);
				invalidPairings.put(names[i], newList);
			}

		}

		public boolean validateOutput(String output) {
			boolean result = true;
			Scanner sc = new Scanner(output);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] split = line.split(RockerInfoManager.buyingText);
				Queue<String> invalidNames = invalidPairings.get(split[0]);// invalid
																			// pairings
																			// for
																			// current
																			// rocker
																			// who
																			// is
																			// buying
				String getter = split[1]; // person the current rocker is buying
											// for
				if (invalidNames.contains(getter) || getter.equals(split[0])) {
					result = false;
				}
				invalidNames.add(getter);
				if (invalidNames.size() > invalidMaxSize) {
					invalidNames.remove();
				}
			}
			sc.close();
			return result;
		}

	}

}
