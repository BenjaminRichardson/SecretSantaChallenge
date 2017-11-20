package multiSeasonPairingGenerator;

import java.util.*;

public class MultiGenerator {

	/***
	 * List of rockers in order they were passed to constructor
	 */
	private List<Rocker> rockers;
	
	/***
	 * Class that maintains a list of rocker names, generates random pairings. Checks the validity of these pairings as well.
	 * @param rockerNames Array of names of rockers. 
	 * @param previousPairings Array of previous pairings, based on the index of the rocker names in the rocker names list. So if the value at array index 2 is 0, that means that
	 * 							the third name on the list bought for the first name on the list. Currently only supports one seasons depth.
	 */
	public MultiGenerator(String[] rockerNames, int[] previousPairings){
		if(rockerNames.length != previousPairings.length)
			throw new IllegalArgumentException("Previous Pairings must have same length as rocker names to be valid");
		
		rockers = new ArrayList<Rocker>();
		for (int i=0; i< rockerNames.length; i++){
			rockers.add(new Rocker(rockerNames[i]));
		}
		
		// Populate invalid rocker info
		Rocker currentRocker;
		Rocker invalidRocker;
		for (int i=0; i < rockerNames.length; i++){
			currentRocker = rockers.get(i);
			invalidRocker = rockers.get(previousPairings[i]);
			currentRocker.addInvalidRecipient(invalidRocker);
		}
	}
	
	/***
	 * This function generates a new pairing of rocker names as a string, keeping the constrains that no one gives to themselves and verifies that there are no repeat givings
	 * It also calls the functions which updates the history of each of the rockers 
	 * @return string of X is buying for Y. Names on the X side are in order they were passed into the constructor
	 */
	public String generateNextPairing(){
		// copy list, so it has the same contents for shuffle, but maintain original list for order
		// Shuffle ensures each name will have a pair, not necissarily valid though
		List<Rocker> recipientList = new ArrayList<Rocker>(rockers); 
		Collections.shuffle(recipientList);
		while (!setPairing(rockers, recipientList)){
			resetRecipients();
			Collections.shuffle(recipientList);
		}
		String output = generatePairingString();
		resetAndStoreRecipients();
		return output;
	}
	
	
	private void resetRecipients(){
		for(Rocker r: rockers){
			r.resetRecipient();
		}
	}
	
	/***
	 *  Iterates over all rockers to prep for next generation.
	 */
	private void resetAndStoreRecipients() {
		for(Rocker r: rockers){
			r.storeAndResetRecipient();
		}
	}
	
	/***
	 * Checks if it is valid for giver to buy a gift for receiver
	 * @param giver
	 * @param receiver
	 * @return false if invalid, true otherwise
	 */
	private static boolean isPairingValid(Rocker giver, Rocker receiver){
		if(receiver.getRecipient() != null && receiver.getRecipient() == giver){
			return false;
		}
		return giver.isRecipientValid(receiver);
	}
	
	/***
	 * Fills in the receiver field on the Rocker object in givers if valid, otherwise reutrns false
	 * @param givers
	 * @param receivers
	 * @return true if everything is inserted correctly, false otherwise
	 */
	private static boolean setPairing(List<Rocker> givers, List<Rocker> receivers){
		Rocker giver;
		Rocker receiver;
		for (int i=0; i<givers.size(); i++){
			giver = givers.get(i);
			receiver = receivers.get(i);
			if (!isPairingValid(giver, receiver)){
				return false;
			}
			giver.setRecipient(receiver);
		}
		return true;
	}
	
	/***
	 * Creates the string for output of the pairing generation
	 * @return string of X is buying for Y.
	 */
	private String generatePairingString(){
		StringBuilder s = new StringBuilder();
		//Rocker's order hasn't been modified since initial import
		for (Rocker r: rockers){
			s.append(r+"\n");
		}
		return s.toString();
	}
	
}
