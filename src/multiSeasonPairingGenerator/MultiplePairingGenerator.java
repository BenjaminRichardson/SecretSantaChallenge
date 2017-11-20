package multiSeasonPairingGenerator;

import java.util.*;

import hopcroftKarp.HopcroftKarpSolver;

public class MultiplePairingGenerator {

	List<Rocker> rockers;
	HopcroftKarpSolver<Rocker> hkSolver;

	/**
	 * Creates instance of multiple pairing generator. previous pairings stores
	 * names in array, only has depth of one currently The index of the name
	 * maps should line up with the previous pairings.
	 * 
	 * @param names
	 *            - String array of names of rockers
	 * @param previousPairings
	 *            - The key is the index of the buyer, the value is the index of
	 *            the receiver in the array of names
	 */
	public MultiplePairingGenerator(String[] names, int[] previousPairings) {
		int n = names.length; // Shorthand for readability

		rockers = new ArrayList<Rocker>();
		hkSolver = new HopcroftKarpSolver<Rocker>();

		for (int i = 0; i < n; i++) {
			rockers.add(i, new Rocker(names[i]));
		}

		// Set up graph with valid initial edges
		for (int giverIndex = 0; giverIndex < n; giverIndex++) {
			Rocker giver = rockers.get(giverIndex);
			for (int getterIndex = 0; getterIndex < n; getterIndex++) {
				Rocker getter = rockers.get(getterIndex);
				if (getter != giver) {
					if (getterIndex == previousPairings[giverIndex]) {
						giver.addInvalidRecipient(getter);
					} else {
						giver.addValidRecipient(getter);
					}
				}
			}
		}
	}

	public void updateInvalidList() {
		for (Rocker r : rockers) {
			r.storeAndResetRecipient();
		}
	}

	/***
	 * Generates the next pairing and outputs as a string. It tracks the last 4
	 * pairings and marks them as invalid
	 * 
	 * @return String of X is buying for Y in order of list of names at
	 *         instantiation.
	 */
	public String generateNextPairing() {
		hkSolver.createMatchingGraph(rockers);
		int matchingSize = hkSolver.solveHopcroftKarp();

		if (matchingSize < rockers.size()) {
			// This should never happen. Exists for verification
			throw new RuntimeException("Something is wrong with the graph or with Hopcroft-Karp solver implementation");
		}

		HashMap<Rocker, Rocker> pairs = hkSolver.getMatching();
		for (Rocker r : rockers) {
			r.setRecipient(pairs.get(r));
		}
		String output = generatePairingString();
		updateInvalidList();
		return output;
	}

	private String generatePairingString() {
		StringBuilder s = new StringBuilder();
		// Rocker's order hasn't been modified since initial import
		for (Rocker r : rockers) {
			s.append(r + "\n");
		}
		return s.toString();
	}

}
