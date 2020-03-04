
package booleanretrieval;
import java.util.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

/**
 *
 * @author M.Saad
 */
public class BooleanRetrieval {

    private static ArrayList<Integer> intersection(ArrayList<Integer> list3, ArrayList<Integer> list4)
    {
        ArrayList<Integer> answer = new ArrayList();
        for(int i=0;i<list3.size() || i< list4.size() ; i++)
        {
            
            if(i >= list3.size())
            {
                if(list3.contains(list4.get(i)) && !answer.contains(list4.get(i)))
                    answer.add(list4.get(i));
            }
            else if(i >= list4.size())
            {
                if(list4.contains(list3.get(i)) && !answer.contains(list3.get(i)))
                    answer.add(list3.get(i));
            }
            
            else
            {
                if(list3.contains(list4.get(i)))
                {
                    if(!answer.contains(list4.get(i)))
                        answer.add(list4.get(i));
                }
            }
            
        }
        
        return answer;
    }

    private static ArrayList<Integer> union(ArrayList<Integer> list3, ArrayList<Integer> list4) 
    {
        ArrayList<Integer> answer = new ArrayList();
        for(int i=0; i < list3.size() || i < list4.size() ; i++)
        {
            if(i >= list3.size())
            {
                if(!answer.contains(list4.get(i)))
                    answer.add(list4.get(i));
            }
            else if(i >= list4.size())
            {
                if(!answer.contains(list3.get(i)))
                    answer.add(list3.get(i));
            }
            
            else
            {
                if(!answer.contains(list4.get(i)))
                    answer.add(list4.get(i));
                
                if(!answer.contains(list3.get(i)))
                    answer.add(list3.get(i));
            }
        }
        
        return answer;        
    }
    
    private static ArrayList<Integer> notQuery(ArrayList<Integer> list3)
    {
        ArrayList<Integer> answer = new ArrayList();
        
        for(int i=1; i<=50; i++)
        {
            if(list3.contains(i))
            {
                
            }
            else
                answer.add(i);
            
        }
        
        return answer;
        
    }
   
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        int i,j,sum=0;
        String line = "";
        String line1;
        String []afterTokenArray ;
        TreeMap<String,ArrayList<Integer>> Map = new TreeMap<String,ArrayList<Integer>>();
        String [] stopWords = {"a","is","the","of","all","and","to","can","be","as","once","for","at","am","are","has","have","had","up","his","her","in","on","no","we","do"};
        List<String> list1 = Arrays.asList(stopWords);
        for(i=1;i<=50;i++)
        {
            FileReader in = new FileReader("F:\\FAST-NU\\6th Semester\\ShortStories\\" + i + ".txt");
            BufferedReader br = new BufferedReader(in);
            
            while ((line = br.readLine()) != null) 
            {
                line1 =line.replaceAll("\\.", "").replaceAll("\\&", "").replaceAll("\\‘", "").replaceAll("th", "").replaceAll("\\$", "").replaceAll("\\*", "").replaceAll("\\'", "").replaceAll("\\,", "").replaceAll("\\?", "").replaceAll("\\!", "").replaceAll("\\;", "").replaceAll("\\-", " ").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("<", "").replaceAll(">", "").replaceAll("_", "").replaceAll("\"", "").replaceAll(":", "").replaceAll("\\(", "").replaceAll("\\)", "");
                line1 = line1.toLowerCase();
                afterTokenArray = line1.split("\\s+");
                for(j=0;j<afterTokenArray.length;j++)
                {
                    if(!(afterTokenArray[j].equals("")))
                    {
                        if(!(list1.contains(afterTokenArray[j])))
                        {
                            if(Map.containsKey(afterTokenArray[j]))
                            {
                                ArrayList list = Map.get(afterTokenArray[j]);
                                if(!list.contains(i))
                                    list.add(i);
                            }

                            else
                            {
                                ArrayList<Integer> a = new ArrayList<Integer>();
                                a.add(i);
                                Map.put(afterTokenArray[j], a);
                            }
                        }      
                    }
                
                }
               
            }
        }
        
        BufferedWriter bufferWriter = null;
        FileWriter fileCursor = null;
        String FILENAME = "F:\\FAST-NU\\6th Semester\\invertedIndex.txt";
        
	fileCursor = new FileWriter(FILENAME);
	bufferWriter = new BufferedWriter(fileCursor);
	 
        for (Map.Entry<String, ArrayList<Integer>> entry : Map.entrySet()) 
        {
            bufferWriter.write(entry.getKey() + "\t" + entry.getValue());
            bufferWriter.newLine();
            sum++;
            bufferWriter.flush();  
        }

        bufferWriter.close(); 
        
        System.out.println(sum);
        
        Scanner obj=new Scanner(System.in);
        
        System.out.println("Enter Query : ");
        String query = obj.nextLine();
        query = query.toLowerCase();
        query =query.replaceAll("\\.", "").replaceAll("\\&", "").replaceAll("\\‘", "").replaceAll("th", "").replaceAll("\\$", "").replaceAll("\\*", "").replaceAll("\\'", "").replaceAll("\\,", "").replaceAll("\\?", "").replaceAll("\\!", "").replaceAll("\\;", "").replaceAll("\\-", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("<", "").replaceAll(">", "").replaceAll("_", "").replaceAll("\"", "").replaceAll(":", "").replaceAll("\\(", "").replaceAll("\\)", "");
        
        String [] simpleQuery;
        
        simpleQuery = query.split(" ");
        
        if(simpleQuery.length == 1)
        {
            if(Map.containsKey(simpleQuery[0]))
            {
                System.out.println(Map.get(simpleQuery[0]).size() + " documents retrieved " + Map.get(simpleQuery[0]));
            }
            
            else
            {
                System.out.println("No Term Found");
            }
        }
            
            
            
        
        if(simpleQuery.length == 3)
        {
            
            if(simpleQuery[1].equals("and"))
            {
                ArrayList list3 = Map.get(simpleQuery[0]);
                ArrayList list4 = Map.get(simpleQuery[2]);
                ArrayList answer = intersection(list3,list4);
                
                System.out.println(answer.size() + " documents retrieved : " + answer);
                
            }
        
            else if(simpleQuery[1].equals("or"))
            {
                ArrayList list3 = Map.get(simpleQuery[0]);
                ArrayList list4 = Map.get(simpleQuery[2]);
                ArrayList answer = union(list3,list4);
                
                System.out.println(answer.size() + " documents retrieved : " + answer);
            }
            else
            {
                System.out.println("Wrong query entered");
            }
        }
        
        else if(simpleQuery.length == 2)
        {
            if(simpleQuery[0].equals("not"))
            {
                 ArrayList list3 = Map.get(simpleQuery[1]);  
                 ArrayList<Integer> answer1 = notQuery(list3);
                 System.out.println(answer1.size() + " documents retrieved : " + answer1);
            }
            
            else
            {
                System.out.println("Wrong Query Entered");
            }
        }
        else if(simpleQuery.length == 5)
        {
            if(simpleQuery[1].equals("and"))
            {
                ArrayList list3 = Map.get(simpleQuery[0]);
                ArrayList list4 = Map.get(simpleQuery[2]);
                ArrayList<Integer> answer = intersection(list3,list4);

                if(simpleQuery[3].equals("and"))
                {
                    ArrayList list5 = Map.get(simpleQuery[4]);

                    ArrayList<Integer> answer1 = intersection(answer,list5);
                    System.out.println(answer1.size() + " documents retrieved : " + answer1);
                }

                else if(simpleQuery[3].equals("or"))
                {
                    ArrayList<Integer> list5 = Map.get(simpleQuery[4]);

                    ArrayList<Integer> answer1 = union(answer,list5);
                    System.out.println(answer1.size() + " documents retrieved : " + answer1);
                }

                else
                {
                    System.out.println("Wrong Query Entered");
                }

            }
                    
            else if(simpleQuery[1].equals("or"))
            {
                ArrayList list3 = Map.get(simpleQuery[0]);
                ArrayList list4 = Map.get(simpleQuery[2]);
                ArrayList answer = union(list3,list4);

                if(simpleQuery[3].equals("and"))
                {
                    ArrayList list5 = Map.get(simpleQuery[4]);

                    ArrayList answer1 = intersection(answer,list5);
                    System.out.println(answer1.size() + " documents retrieved : " + answer1);
                }

                else if(simpleQuery[3].equals("or"))
                {
                    ArrayList list5 = Map.get(simpleQuery[4]);

                    ArrayList answer1 = union(answer,list5);
                    System.out.println(answer1.size() + " documents retrieved : " + answer1);
                }

                else
                {
                    System.out.println("Wrong Query Entered");
                }

            }
        }        
        
        else if(simpleQuery.length == 6)
        {
            if(simpleQuery[0].equals("not"))
            {
                ArrayList list3 = Map.get(simpleQuery[1]);  
                ArrayList<Integer> answer1 = notQuery(list3);
                
                if(simpleQuery[2].equals("or"))
                {
                    
                    ArrayList list4 = Map.get(simpleQuery[3]);
                    ArrayList answer = union(answer1,list4);

                    if(simpleQuery[4].equals("and"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = intersection(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else if(simpleQuery[4].equals("or"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = union(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else
                    {
                        System.out.println("Wrong Query Entered");
                    }

                }
                
                else if(simpleQuery[2].equals("and"))
                {
                    
                    ArrayList list4 = Map.get(simpleQuery[3]);
                    ArrayList answer = intersection(answer1,list4);

                    if(simpleQuery[4].equals("and"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = intersection(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else if(simpleQuery[4].equals("or"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = union(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else
                    {
                        System.out.println("Wrong Query Entered");
                    }

                }
                 
            }
            else if(simpleQuery[2].equals("not"))
            {
                ArrayList list3 = Map.get(simpleQuery[3]);  
                ArrayList<Integer> answer1 = notQuery(list3);
                
                if(simpleQuery[1].equals("or"))
                {
                    
                    ArrayList list4 = Map.get(simpleQuery[0]);
                    ArrayList answer = union(answer1,list4);

                    if(simpleQuery[4].equals("and"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = intersection(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                        
                    }

                    else if(simpleQuery[4].equals("or"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = union(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else
                    {
                        System.out.println("Wrong Query Entered");
                    }

                }
                
                else if(simpleQuery[1].equals("and"))
                {
                    
                    ArrayList list4 = Map.get(simpleQuery[0]);
                    ArrayList answer = intersection(answer1,list4);

                    if(simpleQuery[4].equals("and"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = intersection(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else if(simpleQuery[4].equals("or"))
                    {
                        ArrayList list5 = Map.get(simpleQuery[5]);

                        answer1 = union(answer,list5);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else
                    {
                        System.out.println("Wrong Query Entered");
                    }

                }
            }
            else if(simpleQuery[4].equals("not"))
            {
                ArrayList list5 = Map.get(simpleQuery[5]);  
                ArrayList<Integer> answerFinal = notQuery(list5);
                
                if(simpleQuery[1].equals("and"))
                {
                    ArrayList list3 = Map.get(simpleQuery[0]);
                    ArrayList list4 = Map.get(simpleQuery[2]);
                    ArrayList<Integer> answer = intersection(list3,list4);

                    if(simpleQuery[3].equals("and"))
                    {

                        ArrayList<Integer> answer1 = intersection(answerFinal,answer);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                        
                    }

                    else if(simpleQuery[3].equals("or"))
                    {
                        ArrayList<Integer> answer1 = union(answerFinal,answer);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else
                    {
                        System.out.println("Wrong Query Entered");
                    }

                }
                    
                else if(simpleQuery[1].equals("or"))
                {
                    ArrayList list3 = Map.get(simpleQuery[0]);
                    ArrayList list4 = Map.get(simpleQuery[2]);
                    ArrayList<Integer> answer = intersection(list3,list4);

                    if(simpleQuery[3].equals("and"))
                    {

                        ArrayList<Integer> answer1 = intersection(answerFinal,answer);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else if(simpleQuery[3].equals("or"))
                    {
                        ArrayList<Integer> answer1 = union(answerFinal,answer);
                        System.out.println(answer1.size() + " documents retrieved : " + answer1);
                    }

                    else
                    {
                        System.out.println("Wrong Query Entered");
                    }

                }
            }
            
        }
    }
        
}

