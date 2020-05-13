import java.math.BigInteger;

public class Reduction {

    final char [] Z =  {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    final int L = 7;

    Conversion conversion = new Conversion();


    /**
     * This method calculates the next String on the chain from a given hash value given the rules set on 3.27.
     *
     * @param hash the hash value to calculate the next string for.
     * @return newTextOnTheChain is the text that was calculated from the hash value given.
     */
    public String calculateNextStringFromHash(String hash, int stufe){

        String finalResult = "";

        //convert the hash to its integer value (BigInteger) to calculate the mod.
        BigInteger hashInBigInt = conversion.calculateIntegerValueOfHexa(hash).add(BigInteger.valueOf(stufe));
        for(int i=0;i<L;i++){

            BigInteger sizeOfArray = BigInteger.valueOf(Z.length);
            BigInteger newIndexToAdd = BigInteger.ZERO;

            //calculate the index
            newIndexToAdd = hashInBigInt.mod(sizeOfArray);

            //add the character of that index into the string
            finalResult += Z[newIndexToAdd.intValue()];

            //calculate the new H
            BigInteger newH = hashInBigInt.divide(sizeOfArray);

            //update the hashInBigInt value to be used in the next round
            hashInBigInt = newH;

        }

        //reverse the string
        String newTextOnTheChain = reverseTheString(finalResult);

        return newTextOnTheChain;
    }


    /**
     * This method reverses any given text input.
     *
     * @param input the text that should be reversed.
     * @return reversedResult is the text entered reversed.
     */
    public String reverseTheString(String input){

        String reversedResult = "";
        for(int i = input.length() - 1; i >= 0; i--)
        {
            reversedResult += input.charAt(i);
        }

        return  reversedResult;

    }

    public char[] getZ() {
        return Z;
    }

    public int getL() {
        return L;
    }
}
