/*
ID Number: 620119216
*/

package simpleos.sys;

import simpleos.memory.*;
import simpleos.processor.*;
import java.util.ArrayList;


public class OS {

 
    public static void main(String[] args){
        
        System.out.println("\nThis program generates a fibonacci sequence by simulating the instruction cycle.\n");
        ArrayList<Integer> programMemory = new ArrayList<Integer>() ;
        int index = 0;
        int hold;
            //Program Memory - holds the instructions in the correct order
            programMemory.add(0); //Load accumulator from memory 'a'
            programMemory.add(2); // Add b to accumulator from memory
            programMemory.add(1); // Store accumulator to memory 'a' and 'sequence'
            programMemory.add(5); //Store AC to STDOUT
            programMemory.add(3); //Subtract from accumulator to memory 'b'
            //programMemory.add(1); //Store 'b' in memory
            programMemory.add(4); //Load an input from STDIN
            programMemory.add(8); //COMPARE to check if the user entered the HALT instruction
            programMemory.add(6); // STOP - Print calculated sequence
            programMemory.add(7); //GOTO - Return to the start of the arrayList
        try {
            
            //Memory location to store the set of instructions
            MyMemory insMem = new MyMemory(10);

            //Load AC from Memory
            insMem.setValue(0,Integer.parseInt("0001",2));
            //Store AC to Memory
            insMem.setValue(1,Integer.parseInt("0010",2));
            //Add to AC from Memory
            insMem.setValue(2,Integer.parseInt("0101",2));
            //Subtract from AC to memory
            insMem.setValue(3,Integer.parseInt("0100",2));
            //Load AC from STDIN
            insMem.setValue(4,Integer.parseInt("0011",2));
            //Store AC to STDOUT
            insMem.setValue(5,Integer.parseInt("0111",2));
            //STOP
            insMem.setValue(6,Integer.parseInt("1000",2));
            //GOTO
            insMem.setValue(7,Integer.parseInt("1010",2));
            //COMP
            insMem.setValue(8,Integer.parseInt("1100",2));




            //Memory for registers
            MyMemory registers = new MyMemory(5);
      
            //Register 0 holds my fibonacci number
            registers.setValue(0,0);
            //Register 1 holds the previous fibonacci number (starts at 1)
            registers.setValue(1,1);

            //Initialize the processor
            MyProcessor p = new MyProcessor();
      
            //registers.printSize();
            //System.out.println(Integer.parseInt("1101",2));
            //System.out.println(programMemory.size());

            while(index < programMemory.size())
            {
              //System.out.println(index);
              //System.out.println(insMem.getValue(programMemory.get(index)));

              System.out.println("\n\n");
              p.fetch(index,insMem.getValue(programMemory.get(index)));
              Thread.sleep(1000);
              hold = p.execute(index,registers);
              index = index + 1;
              if (hold == 0)
              {
                index = 0;
              }
              if (hold == 999)
              {
                Thread.sleep(1000);
                System.exit(0);
              }
              Thread.sleep(1000);

              

            }
            
		      }catch (InterruptedException e) {
			        e.printStackTrace();
		      }
    }// End man method

}// END class OS
