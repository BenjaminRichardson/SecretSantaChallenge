package multiSeasonPairingGenerator;

import java.util.*;

import hopcroftKarp.HKGraphNode;
import simplePairingGenerator.RockerInfoManager;

public class Rocker implements HKGraphNode<Rocker> {

	private Queue<Rocker> invalidRecipients; // people who this rocker recently
												// gave up to the season limit
												// cap
	private LinkedList<Rocker> validRecipients;
	private String name; // Name of this Rocker
	private Rocker recipient; // The rocker who this rocker is buying for, null
								// if it has yet to be determined
	public final int seasonLimit = 4; // The number of seasons to be stored

	/***
	 * Creates a rocker object with the specified name and an empty list of
	 * invalid recipients Note the name cannot be changed later, if you wish to
	 * change the name you must create a new object
	 * 
	 * @param name
	 *            - String name of rocker
	 */
	public Rocker(String name) {
		this.name = name;
		recipient = null;
		invalidRecipients = new LinkedList<Rocker>();
		validRecipients = new LinkedList<Rocker>();
	}

	/**
	 * Allows addition of invalid recipient, if the addition of this person
	 * makes the invalid recipient list exceed the limit of seasons the oldest
	 * invalid person will be removed
	 * 
	 * @param r
	 *            - Rocker who this rocker cannot buy for
	 */
	public void addInvalidRecipient(Rocker r) {
		if (r == null) {
			throw new IllegalArgumentException("Invalid Recipient cannot be null");
		}

		invalidRecipients.add(r);
		validRecipients.remove(r);

		if (invalidRecipients.size() > seasonLimit) {
			Rocker noLongerInvalid = invalidRecipients.remove();
			addValidRecipient(noLongerInvalid);
		}
	}

	public void addValidRecipient(Rocker r) {
		if (r == null) {
			throw new IllegalArgumentException("Valid Recipient cannot be null");
		}
		if (invalidRecipients.contains(r)) {
			throw new IllegalArgumentException("Recipient currenly invalid, not possible to be both valid and invalid");
		}
		validRecipients.add(r);
	}

	/***
	 * Updates the list of invalid recipients with the most recent recipient
	 * from this rocker Sets the current recipient to null
	 */
	public void storeAndResetRecipient() {
		addInvalidRecipient(recipient);
		resetRecipient();
	}

	/***
	 * Simply sets the recipient to null, does not store the data
	 */
	public void resetRecipient() {
		recipient = null;
	}

	/***
	 * Verifies that rocker r is an acceptable recipient of a gift from this
	 * rocker
	 * 
	 * @param r
	 *            Rocker who this rocker might give to
	 * @return true if valid, false otherwise
	 */
	public boolean isRecipientValid(Rocker r) {
		if (r == null)
			return false;
		boolean notInvalid = !invalidRecipients.contains(r);
		boolean notSelf = (r != this);
		boolean isValid = validRecipients.contains(r);
		return notInvalid && notSelf && isValid;
	}

	/***
	 * @return name of this rocker
	 */
	public String getName() {
		return name;
	}

	/***
	 * @return the current recipient of this rocker, null if one is set
	 */
	public Rocker getRecipient() {
		return recipient;
	}

	/***
	 * Sets the recipient of this rocker to rocker R. Must check that this is
	 * valid before calling this method
	 * 
	 * @param r
	 *            Rocker for whom this Rocker will buy
	 */
	public void setRecipient(Rocker r) {
		if (recipient != null) {
			throw new IllegalStateException("The rocker already has a recpient for this season.");
		} else if (this.isRecipientValid(r)) {
			recipient = r;
		} else {
			throw new IllegalArgumentException(
					"This is not a valid recipient for this rocker, check validity before calling this method.");
		}
	}

	public String toString() {
		if (recipient != null)
			return name + RockerInfoManager.buyingText + recipient.name;
		return name;
	}

	public List<Rocker> getValidRecipients() {
		return validRecipients;
	}

	public List<Rocker> getEdges() {
		return getValidRecipients();
	}

}
