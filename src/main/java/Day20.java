
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * Solves <a href="https://adventofcode.com/2023/day/20">Advent of Code 2023, Day 20</a>
 */
public class Day20 {
    ModuleListener counter = new ModuleListener();
    Map<String, Module> elementMap = new HashMap<>();

    ActionQueue actionQueue = new ActionQueue(elementMap, counter);

    public static void main(String[] args) throws IOException {
        Day20 solver = new Day20();
        Path path = new File("src/main/resources/day_20_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);

        solver.pushButtonXTimes(1000);
        long result1 = solver.counter.high * solver.counter.low;

        System.out.println("Day 20, Part 1 : " + result1);
        solver.counter.reset();
        int result2 = solver.findTarget();
        System.out.println("Day 20, Part 2 : " + result2);
    }

    public void parseInput(String input) {
        List<SenderAndReciever> senderAndRecieverList = new ArrayList<>();
        List<Conjuction> conjunctions = new ArrayList<>();
        for (String line : input.lines().toList()){
            String[] x = line.split(" -> ");
            if (x[0].equals("broadcaster")){
                senderAndRecieverList.add(new SenderAndReciever("broadcaster", x[1].split(", ")));
                elementMap.put("broadcaster", new Broadcaster("broadcaster", actionQueue, x[1].split(", ")));
            } else if (x[0].startsWith("%")) {
                String name = x[0].substring(1);
                senderAndRecieverList.add(new SenderAndReciever(name, x[1].split(", ")));
                elementMap.put(name, new FlipFlop(name, actionQueue, x[1].split(", ")));
            } else if (x[0].startsWith("&")) {
                String name = x[0].substring(1);
                senderAndRecieverList.add(new SenderAndReciever(name, x[1].split(", ")));
                Conjuction conjunction = new Conjuction(name, actionQueue, x[1].split(", "));
                elementMap.put(name, conjunction);
                conjunctions.add(conjunction);
            }
        }
        for (var c: conjunctions){
            for (var sar: senderAndRecieverList){
                for (var reciever: sar.recievers){
                    if(reciever.equals(c.name)) c.addInput(sar.sender);
                }
            }
        }

    }

    public void button() {
        actionQueue.actions.add(new Action("button", Pulse.LOW, "broadcaster"));
        actionQueue.start(Optional.empty());
    }

    public void pushButtonXTimes(int iterations) {
        for (int i= 0; i< iterations; i++){
            button();
        }
    }

    public int findTarget(){
        int c = 1;
        while (true) {

            actionQueue.actions.add(new Action("button", Pulse.LOW, "broadcaster"));
            if (actionQueue.start(Optional.of("rx"))) return c;
            c++;
        }
    }

    record SenderAndReciever(String sender, String[] recievers){}
    public enum Pulse{LOW, HIGH}
    abstract class Module {

        String name;
        ActionQueue actionQueue;
        String[] elements;

        public Module(String name, ActionQueue actionQueue, String[] elements) {
            this.name = name;
            this.elements = elements;
            this.actionQueue = actionQueue;
        }
        abstract void sendPulse(String from, Pulse pulse);
    }

    class Broadcaster extends Module {


        public Broadcaster(String name, ActionQueue actionQueue, String[] elements) {
            super(name, actionQueue, elements);
        }

        @Override
        public void sendPulse(String from, Pulse pulse) {
            for (var x : elements) {
                actionQueue.actions.add(new Action(name, pulse, x));
            }
        }
    }
    class FlipFlop extends Module {
        boolean isOn = false;

        public FlipFlop(String name, ActionQueue actionQueue, String[] elements) {
            super(name, actionQueue, elements);
        }

        @Override
        void sendPulse(String from, Pulse pulse) {
            if (pulse == Pulse.HIGH) return;
            for (int i= 0; i< elements.length; i++) {
                Pulse nextPulse = isOn ? Pulse.LOW : Pulse.HIGH;
                actionQueue.actions.add(new Action(name, nextPulse, elements[i]));
            }
            isOn = !isOn;
        }
    }

    class Conjuction extends Module{
        Map<String, Pulse> inputs = new HashMap<>();

        public Conjuction(String name, ActionQueue actionQueue, String[] elements) {
            super(name, actionQueue, elements);
        }

        @Override
        void sendPulse(String from, Pulse pulse) {
            inputs.put(from, pulse);
            boolean isAllHigh = inputs.values().stream().allMatch(x -> x.equals(Pulse.HIGH));
            Pulse p = isAllHigh? Pulse.LOW : Pulse.HIGH;
            for (var son : elements){
                actionQueue.actions.add(new Action(name, p,son));
            }

        }

        public void addInput(String input){
            this.inputs.put(input, Pulse.LOW);
        }
    }


    static class ModuleListener {
        long high = 0;
        long low = 0;

        public void count(Pulse pulse) {
            switch (pulse) {
                case LOW -> low++;
                case HIGH -> high++;
            }
        }

        void reset() {
            low = 0;
            high = 0;
        }
    }
        record Action(String from, Pulse pulse, String to){
            void print(){
                System.out.println(from + " -" + pulse.name() + "-> " + to);
            }
        }
        class ActionQueue {
            public Queue<Action> actions = new LinkedList<>();
            Map<String, Module> elementMap;
            ModuleListener counter;

            public ActionQueue(Map<String, Module> elementMap, ModuleListener counter) {
                this.elementMap = elementMap;
                this.counter = counter;
            }

            /**
             * Starts the queue if a target is
             * @param targetToFind The target to find, None if no target
             * @return is the target found
             */
            boolean start(Optional<String> targetToFind){
                while (!actions.isEmpty()){
                    var action = actions.poll();
                    Module module = elementMap.get(action.to);
                    counter.count(action.pulse);
                    if (targetToFind.isPresent()){
                        if (action.to.equals(targetToFind.get()) && action.pulse.equals(Pulse.LOW)) {
                            return true;
                        }
                    }
                    if (module!= null) module.sendPulse(action.from,action.pulse);
                }
                return false;
            }
        }

}
