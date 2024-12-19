package com.company;

import java.io.*;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;
//1.read the input file
//2.remove lines and comments to generate intermediate.txt
/*
 * here the problem is we need to get rid of the line numbers and the comments
 * we can do this by checking if there is an integer discard it
 * for comments we can say that we only scan the first 4 columns separated by spaces only and discard the fifth column which is comments
 * now get all this modifications in a string and write it into an intermediate file
 * */
//3.read the intermediate file to produce 2 things : location counter and symbol table
/*
 * now we will add column in the first of the file the location counter
 * to do it we will need to store the start of a program in a variable and start with it
 * the start of the program is in the first line and third column of the intermediate file
 * now start the location counter from second line of the program
 * each line we will check if it is an format 3 instruction or is it format one
 * ofc we will need to store the format 3 instructions in an array and format 1 aswell
 * we can also do an array of lables and array of menemonics to check what each line has(format 3 or 1)
 * after we have to loop through each line to check if it has a lable or no if it has get its location counter
 * and put it to the corresponding lable in symbol table
 * */
//4.read the intermediate again to generate object code for each line of program and HTE record
/*
 * to generate object code we will need to use symbol table and we need the opcode of each instruction will be 7 bits and
 * 1 bit to be determined if it is an immediate or no
 *hte record will be easy to generate now */

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here

        //read file and stor the info in a string
        BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\in.txt"));

        Scanner scan = new Scanner(file);
        String Clearprogram = "";
        //scan the first line to get the starting value of the program
        String[] Firstline = scan.nextLine().split("\\s+");
        //put the first line 3almashy keda 3shan hy7sl bug lw m3mltosh
        Clearprogram = Clearprogram.concat(Firstline[1] + "\t" + Firstline[2] + "\t" + Firstline[3] + "\t" + "\n");
        //secure the starting value in a variable
        Integer startDecimal = Integer.parseInt(Firstline[3], 16);
        //secure the ending value in a variable
        String ENDLocation = "";
        // System.out.println(startDecimal);


        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split("\\s+");
            if (line[1].equals("--"))
                Clearprogram = Clearprogram.concat(line[1] + "\t\t" + line[2] + "\t" + line[3] + "\t" + "\n");
            else
                Clearprogram = Clearprogram.concat(line[1] + "\t" + line[2] + "\t" + line[3] + "\t" + "\n");

        }
        //write this string into another file
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\intermediate.txt"));

        writer.write(Clearprogram);
        writer.close();
        //STORING FORMAT 3 INSTRUCTIONS
        Hashtable<String, Integer> format3 = new Hashtable<String, Integer>();
        format3.put("ADD", 0x18);
        format3.put("AND", 0x40);
        format3.put("COMP", 0x28);
        format3.put("DIV", 0x24);
        format3.put("J", 0x3C);
        format3.put("JEQ", 0x30);
        format3.put("JGT", 0x34);
        format3.put("JLT", 0x38);
        format3.put("LDA", 0x00);
        format3.put("LDCH", 0x50);
        format3.put("LDL", 0x08);
        format3.put("LDX", 0x04);
        format3.put("MUL", 0x20);
        format3.put("OR", 0X44);
        format3.put("RD", 0xD8);
        format3.put("RSUB", 0x4C);
        format3.put("STA", 0x0C);
        format3.put("STCH", 0x54);
        format3.put("STL", 0x14);
        format3.put("STSW", 0xE8);
        format3.put("STX", 0x10);
        format3.put("SUB", 0x1C);
        format3.put("TD", 0xE0);
        format3.put("TIX", 0x2C);
        format3.put("JSUB", 0x48);
        format3.put("WD", 0xDC);
        //STORING FORMAT 1 INSTRUCTIONS
        Hashtable<String, Integer> format1 = new Hashtable<String, Integer>();
        format1.put("FIX", 0xC4);
        format1.put("NORM", 0xC8);
        format1.put("FLOAT", 0xC0);
        format1.put("SIO", 0xF0);
        format1.put("HIO", 0xF4);
        format1.put("TIO", 0xF8);
        //READ FROM THE INTERMEDIATE FILE
        BufferedReader intermediate = new BufferedReader(new FileReader("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\intermediate.txt"));
        //SCAN THE INTERMEDIATE FILE
        Scanner scan2 = new Scanner(intermediate);
        //STRING PASSONE HAS THE LOCATION COUNTER MODIFICATION
        String passOne = "";
        //STRING PASS TWO WILL BE ADDED A COLUMN AT THE END FOR THE OBJECT CODE
        String passTwo = "";
        //JUST WRITE IN OUTPASS1 THE FIRST LINE FIRST AS IT DOESNT REQUIRE A COUNTER
        String[] FirstlineInter = scan2.nextLine().split("\\s+");
        passOne = passOne.concat("\t\t" + FirstlineInter[0] + "\t" + FirstlineInter[1] + "\t" + FirstlineInter[2] + "\t" + "\n");
        //VARIABLE CURRENT LOCATION INDICATES THE MOST RECENT LOCATION FOR INTITIAL VALUE IT HAS THE START VALUE
        //the location in decima,l
        Integer currentLocation = startDecimal;
        //CURRENT LOCATION STRING IS THE CURRENT LOCATION BUT IN HEXA FORM
        String currentLocationString = "";
        //String for the symbol table text
        String symbTable = "";

        while (scan2.hasNextLine()) {
            //THE CURRENT LINE CODE
            String[] line = scan2.nextLine().split("\\s+");
            if (format3.containsKey(line[1])) {
                //CONVERT THE CURRENT LOCATION IN DECIMAL TO HEXA FORM
                currentLocationString = Integer.toHexString(currentLocation);
                //WRITE THE CURRENT LOCATION STRING + THE REST OF THE LINE
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
                //CHECK IF LABLE OR NOT TO ADD IT IN SYMBOL TABLE WITH ITS CURRENT ADDRESS
                if (!(line[0].equals("--"))) {
                    symbTable = symbTable.concat(line[0] + "\t\t" + currentLocationString + "\n");
                }

                //INCREMENT THE LOCATION BY 3 AS IT IS FORMAT 3
                currentLocation = currentLocation + 3;
            } else if (format1.containsKey(line[1])) {
                //CONVERT THE CURRENT LOCATION IN DECIMAL TO HEXA FORM
                currentLocationString = Integer.toHexString(currentLocation);
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
                //CHECK IF LABLE OR NOT TO ADD IT IN SYMBOL TABLE WITH ITS CURRENT ADDRESS
                if (!(line[0].equals("--"))) {
                    symbTable = symbTable.concat(line[0] + "\t\t" + currentLocationString + "\n");
                }
                //INCREMENT THE LOCATION BY 1 AS IT IS FORMAT 1
                currentLocation = currentLocation + 1;
            } else if (line[1].equals("RESB")) {
                //CONVERT THE CURRENT LOCATION IN DECIMAL TO HEXA FORM
                currentLocationString = Integer.toHexString(currentLocation);
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
                //CHANGE THE VALUE NEXT TO RESB OR RESW TO INTEGER DECIMAL THEN
                //ADD IT TO THE CURRENT LOCATION AS IT IS THE AMOUNT RESERVED IN MEMORY
                Integer x = Integer.parseInt(line[2]);
                //CHECK IF LABLE OR NOT TO ADD IT IN SYMBOL TABLE WITH ITS CURRENT ADDRESS
                if (!(line[0].equals("--"))) {
                    symbTable = symbTable.concat(line[0] + "\t\t" + currentLocationString + "\n");
                }
                //increase the current location by value of x which is the number of bytes needed to be reserved
                currentLocation += x;

            } else if (line[1].equals("RESW")) {
                //CONVERT THE CURRENT LOCATION IN DECIMAL TO HEXA FORM
                currentLocationString = Integer.toHexString(currentLocation);
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
                Integer x = Integer.parseInt(line[2]);
                //CHECK IF LABLE OR NOT TO ADD IT IN SYMBOL TABLE WITH ITS CURRENT ADDRESS
                if (!(line[0].equals("--"))) {
                    symbTable = symbTable.concat(line[0] + "\t\t" + currentLocationString + "\n");
                }
                //increase the current location by value of x which is the number of words so we need to multiply by 3 to be reserved
                //in sic oneword = 3 bytes
                currentLocation += (x * 3);

            } else if (line[1].equals("WORD")) {
                currentLocationString = Integer.toHexString(currentLocation);
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
                //CHECK IF LABLE OR NOT TO ADD IT IN SYMBOL TABLE WITH ITS CURRENT ADDRESS
                if (!(line[0].equals("--"))) {
                    symbTable = symbTable.concat(line[0] + "\t\t" + currentLocationString + "\n");
                }
                //WILL ALWAYS INCREMENT BY 3 BECAUSE IT IS WORD
                currentLocation += 3;

            } else if (line[1].equals("BYTE")) {
                currentLocationString = Integer.toHexString(currentLocation);
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
                if (!(line[0].equals("--"))) {
                    symbTable = symbTable.concat(line[0] + "\t\t" + currentLocationString + "\n");
                }
                //WILL ALWAYS INCREMENT BY 1 BECAUSE IT IS BYTE
                currentLocation += 1;

            } else {
                //this else to put the location counter next to th END
                currentLocationString = Integer.toHexString(currentLocation);
                ENDLocation = currentLocationString;
                passOne = passOne.concat(currentLocationString + "\t" + line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + "\n");
            }
        }

        //System.out.println(passOne);
        //write into the outpass1 file
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\outpass1.txt"));
        //FILE SYMBOL TABLE TO WRITE INSIDE IT
        BufferedWriter writer3 = new BufferedWriter(new FileWriter("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\SymbTable.txt"));
        writer2.write(passOne);
        writer3.write(symbTable);
        writer2.close();
        writer3.close();
        String[] symbolTablelines = symbTable.split("\n");

        Hashtable<String, String> symbolTable = new Hashtable<String, String>();

        for (int i = 0; i < symbolTablelines.length; i++) {
            String[] temp = symbolTablelines[i].split("\\s+");
            symbolTable.put(temp[0], temp[1]);
        }
        //System.out.println(Integer.parseInt(symbolTable.get("FIRST"),16));
        //we already have scan2 scans from the immediate
        intermediate = new BufferedReader(new FileReader("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\intermediate.txt"));
        scan2 = new Scanner(intermediate);

        //make a dummy string to scan the first line of intermediate as it hasnt any instruction only the start location and prog name
        //so we need to start from second line
        String[] dummy = scan2.nextLine().split("\\s+");


        while (scan2.hasNextLine()) {
            String[] line = scan2.nextLine().split("\\s+");
            //CHECK RSUB FIRST BECAUSE ITS A SPECIAL CASE DOESNT CHANGE
            if (line[1].equals("RSUB")) {
                passTwo = passTwo.concat(line[0] + "\t\t" + line[1] + "\t\t" + line[2] + "\t\t" + "4C0000" + "\n");

            }//CHECK IF FORMAT 3 INSTRUCTION
            else if (format3.containsKey(line[1])) {
                //get the first eight bits which they are the opcode in 7 bits + 1 bit for immediate we will assume it is 0 and we check if # exists or not
                Integer firstEight = format3.get(line[1]);

                //declare string for last 15 bits + 1 bit forcomma X
                String last16_bits;
                //integer also for the last 16 bits but we need it in integer so that we can do operation if there is an comma X situation
                Integer addressBits = 0;
                //checking if the operand has the # which indicates we are dealing with immediate (#decimalNumber)
                if (line[2].contains("#")) {
                    //if # exists we need to increment the first eight bits(the bit number 8 is set to 1)
                    firstEight += 1;
                    //and the address is the number next to the # symbol in this case
                    //use substring function  to skip the # symbol
                    addressBits = Integer.parseInt(line[2].substring(1));
                }
                //if the third column (operand column) doesnt contain -- or doesnt contain # then its a label for sure
                if (!(line[2].equals("--")) && !(line[2].contains("#"))) {
                    String label;
                    //if label has comma X
                    if (line[2].contains(",X")) {
                        //split using ,
                        String[] temp = line[2].split(",");
                        //temp 0 has the label name
                        label = temp[0];
                        //search the lable in the hash table and convert the address to decimal value to do some additions then convert it back to hexa later
                        addressBits = Integer.parseInt(symbolTable.get(label), 16);
                        //if we want to set the bit number X to one we must add to the address 8000 in hexadecimal which is equivalent to 32768 in decimal
                        addressBits += 32768;
                    } else {
                        //now we need to get address of the label (last 15 bits + 1 bit for comma X)
                        //get the current label name
                        label = line[2];
                        //search the lable in the hash table and convert the address to decimal value to do some additions then convert it back to hexa later
                        addressBits = Integer.parseInt(symbolTable.get(label), 16);
                    }


                    //System.out.println(Integer.parseInt(x,16));
                    //System.out.println(symbolTable.get(label)+"    "+line[1]);

                }

                //now concatenate the bits the 24-bits together to form object code
                String objectCode = "";
                //check if the opcode is consists of only one digit in this case we need to add "0" to the most significant nibble
                String opcode = Integer.toHexString(firstEight);
                if (opcode.length() == 1) {
                    opcode = "0" + opcode;
                }
                //converting the first eight bits and last 16 bits to hexa and concatenating them together
                objectCode = objectCode.concat(opcode + Integer.toHexString(addressBits));


                passTwo = passTwo.concat(line[0] + "\t\t" + line[1] + "\t\t" + line[2] + "\t\t" + objectCode + "\n");
            } else if (format1.containsKey(line[1])) {
                //get the first eight bits which they are the opcode in 7 bits + 1 bit for immediate we will assume it is 0 and we check if # exists or not
                Integer firstEight = format1.get(line[1]);
                //declare string for last 15 bits + 1 bit for comma X
                String last16_bits;
                //integer also for the last 16 bits but we need it in integer so that we can do operation if there is an comma X situation
                Integer addressBits = 0;
                //checking if the operand has the # which indicates we are dealing with immediate (#decimalNumber)
                if (line[2].contains("#")) {
                    //if # exists we need to increment the first eight bits(the bit number 8 is set to 1)
                    firstEight += 1;
                    //and the address is the number next to the # symbol in this case
                    //use substring function  to skip the # symbol
                    addressBits = Integer.parseInt(line[2].substring(1));
                }
                //if the third column (operand column) doesnt contain -- or doesnt contain # then its a label for sure
                if (!(line[2].equals("--")) && !(line[2].contains("#"))) {
                    String label;
                    //if label has comma X
                    if (line[2].contains(",X")) {
                        //split using ,
                        String[] temp = line[2].split(",");
                        //temp 0 has the label name
                        label = temp[0];
                        //search the lable in the hash table and convert the address to decimal value to do some additions then convert it back to hexa later
                        addressBits = Integer.parseInt(symbolTable.get(label), 16);
                        //if we want to set the bit number X to one we must add to the address 8000 in hexadecimal which is equivalent to 32768 in decimal
                        addressBits += 32768;

                    } else {
                        //now we need to get address of the label (last 15 bits + 1 bit for comma X)
                        //get the current label name
                        label = line[2];
                        //search the lable in the hash table and convert the address to decimal value to do some additions then convert it back to hexa later
                        addressBits = Integer.parseInt(symbolTable.get(label), 16);
                    }


                    //System.out.println(Integer.parseInt(x,16));
                    //System.out.println(symbolTable.get(label)+"    "+line[1]);

                }

                //now concatenate the bits the 24-bits together to form object code
                String objectCode = "";
                //check if the opcode is consists of only one digit in this case we need to add "0" to the most significant nibble
                String opcode = Integer.toHexString(firstEight);
                if (opcode.length() == 1) {
                    opcode = "0" + opcode;
                }
                //converting the first eight bits and last 16 bits to hexa and concatenating them together
                objectCode = objectCode.concat(opcode + Integer.toHexString(addressBits));
                // System.out.println(objectCode+"    "+line[1]);
                passTwo = passTwo.concat(line[0] + "\t\t" + line[1] + "\t\t" + line[2] + "\t\t" + objectCode + "\n");
            } else if (line[1].equals("WORD")) {
                String objectCode = "";
                //value next to "word" in the program changed to hexa
                String wordValue = Integer.toHexString(Integer.parseInt(line[2]));
                //string repeated is used to increase number of zeroes from left side in case we need to increase
                String repeated = new String(new char[6 - wordValue.length()]).replace("\0", "0");
                objectCode = objectCode.concat(repeated + wordValue);

                //take the number next to "WORD" AND CONVERT IT INTO HEXA
                passTwo = passTwo.concat(line[0] + "\t\t" + line[1] + "\t\t" + line[2] + "\t\t" + objectCode + "\n");
            } else if (line[1].equals("BYTE")) {
                String objectCode = "";
                String[] operand = line[2].split("'");
                //check if hexa
                if (operand[0].equals("X")) {
                    //put it as it is
                    objectCode = operand[1];
                } else if (operand[0].equals("C")) {
                    //convert it to character array and pass in a loop each character to the toHexString function to get its hexa ascii value and concatenate each result together
                    char ch[] = operand[1].toCharArray();
                    for (int i = 0; i < ch.length; i++) {
                        String hexString = Integer.toHexString(ch[i]);
                        objectCode = objectCode.concat(hexString);

                    }
                }

                passTwo = passTwo.concat(line[0] + "\t\t" + line[1] + "\t\t" + line[2] + "\t\t" + objectCode + "\n");
            } else {
                passTwo = passTwo.concat(line[0] + "\t\t" + line[1] + "\t\t" + line[2] + "\t\t" + "Has no object code" + "\n");
            }

        }
        BufferedWriter outpass2 = new BufferedWriter(new FileWriter("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\outpass2.txt"));

        outpass2.write(passTwo);
        outpass2.close();

        //HTE HAS ALL THE OBJECT CODE
        //AND THE H RECORD
        //WE CAN ADD ALL THE LOCATION COUNTERS IN AN ARRAYLIST AND THEN CHECK ON EVERYONE AND THE ONE AFTER IT
        //SUBTRACT THE ONE AFTER WITH THE CURRENT LOCATION AND CHECK IF IT REACHED THE LIMIT OF 30 BYTES OR NOT
        //ALSO WE NEED TO CHECK IF THE CURRENT MENEMONIC IS AN RESW OR RESB  BOTH THE LIMIT AND THE RESW,RESB WE NEED TO START A NEW RECORD
        //WE INCREMENT THE LENGTH OF THE RECORD EACH ITERATION SUCCESS (NO CONDITION BREAK)
        //IF CONDITION BREAKS WE STORE THE CURRENT RECORD WITH THE CURRENT LENGTH AND CURRENT START
        //AND RESET THE RECORD TO NOTHING ()NO OBJ CODE NOW AND RESET THE START TO THE COMING LOCATION COUNTER

        StringBuilder hteRecord = new StringBuilder();

// Header Record (H)
        String programName = "COPY  "; // 6 characters, pad with spaces if needed
        String startAddress = String.format("%06X", startDecimal); // 6 hex digits
        String programLength = String.format("%06X", currentLocation - startDecimal); // 6 hex digits
        hteRecord.append("H^").append(programName).append("^").append(startAddress).append("^").append(programLength).append("\n");

// Text Records (T)
        BufferedReader pass2Reader = new BufferedReader(new FileReader("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\outpass2.txt"));
        Scanner pass2Scanner = new Scanner(pass2Reader);

        StringBuilder currentRecord = new StringBuilder();
        String currentStartAddress = "";
        int recordLength = 0;
        boolean firstInstruction = true;

// Don't skip the first line anymore, as it contains the first instruction

        while (pass2Scanner.hasNextLine()) {
            String line = pass2Scanner.nextLine();
            String[] parts = line.trim().split("\\s+");

            // Skip if line doesn't have enough parts
            if (parts.length < 4) continue;

            String label = parts[0];
            String instruction = parts[1];
            String operand = parts[2];
            String objectCode = parts[3];

            // Skip lines with no object code or RESW/RESB instructions
            if (objectCode.equals("Has") || instruction.equals("RESW") || instruction.equals("RESB")) {
                if (currentRecord.length() > 0) {
                    // Write the current record before starting a new one
                    String lengthHex = String.format("%02X", recordLength);
                    hteRecord.append("T^").append(currentStartAddress).append("^")
                            .append(lengthHex).append("^").append(currentRecord.toString()).append("\n");
                    currentRecord = new StringBuilder();
                    recordLength = 0;
                }
                continue;
            }

            // Get the object code length
            int codeLength = objectCode.length() / 2; // Two hex digits = 1 byte

            // Start a new record if:
            // 1. This is the first instruction
            // 2. Adding this instruction would exceed 30 bytes
            if (firstInstruction) {
                currentStartAddress = String.format("%06X", startDecimal); // Use program start address for first record
                firstInstruction = false;
            } else if (recordLength + codeLength > 0x1E) { // 30 bytes = 0x1E
                // Write current record and start new one
                String lengthHex = String.format("%02X", recordLength);
                hteRecord.append("T^").append(currentStartAddress).append("^")
                        .append(lengthHex).append("^").append(currentRecord.toString()).append("\n");

                // Start new record
                currentRecord = new StringBuilder();
                if (symbolTable.containsKey(label)) {
                    currentStartAddress = symbolTable.get(label);
                }
                currentStartAddress = String.format("%06X", Integer.parseInt(currentStartAddress, 16));
                recordLength = 0;
            }

            // Add object code to current record
            currentRecord.append(objectCode);
            recordLength += codeLength;
        }

// Write last text record if exists
        if (currentRecord.length() > 0) {
            String lengthHex = String.format("%02X", recordLength);
            hteRecord.append("T^").append(currentStartAddress).append("^")
                    .append(lengthHex).append("^").append(currentRecord.toString()).append("\n");
        }

// End Record (E)
        hteRecord.append("E^").append(String.format("%06X", startDecimal));

// Write HTE record to file
        BufferedWriter hteWriter = new BufferedWriter(new FileWriter("C:\\Users\\ALEX STORE\\IdeaProjects\\ModifiedSic\\src\\HTE.txt"));
        hteWriter.write(hteRecord.toString());
        hteWriter.close();
        pass2Reader.close();
    }
}