package simplePairingGenerator;

public class RockerInfoManager {

	// This is kind of a silly class at the moment. My thought is if there needs
	// to be a more sophisticated way to get the names or gets the last year
	// info
	// that can all be done in this file.
	public static String[] rockerList = { "Buddy Holly", // 0
			"John Bonham", // 1
			"Elvis Presley", // 2
			"Kurt Cobain", // 3
			"Janis Joplin", // 4
			"Ritchie Valens", // 5
			"John Lennon" };// 6
	// TODO: write function to generate this array
	// index is giver, value is receiver the numbers correspond to the order in
	// the rockerlist
	public static int[] lastYearPairById = { 2, 3, 4, 5, 6, 0, 1 };
	public static String buyingText = " is buying for ";

}
