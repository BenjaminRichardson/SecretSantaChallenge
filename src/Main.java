import java.util.*;

import simplePairingGenerator.*;
import multiSeasonPairingGenerator.MultiplePairingGenerator;

public class Main {

	public static void main(String args[]) {

		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("Enter 1 for simple generator");
			System.out.println("Enter 2 for multi-season generator");

			switch (scanner.nextLine()) {

			case "1":
				simpleMain();
				running = false;
				break;
			case "2":
				mulitMain(scanner);
				running = false;
				break;
			default:
				System.out.println("command not recognized");
				break;
			}
		}
		scanner.close();
	}

	public static void simpleMain() {
		System.out.println(SimpleGenerator.generatePairing());
	}

	public static void mulitMain(Scanner scanner) {
		boolean running = true;
		MultiplePairingGenerator mg = new MultiplePairingGenerator(RockerInfoManager.rockerList,
				RockerInfoManager.lastYearPairById);
		System.out.println("Press enter to generate a new pairing, press 'q' to quit");
		while (running) {
			switch (scanner.nextLine()) {

			case "q":
			case "Q":
				running = false;
				break;

			default:
				System.out.println(mg.generateNextPairing());
				break;
			}

		}
	}

}
