public class DesCipher {
    private final int MAX_8_LENGTH = 8;
    final int[] PC1 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
    final int[] PC2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    final int[] nShift = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    final int[] IP = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
    final int[] expansionPermutation = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};
    final int[][][] sBoxes = {{{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7}, {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8}, {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0}, {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}}, {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10}, {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5}, {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15}, {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}}, {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8}, {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1}, {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7}, {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}}, {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15}, {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9}, {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4}, {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}}, {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9}, {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6}, {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14}, {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}}, {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11}, {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8}, {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6}, {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}}, {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1}, {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6}, {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2}, {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}}, {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7}, {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2}, {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8}, {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};
    final int[] simplePermutation = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};
    final int[] inversePermutation = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};

    private  String key;
    private boolean logging = true;

    public DesCipher() {
    }

    public DesCipher(boolean logging) {
        this.logging = logging;
    }

    public String encrypt(String message, String key){
        print("--- Encryption ---");
        print("\nMessage to be encrypted: " + message);
        if(isKeyWrong(key)) throw new IllegalArgumentException("Key length should be less than or equal to 8");
        print("Using key: " + this.key);
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i = i + MAX_8_LENGTH) {
            String block;
            /* if the message is less than 8, to avoid index out of bound exception*/
            if(i+MAX_8_LENGTH > message.length()){
                block = message.substring(i);
            } else {
                block = message.substring(i, i + MAX_8_LENGTH);
            }
            print("\nCurrent message block: " + block);
            String encryptedBlock = encryptBlock(block);
            print("\n" + block + " -> " + encryptedBlock);
            encryptedMessage.append(encryptedBlock);
        }
        return encryptedMessage.toString();
    }

    public String decrypt(String message, String key){
        if(isKeyWrong(key)) throw new IllegalArgumentException("Key length should be less than or equal to 8");
        print("\n\n--- Decrypting ---");
        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i = i + 16) { // because encrypted message is hex
            String hexBlock = message.substring(i, i+16);
            print("\nCurrent block: " + hexBlock);
            String decryptedBlock = decryptBlock(hexBlock);
            decryptedMessage.append(decryptedBlock);
        }
        return decryptedMessage.toString();
    }

    private String encryptBlock(String block) {
        String binaryMessage = getBinaryString(block);

        /*For testing only*/
        //String binaryKey = "0001001100110100010101110111100110011011101111001101111111110001";
        //String binaryMessage = "0000000100100011010001010110011110001001101010111100110111101111";

        /*String binaryMessage = getBinaryString(block);*/
        String binaryKey = getBinaryString(key);

        /*Generating key array. */
        String[] key16Array = getKeyArray(binaryKey);
        /* Performing initial permutation*/
        String afterIP = selectByPermutation(binaryMessage, IP);
        print("After initial permutation: " + binaryToHex(afterIP));
        /* encrypt for 16 rounds */
        String encryptedMessage = afterIP;
        for (int i = 0; i < 16; i++) {
            if(logging) System.out.print("Round " + (i+1) + " => "); // print current round if logging is true.
            encryptedMessage = performRound(encryptedMessage, key16Array[i]);
        }

        /*swapping two halves of encrypted message*/
        String leftHalf = encryptedMessage.substring(0, encryptedMessage.length() / 2);
        String rightHalf = encryptedMessage.substring(encryptedMessage.length() / 2);
        String afterSwap = rightHalf + leftHalf;
        print("\nAfter last swap: " + binaryToHex(afterSwap));
        /* performing inverse permutation*/
        encryptedMessage = selectByPermutation(afterSwap, inversePermutation);
        print("After performing inverse permutation: " + binaryToHex(encryptedMessage));
        String hexMessage = binaryToHex(encryptedMessage).toUpperCase();
        return hexMessage;
    }


    private String decryptBlock(String encryptedMessage) {
        String binaryMessage = hexToBinary(encryptedMessage);
        String binaryKey = getBinaryString(key);

        String[] key16Array = getKeyArray(binaryKey);

        /* performing initial permutation */
        String afterIp = selectByPermutation(binaryMessage, IP);
        print("After initial permutation: " + binaryToHex(afterIp));

        String decryptedMessage = afterIp;
        /* decrypting 16 rounds */
        int j = 0;
        for (int i = 15; i >= 0; i--) {
            if(logging) System.out.print("Round " + (j++) + " => "); // print current round if logging is true
            decryptedMessage = performRound(decryptedMessage, key16Array[i]);
        }

        /* swapping two halves with each other*/

        String leftHalf = decryptedMessage.substring(0, decryptedMessage.length() / 2);
        String rightHalf = decryptedMessage.substring(decryptedMessage.length() / 2);

        String afterSwap = rightHalf + leftHalf;
        print("\nAfter last swap: " + binaryToHex(afterSwap));

        /* performing inverse permutation */
        decryptedMessage = selectByPermutation(afterSwap, inversePermutation);
        print("After performing inverse permutation: " + binaryToHex(decryptedMessage));

        String decryptedHex = binaryToHex(decryptedMessage);

        return hexToString(decryptedHex);
    }


    private  String hexToString(String hexMessage) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexMessage.length(); i = i + 2) {
            String hexBlock = hexMessage.substring(i, i + 2);
            char c = (char) Integer.parseInt(hexBlock, 16);
            sb.append(c);
        }
        return sb.toString();
    }

    private  String performRound(String message, String key) {
        String left = message.substring(0, message.length() / 2);
        String right = message.substring(message.length() / 2);
        String rightSafeKeep = right;

        print("Left: " + binaryToHex(left) + " | Right: " + binaryToHex(right) + " | Key: " + binaryToHex(key));
        /*Expanding 32 bit right half of message to 48 bit */
        right = selectByPermutation(right, expansionPermutation);

        /* performing XOR of right half and key[round] */
        right = performXOR(right, key);

        /* performing sBox compression 48 bit -> 32 bit*/
        right = sBoxCompression(right);

        /* performing simple permutation*/
        right = selectByPermutation(right, simplePermutation);

        left = performXOR(left, right);


        return rightSafeKeep + left;
    }

    private  String sBoxCompression(String right) {

        /* input block length is 6*/
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < right.length(); i = i + 6) {
            /*Selecting appropriate sBox based on the block number*/
            int[][] sBox = sBoxes[i / 6];
            /* slicing a block*/
            String block = right.substring(i, i + 6);
            /*taking first and last bit as row */
            int row = Integer.parseInt(block.charAt(0) + "" + block.charAt(5), 2);
            /* taking middle 4 bits as col*/
            int col = Integer.parseInt(block.substring(1, 5), 2);

            /*getting number from s box */
            int number = sBox[row][col];
            /*converting number to binary string*/
            String binaryString = Integer.toBinaryString(number);
            while (binaryString.length() < 4) binaryString = "0" + binaryString;
            sb.append(binaryString);
        }

        return sb.toString();
    }

    private  String performXOR(String right, String key) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < right.length(); i++) {
            if (right.charAt(i) == key.charAt(i)) {
                res.append("0");
            } else {
                res.append("1");
            }
        }
        return res.toString();
    }

    private  String[] getKeyArray(String binaryKey) {

        // selecting 56 bits according to PC1
        String sb = selectByPermutation(binaryKey, PC1);

        String c = sb.substring(0, 28);
        String d = sb.substring(28, 56);
        String[] keyArray = new String[16];
        /* Left shifting both blocks */
        for (int i = 0; i < nShift.length; i++) {
            //if(i == 0 ) continue;
            c = leftBitShift(c, nShift[i]);
            d = leftBitShift(d, nShift[i]);
            String temp = c + d;
            keyArray[i] = selectByPermutation(temp, PC2);
        }

        return keyArray;
    }

    private  String selectByPermutation(String binaryMessage, int[] permutation) {
        /*universal function to select characters based on permutation array provided*/
        StringBuilder sb = new StringBuilder();
        for (int i : permutation) {
            sb.append(binaryMessage.charAt(i - 1));
        }
        return sb.toString();
    }


    private  String leftBitShift(String key, int bit) {
        return key.substring(bit) + key.substring(0, bit);
        /*int number = Integer.parseInt(key, 2); // binary to integer
        System.out.println(4039944944 >= 2147483647);
        int res = number << bit;
        return Integer.toBinaryString(res);*/
    }

    private  String getBinaryString(String message) {
        var sb = new StringBuilder();
        char[] characters = message.toCharArray();
        for (char character : characters) {
            String temp = Integer.toBinaryString(character);
            temp = "0".repeat(MAX_8_LENGTH - temp.length()) + temp;
            sb.append(temp);
        }

        while (sb.length() != 64) {
            sb.insert(0, "0"); // make it 64 bit long // add padding of 0
        }
        return new String(sb);
    }

    private  String binaryToHex(String binaryMessage) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < binaryMessage.length(); i = i + 8) { /* get 8 blocks */
            String binaryBlock = binaryMessage.substring(i, i + 8);
            int temp = Integer.parseInt(binaryBlock, 2);
            String hex = Integer.toHexString(temp);
            while (hex.length() < 2) hex = "0" + hex; // if the hex is single letter append 0 to it.
            sb.append(hex);
        }
        return sb.toString();
    }

    private  String hexToBinary(String hexMessage) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < hexMessage.length(); i = i + 2) {
            String hexBlock = hexMessage.substring(i, i + 2);
            int number = Integer.parseInt(hexBlock, 16);
            String binaryBlock = Integer.toBinaryString(number);
            while (binaryBlock.length() < 8)
                binaryBlock = "0" + binaryBlock; // if the binary block is less than 8 bit long.
            sb.append(binaryBlock);
        }
        return sb.toString();
    }


    private  void print(String message) {
        if(!logging) return;
        System.out.println(message);
    }



    private boolean isKeyWrong(String key) {

        if (key.length() > 8) {
            /* key length more than 8*/
            return true;
        }
        this.key = key;
        return false;
    }

    private  String preprocessing(String message) {
        int diff = MAX_8_LENGTH - message.length() % MAX_8_LENGTH;
        if (diff == MAX_8_LENGTH) return message;
        return message + "x".repeat(diff);
    }
}