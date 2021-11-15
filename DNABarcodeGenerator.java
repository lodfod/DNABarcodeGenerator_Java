import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;


public class DNABarcodeGenerator {
    // data
    static int n;
    static int l;
    
    // generates and returns a random DNA barcode sequence as a String
    static String generateBarcode() {
       
        char[] charCode = new char[l];

        for(int i = 0; i < l; i++){
            double g = (Math.random() * 4.0);
            if(g < 1) {
                charCode[i] = 'A';
            } else if(g < 2) {
                charCode[i] = 'G';
            } else if(g < 3) {
                charCode[i] = 'T';
            } else {
                charCode[i] = 'C';
            }
        }

        String barcode = new String(charCode);
        
        return barcode;
    }

    // checks if a given barcode is on the list of restricted sequences, returns true if in restricted range, false if not in restricted range
    static boolean isRestrictedList(String barcode) {
        System.out.println(barcode);
        String[] restrictedList = {"ACCGGT", "GGCGCGCC", "GGATCC", " CCTGCAGG"};
        for(int i = 0; i < restrictedList.length; i++){
            if(barcode.equals(restrictedList[i])) {
                //System.out.println("is on restricted list of barcodes: ");
                return true;
            }
        }

        return false;
    }

    // checks whether a given barcode is between 40% and 60% GC value, returns true if within range, false if not within range
    static boolean validateGCCount(String barcode){

        // creates hashmap to map count of each base (as represented by their char value)
        HashMap<Character,Integer> codeMap = new HashMap<Character, Integer>();
        codeMap.put('A', 0);
        codeMap.put('G', 0);
        codeMap.put('T', 0);
        codeMap.put('C', 0);

        char[] codeCharArr = barcode.toCharArray();
        
        // updates hashmap with count of each base 
        for(int i = 0; i < codeCharArr.length; i++) {
            if(codeCharArr[i] == 'A')
                codeMap.put('A', codeMap.get('A') + 1);
            if(codeCharArr[i] == 'G')
                codeMap.put('G', codeMap.get('G') + 1);
            if(codeCharArr[i] == 'T')
                codeMap.put('T', codeMap.get('T') + 1);
            if(codeCharArr[i] == 'C')
                codeMap.put('C', codeMap.get('C') + 1);
        }

        // calculate G-C content as a decimal based on values from hashmap
        double gcVal = ((double) (codeMap.get('G') + codeMap.get('C'))) / ((double)(codeMap.get('G') + codeMap.get('C') + codeMap.get('A') + codeMap.get('T')));

        if(gcVal >= 0.4 && gcVal <= 0.6){
            return true;
        }
       
        return false;
    }

    // checks whether barcode has 3 or more of a certain base consecutively, returns true if so, false if not
    static boolean isRedundantBarcode(String barcode) {
       
        for(int i = 0; i < barcode.length() - 2; i++){
            if(barcode.charAt(i) == barcode.charAt(i+1) && barcode.charAt(i) == barcode.charAt(i+2)){
                //System.out.println("redundant: "+ barcode);
                return true;
            }
        }
        return false;

    }

    // checks whether generated code is new or not, returns true if code is unique, false if it has been generated before in the same batch
    static boolean newCode(String currentBarcode, ArrayList<String> existingCodes){
        for(int i = 0; i < existingCodes.size(); i++){
            if(existingCodes.get(i).equals(currentBarcode)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        // Name: Archish Arun
        // NetID: ada8211

        // get n and l values from user
        Scanner input = new Scanner(System.in);
        System.out.println("How many sequences of DNA barcodes would you like to generate?");
        n = input.nextInt();
        System.out.println("What is the length of each DNA barcode?");
        l = input.nextInt();

        // create ArrayList to store codes and check whether newly generated code is unique or not (see newCode function)
        ArrayList<String> existingCodes = new ArrayList<String>();

        // keeps generating codes until number is reached
        int i = 0;
        while(i<n){
            String currentBarcode = generateBarcode();

            // pass newly generated code through all functions and check validity:
            // isRedundant == false
            // validateGCCount == true
            // newCode == true

            if(!(isRedundantBarcode(currentBarcode)) && validateGCCount(currentBarcode) && (newCode(currentBarcode, existingCodes))){
                // since code is valid, add to arraylist of valid codes existingCodes
                existingCodes.add(i, currentBarcode);

                // only increment number of codes if new code is unique
                i+=1;

            } else {
                // generate new code without adding to list of valid codes
                currentBarcode = generateBarcode();
            }
            
        }

        // prints all validated codes
        for(int j = 0; j < existingCodes.size(); j++) {
            System.out.println("barcode"+String.valueOf(j+1) + ": "+ existingCodes.get(j));
        }

        input.close();

    }
}
