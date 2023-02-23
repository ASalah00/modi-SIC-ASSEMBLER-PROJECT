# modi-SIC-ASSEMBLER-PROJECT
# Introduction to modi-SIC
The modi-SIC consists of
1. Same instructions set (Format 3) of SIC
2. Same idea of reservation of variables in memory using BYTE, WORD, RESB, RESW

# modi-SIC is extened to include
1. Format 1 instuctions
2. Immediate Instruction (Format 3) that deals with an immediate value passed to as integer.
For the sack of simplification a list of modi-SIC instructions is attached at the end of project
description that have all instructions handled by modi-SIC + a full description of a new introduced
bit that states if the instruction is dealing with immediate or not.

# modi-SIC implementation details
It takes as an input a text file (in.txt) that contains an assembly program written in modi-SIC
assembly language containing comments, number lines as shown in figure 1. The modi-SIC
assemble will be modified to accept also Format 1 instruction of SIC/XE. So this case must be
handled.


# Pass 1
Pass 1 should read the intermediate file and produce: Location Counter for all program lines
followed by the generation of the symbol table
The program should produce as output:
• (outpass1.txt) that contains the corresponding location counter of the input Program
• (symbTable.txt) that contains the symbol table output of the input program
# Pass 2
Pass 2 should read the intermediate file and produce: object code for all program lines 
The program should produce as output:
• (outpass2.txt) that contains the corresponding object code of the input program


Immediate Instruction format in modi-SIC
All Type 3 instruction could be immediate instructions.
It is written in code as

LDA #3

where \# represents it is an immediate instruction and the number following the hash is a
decimal number.
This is done by a new division of bits of instructions of Type 3 (Format 3) as shown in following table.
Opcode (7 bits) Immediate flag (i) (1 bit) Indexing (x) (I bit) Address (15 bits)
The modification applied on the opcode as
1. Only opcode is represented as 7 bits (not 8) as in SIC
2. The 8th bit of the opcode represents the immediate flag (i) which has two values
a. 0 if the instruction without immediate value (has an address)
b. 1 if the instruction with immediate value
