public class QuarterGumball extends State {

    @Override
    void handleTurned(GumballMachine machine) {
        System.out.println("Transaction succeeds! Enjoy your gumball!");
        machine.swallowQuarter();
        if(machine.getGumballs() <= 0){
            machine.updateState(new NoQuarterNoGumball());
        }
        else {
            machine.updateState(new NoQuarterGumball());
        }
    }

    @Override
    void addQuarter(GumballMachine machine) {
        System.out.println("Already a quarter in the machine");
    }

    @Override
    void removeQuarter(GumballMachine machine) {
        System.out.println("Quarter removed");
        machine.updateState(new NoQuarterGumball());
    }
}
