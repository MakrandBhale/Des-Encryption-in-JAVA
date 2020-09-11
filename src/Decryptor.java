import java.io.IOException;

public class Decryptor {
    public static void main(String[] args) {
        DesCipher cipher = new DesCipher();
        SocketCom socketCom = new SocketCom();
        try {
            MessagePacket messagePacket = socketCom.getMessage();
            System.out.println("Encrypted Message: " + messagePacket.message);
            String decryptedMessage = cipher.decrypt(messagePacket.message, messagePacket.key);
            System.out.println("Decrypted Message: " + decryptedMessage);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
