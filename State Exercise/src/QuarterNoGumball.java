public class QuarterNoGumball extends State {

    @Override
    void handleTurned(GumballMachine machine) {
        if (machine.getGumballs() > 0){
            State state = new QuarterGumball();
            state.handleTurned(machine);
        }
        else {
            System.out.println("No gumballs to dispense, thanks for the money!");
            machine.swallowQuarter();
            machine.updateState(new NoQuarterNoGumball());
        }
    }

    @Override
    void addQuarter(GumballMachine machine) {
        System.out.println("Already a quarter in the machine");
    }

    @Override
    void removeQuarter(GumballMachine machine) {
        System.out.println("Quarter removed");
        machine.updateState(new NoQuarterNoGumball());
    }
}
