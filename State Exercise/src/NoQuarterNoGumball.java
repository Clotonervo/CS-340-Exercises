public class NoQuarterNoGumball extends State {

    @Override
    void handleTurned(GumballMachine machine) {
        if(machine.getGumballs() > 0){
            machine.updateState(new NoQuarterGumball());
        }
        System.out.println("Invalid Transaction, no quarter in machine");
    }

    @Override
    void addQuarter(GumballMachine machine) {
        System.out.println("Quarter Inserted");
        machine.updateState(new QuarterNoGumball());
    }

    @Override
    void removeQuarter(GumballMachine machine) {
        System.out.println("No quarter to remove!");
    }
}
