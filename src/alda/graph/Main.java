package alda.graph;


import java.util.Scanner;

public class Main {
    Graph graph;
    Scanner scanner;
    public static void main(String[] args){
        Main main = new Main(args);

    }

    public Main(String[] args){
        graph = new Graph(args);
        scanner = new Scanner(System.in);
        run();
    }

    public String getInput(){
        return scanner.nextLine();
    }
    private void run() {
        while(true){
            System.out.println("------------------------------------------------");
            System.out.println("Write the Name of the actor you want to find: ");
            String input = getInput();
            if(input != null && !input.isEmpty()){
                long time = System.currentTimeMillis();
                int number = graph.getDegreeOfSeparation(input);
                System.out.println("Bacon number of " + input + " = " + number);
                System.out.println("Search took: " + (System.currentTimeMillis() - time ) + " ms");
            }


        }
    }
}
