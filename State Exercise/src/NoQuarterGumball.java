public class NoQuarterGumball extends State{

    @Override
    public void handleTurned(GumballMachine machine){
        if (machine.getGumballs() < 0){
            machine.updateState(new NoQuarterNoGumball());
        }
        System.out.println("Invalid Transaction, no quarter in machine");
    }

    @Override
    public void addQuarter(GumballMachine machine){
        System.out.println("Quarter Inserted");
        if(machine.getGumballs() < 0){
            machine.updateState(new QuarterNoGumball());
        }
        else {
            machine.updateState(new QuarterGumball());
        }
    }

    @Override
    void removeQuarter(GumballMachine machine) {
        System.out.println("No quarter to remove!");
    }
}