package simpleos.processor;
import simpleos.memory.MyMemory;

public abstract class Processor {

    public abstract int fetch(int index, int instruction);
    public abstract int execute(int index, MyMemory registers);

} //end abstract class Processor
