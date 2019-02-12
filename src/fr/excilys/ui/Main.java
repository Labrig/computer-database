package fr.excilys.ui;

import java.util.Scanner;

import fr.excilys.business.Commande;
import fr.excilys.exceptions.NotCommandeException;

public class Main {

	public static void main(String[] args) {
		boolean isRunning = true;
		Scanner sc = new Scanner(System.in);
		while(isRunning) {
		    try {
		    	System.out.println("/h <object> if you need help");
				String result = Commande.execute(sc.nextLine());
				System.out.println(result);
			} catch (NotCommandeException e) {
				System.out.println(e.getMessage());
			}
		    System.out.println("\n");
		}
	}

}
