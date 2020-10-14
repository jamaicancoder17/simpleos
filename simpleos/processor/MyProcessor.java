package simpleos.processor;

import simpleos.memory.*;

import java.util.ArrayList ;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.Map;
import java.util.HashMap;


public class MyProcessor extends Processor {

    private MyMemory PC;    
    private MyMemory IR;    
    private MyMemory ACC; 
    private ArrayList<Integer> sequence = new ArrayList<Integer>();  
    private int flag = 0;
    private Map<Integer, BiConsumer<Integer, MyMemory>> commands = new HashMap<>();
    
    //Set up processors with commands to execute
    public MyProcessor()
    {
      sequence.add(0);

      commands.put(1,(input,memory)->{
        System.out.println("Load AC from MEMORY");
        int val = memory.getValue(0);
        ACC.setValue(0,val);
      });

      commands.put(2,(input,memory)->{
        System.out.println("Store AC to MEMORY");
        int val = ACC.getValue(0);
        if (input == 999)
        {
          memory.setValue(1,val);
        }
        else{
          memory.setValue(0, val);
        }
        
      });

      commands.put(5,(input,memory)->{
        System.out.println("Add to AC from MEMORY");
        int val = memory.getValue(1);
        int accumulator = ACC.getValue(0) + val;
        ACC.setValue(0,accumulator);
      });

      commands.put(4,(input,memory)->{
        System.out.println("Subtract from AC from MEMORY");
        int val = memory.getValue(1);
        int accumulator = memory.getValue(0) - val;
        ACC.setValue(0,accumulator);

        commands.getOrDefault(2, (input1,memory1)->{
          System.out.println("Invalid Command");
        }).accept(999,memory);
      });

      commands.put(3,(input,memory)->{
        System.out.println("Load AC from STDIN\n");
        System.out.println("~~~Enter 0 to continue. Enter 8 to STOP~~~");
        Scanner read = new Scanner(System.in);
        int val = read.nextInt();
        ACC.setValue(0,val);

      });

      commands.put(7,(input,memory)->{
        System.out.println("Store AC to STDOUT");
        sequence.add(ACC.getValue(0));
        System.out.println("New Fibonacci Number: "+ACC.getValue(0));
        System.out.println("~~~~~FIBONACCI SEQUENCE GENERATED~~~~~~");
        System.out.println(sequence.toString());
      });

      commands.put(8,(input,memory)->{
        //System.out.println("Halt");
        System.out.println("~~~~~FIBONACCI SEQUENCE GENERATED~~~~~~");
        System.out.println(sequence.toString());
        System.out.println("The Program will now terminate");
        
      });

      commands.put(10, (input,memory)->{
        //System.out.println("GO-TO");
        System.out.println("Continuing");
      });

      commands.put(12, (input,memory)->{
        //System.out.println("COMPARE");
        int test = ACC.getValue(0);
        if(test == 8){
          flag = 1;
        }
      });

      this.PC = new MyMemory(1);
      this.IR = new MyMemory(1);
      this.ACC = new MyMemory(1);
      this.ACC.setValue(0,0);

    }

    public int fetch(int index, int instruction){
        System.out.println("Processor is now fetching..");

        this.PC.setValue(0,index);
        this.IR.setValue(0,instruction);

        return 1;
    } 
    public int execute(int index, MyMemory registers){
        
        System.out.println("Processor is now execting..");
        
        int currCommand = this.IR.getValue(0);
        System.out.println("The current command is: "+ currCommand);

        commands.getOrDefault(currCommand, (input,memory)->{
          System.out.println("Invalid Command");
        }).accept(index,registers);
        
        if (currCommand == 12)
        {
          if (flag == 1){
            commands.getOrDefault(8, (input,memory)->{
              System.out.println("Invalid Command");
            }).accept(8,registers);
            return 999;
          }
          else{
            commands.getOrDefault(10, (input,memory)->{
              System.out.println("Invalid Command");
            }).accept(10,registers);
            return 0;
          }
        }

        return 1;
    } 

} //end abstract class Processor
