import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {


    /**
     * This method returns the hash value of a given text based on Message Digest Algorith MD5.
     *
     * @param input that contains the text to calculate the hash value for.
     * @return hashValue of the input text.
     * @throws NoSuchAlgorithmException which needed in case the algorithm used for the hash function is not found.
     */
    public String calculateHash(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes(),0,input.length());
        String hashValue = new BigInteger(1,md.digest()).toString(16);

        return hashValue;
    }
}
