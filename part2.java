import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class part2 {

    public int cap = 100;
    public int[][] matrix;
    public List<group> Groups;
    // constructor to initialize arraylist and fill it

    public part2(int cap, String[] groupnames, int[] amountmembers, int[] weight) {
        this.cap = cap;
        this.Groups = new ArrayList<group>(groupnames.length);
        for(int x = 0; x < groupnames.length;x++){
            Groups.add(new group(groupnames[x], amountmembers[x], weight[x]));
        }
    }


    public class group{

        String name;
        int members;
        int weight;
        int profits;

        public group(String n, int w, int m){
            this.name = n;
            this.members = m;
            this.weight = w;
            if(this.members > 100){
                this.profits = (members- 100)*5;
            }else{
                this.profits = 0;
            }
        }    


        //gets
        public String getName(){return name;}
        public int getMembers(){return members;}
        public int getWeight(){return weight;}
        public int getProfits(){return profits;}

        public String toString(){
            return "Group "+name+": "+weight+" members, with a weight of "+members;
        }

    }

    /////////////////////////
    //  solution class  ////

    /*public static class Solution{
        
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

    }*/
///////////////////////////
/*
    public static class knapsack{
        
        private static List<group> items;
        private static int capacity;

        public knapsack(List<group> items, int capacity) {
            part2.knapsack.items = items;
            part2.knapsack.capacity = capacity;
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
            System.out.println(nitems);
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

        public void remove(Solution sol, ArrayList<group> Groups){

            

        }



    }
    */

    ///////////////////////////


    public int knapsack(){
        
        this.matrix = new int[Groups.size()+1][cap+1];
        for(int i=0; i <= Groups.size();i++ ){
            for (int j=0; j <= cap;j++){
                if( i==0 || j==0 ){
                    matrix[i][j] = 0;
                }else if ( Groups.get(i-1).weight <= j ){
                    matrix[i][j] = Math.max( Groups.get(i-1).members + matrix[i-1][j - Groups.get(i-1).weight] , matrix[i-1][j] );
                }else{
                    matrix[i][j] = matrix[i-1][j];
                }
            }
        }    

        return matrix[Groups.size()][cap];
    }

    public void removeGroups(){
        
        for(int x = 1;x <= 5;x++){
            int index = 0;
            int finalProf = 0;
            int finalWeight = 0;
            int removeable[] = new int[Groups.size()];
            
            this.knapsack();

            int w = cap;
            String removedGroups = "";

            for(int i = matrix.length -1 ;i > 0 ;i--){
                int matSingle  = this.matrix[i][w];
                if(matSingle != this.matrix[i-1][w] ){
                    removedGroups += Groups.get(i-1).name+" ";

                    finalWeight += Groups.get(i-1).members;
                    finalProf += Groups.get(i-1).profits;
                    w -= Groups.get(i-1).weight;
                    removeable[index++] = i-1;
                }

            }
            System.out.println("Bus "+x+" has a weight of: "+finalWeight+"kg and thus a profit of: R"+finalProf+"\nThe included groups on bus "+ x+" are: "+removedGroups);
            finalWeight = 0;
            removedGroups = "";
            finalProf = 0;
            for(int y=0; y < index;y++){
                Groups.remove(removeable[y]);
            }
        }

    }


    // making a list of all the groups
    

    // method of populating the empty list with data from csv
   /* public static List<group> readData() {

        BufferedReader bf = null;
        String line = "";
        group nuut = null;

        try {
            bf = new BufferedReader(new FileReader("data.csv"));
            line = bf.readLine();
            while ((line = bf.readLine()) != null) {
                String split[] = line.split(",");

                nuut = new group(split[0], Integer.parseInt(split[2]), Integer.parseInt(split[1]));
                //System.out.println(nuut.toString());
                Groups.add(nuut);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Groups;
    }*/

    public static void main(String args[]) {

        //readData();

        String groups[] = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
        int amountmembers[] = new int[]{48,30,42,36,36,48,42,42,36,24,30,30,42,36,36};
        int weight[] = new int[]{100,300,250,500,350,300,150,400,300,350,450,100,200,300,250};

        //knapsack kp = new knapsack(Groups, 100);
        //knapsack.display();

        //Solution sol = kp.solve();
        //System.out.println(sol.items.get(0));
        //sol.display();

        part2 p = new part2(100,groups,amountmembers,weight);
        p.removeGroups();
        

    }

}