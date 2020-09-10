import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String message = input("Enter a message: ");
		String key = input("Enter a key: ");

		DesEncryptor des = new DesEncryptor(false);

		String encryptedMessage = des.encrypt(message, key);
		print("\nEncrypted Message: " + encryptedMessage);

		String decryptedMessage = des.decrypt(encryptedMessage, key);
		print("\nDecrypted Message: " + decryptedMessage);
	}

	private static void print(String message) {
		System.out.println(message);
	}

	public static String input(String message){
		Scanner myObj = new Scanner(System.in);
		System.out.print(message);
		return myObj.nextLine();
	}

}