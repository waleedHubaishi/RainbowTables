import java.math.BigInteger;

public class Conversion {

    /**
     * This method calculates decimal value of a given Hex number.
     *
     * @param hash to calculate the integer value of.
     * @return BigInteger containing the decimal value of the Hex number entered.
     */
    public BigInteger calculateIntegerValueOfHexa(String hash){
        return new BigInteger(hash, 16);
    }
}
