import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class part2 {

    public static class group{

        private String name;
        private int members;
        private int weight;

        public group(String n, int m, int w){
            this.name = n;
            this.members = m;
            this.weight = w;
        }    


        //gets
        public String getName(){return name;}
        public int getMembers(){return members;}
        public int getWeight(){return weight;}

        public String toString(){
            return "Group "+name+": "+members+" members, with a weight of "+weight;
        }

    }

    /////////////////////////
    //  solution class  ////

    public static class Solution{
        
        public List<group> items;
        public int value;

        public Solution(List<group> items, int value){
            this.items = items;
            this.value = value;
        }

        public void display() {
            if (items != null  &&  !items.isEmpty()){
                System.out.println("\nKnapsack solution");
                System.out.println("Value = " + value);
                System.out.println("Items to pick :");
                
                for (group item : items) {
                    System.out.println("- " + item.toString());
                }
            }
        }

    }
///////////////////////////

    public static class knapsack{
        
        private static List<group> items;
        private static int capacity;

        public knapsack(List<group> items, int capacity) {
            part2.knapsack.items = items;
            this.capacity = capacity;
        }

        public static void display() {
            if (items != null && items.size() > 0) {
                System.out.println("Knapsack problem");
                System.out.println("Capacity : " + capacity);
                System.out.println("Items :");

                for (part2.group item : items) {
                    System.out.println("- " + item.toString());
                }
            }
        }

        public Solution solve() {
            int nitems = items.size();
            int[][] matrix = new int[nitems + 1][capacity + 1];

            for (int i = 0; i <= capacity; i++)
                matrix[0][i] = 0;

            for (int i = 1; i <= nitems; i++) {
                for (int j = 0; j <= capacity; j++) {
                    if (items.get(i - 1).weight > j) {
                        matrix[i][j] = matrix[i - 1][j];
                    } else {
                        matrix[i][j] = Math.max(matrix[i - 1][j],
                                matrix[i - 1][j - items.get(i - 1).weight] + items.get(i - 1).members);
                    }
                }
            }

            int res = matrix[nitems][capacity];
            int w = capacity;
            List<group> oplossing = new ArrayList<>();

            for (int i = nitems; i > 0 && res > 0; i--) {
                if (res != matrix[i - 1][w]) {
                    oplossing.add(items.get(i - 1));
                    res -= items.get(i - 1).members;
                    w -= items.get(i - 1).weight;
                }
            }

            return new Solution(oplossing, matrix[nitems][capacity]);
        }

    }

    ///////////////////////////

    // making a list of all the groups
    public static List<group> Groups = new ArrayList<>();

    // method of populating the empty list with data from csv
    public static List<group> readData() {

        BufferedReader bf = null;
        String line = "";
        group nuut = null;

        try {
            bf = new BufferedReader(new FileReader("data.csv"));
            line = bf.readLine();
            while ((line = bf.readLine()) != null) {
                String split[] = line.split(",");

                nuut = new group(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                //System.out.println(nuut.toString());
                Groups.add(nuut);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Groups;
    }

    public static void main(String args[]) {

        part2 obj = new part2();

        readData();

        knapsack kp = new knapsack(Groups, 100);
        knapsack.display();

        Solution sol = kp.solve();
        sol.display();


    }

}