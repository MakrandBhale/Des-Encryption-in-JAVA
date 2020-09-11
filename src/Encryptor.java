import java.io.IOException;
import java.util.Scanner;

public class Encryptor {
	public static void main(String[] args) {
		String message = input("Enter a message: ");
		String key = input("Enter a key: ");
		SocketCom socketCom = new SocketCom();
		DesCipher des = new DesCipher(true);

		String encryptedMessage = des.encrypt(message, key);
		print("Encrypted Message: " + encryptedMessage);
		try {
			socketCom.sendMessage(new MessagePacket(encryptedMessage, key));
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
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