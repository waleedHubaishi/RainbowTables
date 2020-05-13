import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Rainbow {


    MD5 md = new MD5();
    Reduction reduction = new Reduction();

    int lengthOfHorizontalChain;
    int lengthOfVerticalChain;
    String startText;

    public void setRainBowTable(String[][] rainBowTable) {
        this.rainBowTable = rainBowTable;
    }

    String[][] rainBowTable;


    public Rainbow(String startText, int lengthOfChainVertically, int lengthOfChainHoriziontaly) throws NoSuchAlgorithmException{

        this.lengthOfHorizontalChain = lengthOfChainHoriziontaly;
        this.lengthOfVerticalChain = lengthOfChainVertically;
        this.startText = startText;

        rainBowTable = new String[lengthOfVerticalChain][2];

        //only if you wish to construct the table and not load it, remove the setter from main in this case and uncomment
        //the following line of code, we load the rainbow table to save the construction time.
        //setRainBowTable(constructRainbowTable());

    }

    /**
     * This method constructs the rainbow table.
     *
     * @return rainBowTable which is the table that contains the first password in the chain and the last password.
     * @throws NoSuchAlgorithmException which needed in case the algorithm used for the hash function is not found.
     */
    public String[][] constructRainbowTable() throws NoSuchAlgorithmException{
        System.out.println("Constructing Rainbow table, please wait ...");

        //create the list of passwords starting 0000000, 0000001 ...
        ArrayList<String> passwordsList = constructTheChainVertically(startText);

        for(int i=0; i<passwordsList.size();i++){

            //save each password of passwordList as a starter of a chain.
            rainBowTable[i][0] = passwordsList.get(i);

            //calculate the 2000 chain that starts with the previously saved password.
            rainBowTable[i][1] = constructTheChainHoriziontaly(passwordsList.get(i))[0][1];

        }

        System.out.println("Construction of Rainbow table done.");


        return rainBowTable;
    }

    /**
     * This method constructs the chain(kette) of the specified given length for one password.
     *
     * @param password at the start of the chain.
     * @return chain which is an array containing the first and the last element of a chain, both are passwords.
     * @throws NoSuchAlgorithmException which needed in case the algorithm used for the hash function is not found.
     */
    public String[][] constructTheChainHoriziontaly(String password) throws NoSuchAlgorithmException {

        String [][] chain = new String[1][2];
        String hashvVlue = "";

        for(int i=0; i<lengthOfHorizontalChain;i++){

            if(i == 0){
                //set the start of the chain.
                chain[0][0] = password;
            }

            //You reached the end of the chain so that it contains 2000 records.
            if(i == lengthOfHorizontalChain-1){

                //save the end of the chain.
                chain[0][1] = password;
                break;
            }

            //while you are not on the start or the end of the chain, keep calculating.
            hashvVlue = md.calculateHash(password);
            password = reduction.calculateNextStringFromHash(hashvVlue,i);

        }

        return chain;
    }


    /**
     * This method constructs an Arraylist filled of passwords, 0000000 0000001 0000002 ...
     * because we need only 2000 passwords, only the last three digits will be filled in this case
     *
     * @param initialPassword to start the list with.
     * @return passwordsList of all passwords that were constructed.
     */
    public ArrayList<String> constructTheChainVertically(String initialPassword){

        //one at a time in a character array, easier this way to choose which digit to increment
        char [] charactersOfString = initialPassword.toCharArray();
        int counter = 0;

        //list of all passwords
        ArrayList<String> passwordsList = new ArrayList<>();

            outerloop:
            for(int i=0;i<reduction.getZ().length;i++){
                //change the third digit before the last
                charactersOfString[initialPassword.length()-3] = reduction.getZ()[i];

                for(int u = 0; u<reduction.getZ().length;u++) {

                    //change the second digit before the last
                    charactersOfString[initialPassword.length() - 2] = reduction.getZ()[u];

                    for (int p = 0; p < reduction.getZ().length; p++) {

                        //make sure not to go after 2000 passwords
                        if(counter < lengthOfVerticalChain){

                            //change the last digit
                            charactersOfString[initialPassword.length() - 1] = reduction.getZ()[p];

                            //add the password to the List
                            passwordsList.add(new String(charactersOfString));

                            //keep counting how many passwords were added to the list
                            counter++;
                        }
                        else{
                            break outerloop;
                        }
                    }

                }

            }

         return passwordsList;
    }


    /**
     * This method constructs an Arraylist that contains the hash value followed by the text of reduction function given a Stufe as parameter.
     *
     * @param hash to search for.
     * @param Stufe to start looking from, the Stufe to start looking for the hash from.
     * @return chain that contains the hash values and the text ordered as they are on the chain.
     * @throws NoSuchAlgorithmException which needed in case the algorithm used for the hash function is not found.
     */
    public ArrayList<String> constructTheChainHoriziontalyFromGivenStufe(String hash, int Stufe) throws NoSuchAlgorithmException {

        ArrayList<String> chain = new ArrayList<>();

        //start from the given Stufe till it reaches the end
        for(int i=Stufe; i<lengthOfHorizontalChain-1;i++){

            //add the hash followed by the text
            chain.add(hash);
            chain.add(reduction.calculateNextStringFromHash(hash,i));

            //update the hash value for the next round
            hash = md.calculateHash(reduction.calculateNextStringFromHash(hash,i));
        }

        return chain;
    }

    /**
     * This method checks if the hash is included in the constructed rainbow table and doesnt return any value but prints out in which chain is it located.
     *
     * @param hash to search for.
     * @throws NoSuchAlgorithmException which needed in case the algorithm used for the hash function is not found.
     */
    public void checkIfHashIsInRainbowTable(String hash) throws NoSuchAlgorithmException{

        outloop:

        //As Rainbow table rule said, start from the last Stufe, if hash not found then go one back
        for(int stufe=lengthOfHorizontalChain-2; stufe >= 0 ; stufe--){

            //apply the rules to all chains saved in the rainbow table
            for(int i=0; i<rainBowTable.length;i++){

                //construct the chain for from the Stufe that we are on now
                ArrayList chain = constructTheChainHoriziontalyFromGivenStufe(hash,stufe);

                // if the last password on the constructed chain matches on of the end passwords of the rainbow tables chains, then we have found the chain where the hash is located
                if(chain.get(chain.size()-1).equals(rainBowTable[i][1])){

                    //print out the chain, the text after applying the reduction function and also the Stufe that we are in
                    //the hash you are looking for was found in this chain which starts with 00000rs and ends with 7ezfpbe
                    //the corresponding text is ici2gyb
                    //The Stufe is 1601

                    System.out.println("the hash you are looking for was found in the chain that starts with "+rainBowTable[i][0]+" and ends with "+rainBowTable[i][1]);
                    System.out.println("the corresponding text is "+reduction.calculateNextStringFromHash(hash,stufe));
                    System.out.println("The Stufe is "+stufe);
                    break outloop;
                }
            }
        }

    }


}

