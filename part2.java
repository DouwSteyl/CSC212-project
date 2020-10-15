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

    // making a list of all the groups
    public static ArrayList<group> Groups = new ArrayList<>();


    // method of populating the empty list with data from csv
    public static ArrayList<group> readData(){

       
        BufferedReader bf = null;
        String line = "";
        group nuut = null;

        try {
            bf = new BufferedReader(new FileReader("data2.csv"));
            line = bf.readLine();
            while( (line  = bf.readLine()) != null ){
                String split[] = line.split(",");
                
                nuut = new group( split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]) );
                System.out.println(nuut.toString());
                Groups.add(nuut);
            }

        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Groups;
    }










    public static void main(String args[]){

        part2 obj = new part2();
            
        readData();
        



    }

}