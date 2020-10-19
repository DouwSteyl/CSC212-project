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