package simplePairingGenerator;

import java.util.Random;
import circularlyLinkedList.CircularlyLinkedListManager;

public class SimpleGenerator {
	// TODO: finish refactoring to remove language of rockers
	/***
	 * Generates pairings based on taking a random number of steps around a
	 * circularly linked list. Follows the rules of no self matches and each
	 * participant having one invalid match. Each participant has a number, the
	 * invalid pairing is a lookup based on that number. The number is their
	 * position in the returned array
	 * @param numberOfParticipants
	 * @param invalidPairings
	 * @return array of pairings. the ID of the
	 */
	protected static int[] generatePairingsThroughRandomWalk() {
		// Read in rocker names, their location in the array is their "ID"
		// There isn't a need to use this ID in this way, you could easily use a
		// string and a hashmap
		// using the id to index the pairings eliminates the overhead of a
		// hashmap, also int comparison is a little bit faster than string
		// all operations in the function on done by this id
		String[] rockerNames = RockerInfoManager.rockerList;

		// Create cycle of rocker id's
		CircularlyLinkedListManager<Integer> rockers = new CircularlyLinkedListManager<Integer>();
		for (int i = 0; i < rockerNames.length; i++) {
			rockers.addItem(new Integer(i));
		}

		// This year's pairings by id. Location is id of who is buying, value is
		// who they are buying for.
		int[] thisYearPairingById = new int[rockerNames.length];

		// Store first buyer. They will be removed from the list. The final
		// person will buy for them, if valid
		int firstBuyer = rockers.getCurrentItem();

		int currentBuyer;
		int numSteps;
		Random rand = new Random();
		// The final person buys for the first if that is valid
		while (rockers.getSize() > 1) {
			// store id this is who is buying this time
			currentBuyer = rockers.getCurrentItem();
			rockers.removeCurrentItem(); // no one buys for themselves, someone
											// else has already bought for them
											// so it is safe to remove

			// move number of steps between 0 and length-1
			numSteps = rand.nextInt(rockers.getSize());
			rockers.stepThroughList(numSteps);

			// check if valid, if not take anywhere between 1 and length-1
			// it could just go to the next name but then it wouldn't be truly
			// random
			if (!isValidPairing(currentBuyer, rockers.getCurrentItem())) {
				if (rockers.getSize() == 1) {
					return null; // We have an invalid matching at the end
				}
				numSteps = rand.nextInt(rockers.getSize() - 1) + 1; // don't
																	// want to
																	// end up on
																	// the
																	// conflict
																	// again
				rockers.stepThroughList(numSteps); // due to restrictions of the
													// number of steps we can't
													// be at who they bought for
													// last year
			}

			thisYearPairingById[currentBuyer] = rockers.getCurrentItem();
		}

		// There is only be 1 rocker left
		// Test if final matching is valid
		if (!isValidPairing(rockers.getCurrentItem(), firstBuyer)) {
			return null;
		} else {
			thisYearPairingById[rockers.getCurrentItem()] = firstBuyer;
		}

		return thisYearPairingById;
	}

	protected static boolean isValidPairing(int giver, int getter) {
		return RockerInfoManager.lastYearPairById[giver] != getter;
	}

	protected static String createStringOutputForBuyingMapping(int[] idPairing, String[] nameMap) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < idPairing.length; i++) {
			output.append(nameMap[i] + RockerInfoManager.buyingText + nameMap[idPairing[i]] + "\n");
		}
		return output.toString();
	}

	public static String generatePairing() {
		int[] buyingList = generatePairingsThroughRandomWalk();
		while (buyingList == null) {
			buyingList = generatePairingsThroughRandomWalk();
		}
		return createStringOutputForBuyingMapping(buyingList, RockerInfoManager.rockerList);
	}

}
