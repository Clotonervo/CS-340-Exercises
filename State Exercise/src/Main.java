public class Main {

    public static void main(String[] args){
        GumballMachine machine = new GumballMachine();
        machine.addGumballs(2);
        machine.addQuarter();
        machine.turnHandle();   //valid
        machine.turnHandle();   //no quarter
        machine.addQuarter();
        machine.turnHandle();   //valid
        machine.addQuarter();
        machine.addQuarter();   //already in machine
        machine.turnHandle();   //no more gumballs
        machine.turnHandle();   //no more gumballs/quarters
        machine.addQuarter();
        machine.addGumballs(2);
        machine.turnHandle();   //valid
        machine.addQuarter();
        machine.removeQuarter();
        machine.removeQuarter(); //no quarter to remove
        System.out.println(machine.getTotalMoney());
    }
}
