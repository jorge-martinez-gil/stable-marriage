package stable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
 
public class Stable {

	static Map<String, Integer> Cell;
	
	static List<String> rastro = new ArrayList<String>();
		
	static List<String> employees = Arrays.asList("jorge", "mario", "martin", "lorena");
	static List<String> employers = Arrays.asList("microsoft", "apple", "ibm", "adobe");
		
    static List<String> skillsJorge = new ArrayList<String>(Arrays.asList("C", "C++", "C#"));
    static List<String> skillsMario = new ArrayList<String>(Arrays.asList("Javascript", "CSS", "HTML", "Java"));
    static List<String> skillsMartin = new ArrayList<String>(Arrays.asList("Python", "R", "C", "SQL"));
    static List<String> skillsLorena = new ArrayList<String>(Arrays.asList("C", "C++", "SQL", "Java", "Scala"));

    
    static ArrayList<ArrayList<String>> skills = new ArrayList<ArrayList<String>>();
    
    static List<String> positionMicrosoft = new ArrayList<String>(Arrays.asList("C", "C++"));
    static List<String> positionApple = new ArrayList<String>(Arrays.asList("Python", "R", "Julia", "Ruby"));
    static List<String> positionIBM = new ArrayList<String>(Arrays.asList("Java", "Javascript", "SQL"));
    static List<String> positionAdobe = new ArrayList<String>(Arrays.asList("Scala", "Perl"));

    static ArrayList<ArrayList<String>> positions = new ArrayList<ArrayList<String>>();
	
	@SuppressWarnings("serial")
	static Map<String, List<String>> employeesPrefer =
        new HashMap<String, List<String>>(){{
        put("jorge", Arrays.asList("", "", "", ""));
        put("mario", Arrays.asList("", "", "", ""));
        put("martin", Arrays.asList("", "", "", ""));
        put("lorena", Arrays.asList("", "", "", ""));
    }};

	@SuppressWarnings("serial")
	static Map<String, List<String>> employersPrefer =
        new HashMap<String, List<String>>(){{
        put("microsoft", Arrays.asList("", "", "", ""));
        put("apple", Arrays.asList("", "", "", ""));
        put("ibm", Arrays.asList("", "", "", ""));
        put("adobe", Arrays.asList("", "", "", ""));

    }}; 
	
	
   /* It computes the intersection between two lists */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    /* Adds a new skill to the candidates profiles */
    public static void addSkill (String newSkill) {
    	
    	skillsJorge.add(newSkill);
    	skillsMario.add(newSkill);
    	skillsMartin.add(newSkill);
    	skillsLorena.add(newSkill);


    	//Remove duplicates from the candidates
    	Set<String> hs = new HashSet<>();
    	hs.addAll(skillsJorge);
    	skillsJorge.clear();
    	skillsJorge.addAll(hs);
    	
    	Set<String> hs2 = new HashSet<>();
    	hs2.addAll(skillsMario);
    	skillsMario.clear();
    	skillsMario.addAll(hs2);
    	
    	Set<String> hs3 = new HashSet<>();
    	hs3.addAll(skillsMartin);
    	skillsMartin.clear();
    	skillsMartin.addAll(hs3);
    	
    	Set<String> hs4 = new HashSet<>();
    	hs4.addAll(skillsLorena);
    	skillsLorena.clear();
    	skillsLorena.addAll(hs4);
    	
    }
    
    
    /* It computes the distance to the optimum for a stable marriage */
    public static int happinessIndex (Map<String, String> matches) {
		
    	int acumulator = 0;
    	
        for(Map.Entry<String, String> couple:matches.entrySet()){
        	
        	String Empresa = couple.getKey();
        	String Candidato = couple.getValue();
        	
    		List<String> lista = new ArrayList<String>();
    		lista = employersPrefer.get(Empresa);
        	
        	for(int i = 0 ; i < employersPrefer.size(); i++) {
    			if (lista.get(i).compareToIgnoreCase(Candidato) == 0)
    				break;
    			else
    				acumulator++;
        	}	
        	
        } 
        
       
        for(Map.Entry<String, String> couple:matches.entrySet()){
        	String Empresa2 = couple.getKey();
        	String Candidato2 = couple.getValue();
        	
    		List<String> lista2 = new ArrayList<String>();
    		lista2 = employeesPrefer.get(Candidato2);
        	
        	for(int i = 0 ; i < employeesPrefer.size(); i++) {
    			if (lista2.get(i).compareToIgnoreCase(Empresa2) == 0)
    				break;
    			else
    				acumulator++;
        	}	
        	
        } 
        	
    	return acumulator;
    	
    }
    
    
    /* It computes the preference lists for the candidates */
    public static void compute () {
    	   
    	List<String> preferences = new ArrayList<String>();
    	
    	for(int i = 0 ; i < skills.size(); i++) {
    	    Cell = new HashMap<String, Integer>();
    		for(int j = 0 ; j < positions.size(); j++)    	
    	       Cell.put(employers.get(j), intersection (skills.get(i), positions.get(j)).size());
    	   
    	    preferences.addAll(Cell.keySet());
    	    Collections.sort(preferences, new Comparator<String>() {
    	    	   @Override
    	    	   public int compare(String s1, String s2) {
    	    		   Integer popularity1 = Cell.get(s1);
    	    		   Integer popularity2 = Cell.get(s2);
    	    		   return popularity2.compareTo(popularity1);
    	    	   }
    	       });
    		
    	    List<String> toInsert = new ArrayList<String> (preferences);
    	    employeesPrefer.put(employees.get(i), toInsert);
    	    preferences.clear();
    	}
    	
    }
    
    /* It computes the preference lists for the companies */
    public static void compute2 () {
    	
	
    	List<String> preferences = new ArrayList<String>();

    	for(int i = 0 ; i < positions.size(); i++) {
    	    Cell = new HashMap<String, Integer>();
    		for(int j = 0 ; j < skills.size(); j++) 				
    			Cell.put(employees.get(j), intersection (positions.get(i), skills.get(j)).size());

    	   
    	    preferences.addAll(Cell.keySet());
    	    Collections.sort(preferences, new Comparator<String>() {
    	    	   @Override
    	    	   public int compare(String s1, String s2) {
    	    		   Integer popularity1 = Cell.get(s1);
    	    		   Integer popularity2 = Cell.get(s2);
    	    		   return popularity2.compareTo(popularity1);
    	    	   }
    	       });
    		
    	    List<String> toInsert = new ArrayList<String> (preferences);
    	    employersPrefer.put(employers.get(i), toInsert);
    	    preferences.clear();
    	}
    	
    }
    
    /* Initial load of the data */
    public static void load () {
    	    	    	
        skills.add((ArrayList<String>) skillsJorge); 
        skills.add((ArrayList<String>) skillsMario);
        skills.add((ArrayList<String>) skillsMartin);
        skills.add((ArrayList<String>) skillsLorena);

        
        positions.add((ArrayList<String>) positionMicrosoft);
        positions.add((ArrayList<String>) positionApple);
        positions.add((ArrayList<String>) positionIBM);
        positions.add((ArrayList<String>) positionAdobe);

    }
    
        
	/* Iterations over the permutations */	
    public static void iteration (List<String> skillsandco) {
    	  
    	
    	skills = new ArrayList<ArrayList<String>>();
    	positions = new ArrayList<ArrayList<String>>();
    	
    	employeesPrefer =
    	        new HashMap<String, List<String>>(){{
    	        put("jorge", Arrays.asList("", "", "", ""));
    	        put("mario", Arrays.asList("", "", "", ""));
    	        put("martin", Arrays.asList("", "", "", ""));
    	        put("lorena", Arrays.asList("", "", "", ""));
    	    }};

    	    employersPrefer =
    	        new HashMap<String, List<String>>(){{
    	        put("microsoft", Arrays.asList("", "", "", ""));
    	        put("apple", Arrays.asList("", "", "", ""));
    	        put("ibm", Arrays.asList("", "", "", ""));
    	        put("adobe", Arrays.asList("", "", "", ""));

    	    }};
    	    
    	    skillsJorge = new ArrayList<String>(Arrays.asList("C", "C++", "C#"));
    	    skillsMario = new ArrayList<String>(Arrays.asList("Javascript", "CSS", "HTML", "Java"));
    	    skillsMartin = new ArrayList<String>(Arrays.asList("Python", "R", "C", "SQL"));
    	    skillsLorena = new ArrayList<String>(Arrays.asList("C", "C++", "SQL", "Java", "Scala"));
    	    
			positionMicrosoft = new ArrayList<String>(Arrays.asList("C", "C++"));
			positionApple = new ArrayList<String>(Arrays.asList("Python", "R", "Julia", "Ruby"));
			positionIBM = new ArrayList<String>(Arrays.asList("Java", "Javascript", "SQL"));
			positionAdobe = new ArrayList<String>(Arrays.asList("Scala", "Perl"));
    	   
    	   load ();
    	    
    	   
           try(FileWriter fw = new FileWriter("myfile.txt", true);
           	    BufferedWriter bw = new BufferedWriter(fw);
           	    PrintWriter out = new PrintWriter(bw))
           {   
    	
    	for(String line : skillsandco) 
        {
    			addSkill (line);
    			rastro.add(line);
        }
    	    	
    	compute (); 
    	compute2 ();
    	    	 

    	for (int i = 0; i < rastro.size(); i++)
    		out.print(rastro.get(i) + "#");	
    	
        for (int i = 0; i < rastro.size(); i++)
        	rastro.remove(i);
        	
    	Map<String, String> matches = match(employees, employeesPrefer, employersPrefer);
        for(Map.Entry<String, String> couple:matches.entrySet()){
            out.println(
                    couple.getKey() + " <--> " + couple.getValue());
        }
        

        for(Map.Entry<String, String> couple:matches.entrySet()){
        	
        	String Empresa = couple.getKey();
        	
    		List<String> lista = new ArrayList<String>();
    		lista = employersPrefer.get(Empresa);
        	
        	for(int i = 0 ; i < employersPrefer.size(); i++) {
        		out.print (i + ": " + lista.get(i) + ", " );
        	}
        }
        
        for(Map.Entry<String, String> couple:matches.entrySet()){
        	

        	String Candidato = couple.getValue();
        	
    		List<String> lista = new ArrayList<String>();
    		lista = employeesPrefer.get(Candidato);
        	
        	for(int i = 0 ; i < employeesPrefer.size(); i++) {
        		out.print (i + ": " + lista.get(i) + ", " );
        	}
        }
        
        
        
        	
        out.println("--------------------");
        out.println("Distance to Optimum: " + happinessIndex (matches));
       

        
        
        	} catch (IOException e) {
        	    //exception handling left as an exercise for the reader
        	}
        
    
    }
         
    /* Gale and Shapley implementation */
	private static Map<String, String> match(List<String> employees,
        Map<String, List<String>> employeesPrefer,
        Map<String, List<String>> employersPrefer){
        Map<String, String> engagedTo = new TreeMap<String, String>();
        List<String> freeEmployees = new LinkedList<String>();
        freeEmployees.addAll(employees);
        while(!freeEmployees.isEmpty()){
            String thisEmployee = freeEmployees.remove(0); 
            List<String> thisEmployeePrefers = employeesPrefer.get(thisEmployee);
            for(String employer:thisEmployeePrefers){
                if(engagedTo.get(employer) == null){//employer is free
                    engagedTo.put(employer, thisEmployee); 
                    break;
                }else{
                    String otherEmployee = engagedTo.get(employer);
                    List<String> thisEmployerPrefers = employersPrefer.get(employer);
                   
                    if(thisEmployerPrefers.indexOf(thisEmployee) <
                            thisEmployerPrefers.indexOf(otherEmployee)){
                        engagedTo.put(employer, thisEmployee);
                        freeEmployees.add(otherEmployee);
                        break;
                    }
                }
            }
        }
        return engagedTo;
    }
	
	public static void combination(Object[]  elements, int K){
		 
        // get the length of the array
        // e.g. for {'A','B','C','D'} => N = 4 
        int N = elements.length;
         
        if(K > N){
            System.out.println("Invalid input, K > N");
            return;
        }
        // calculate the possible combinations
        // e.g. c(4,2)
        c(N,K);
         
        // get the combination by index 
        // e.g. 01 --> AB , 23 --> CD
        int combination[] = new int[K];
         
        // position of current index
        //  if (r = 1)              r*
        //  index ==>        0   |   1   |   2
        //  element ==>      A   |   B   |   C
        int r = 0;      
        int index = 0;
         
        while(r >= 0){
            // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
            // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"
             
            // for r = 0 ==> index < (4+ (0 - 2)) = 2
            if(index <= (N + (r - K))){
                    combination[r] = index;
                     
                // if we are at the last position print and increase the index
                if(r == K-1){
                    print(combination, elements, K);
                    index++;                
                }
                else{
                    index = combination[r]+1;
                    r++;                                        
                }
            }
            else{
                r--;
                if(r > 0)
                    index = combination[r]+1;
                else
                    index = combination[0]+1;   
            }           
        }
    }
	
	public static int c(int n, int r){
		int nf=fact(n);
		int rf=fact(r);
		int nrf=fact(n-r);
		int npr=nf/nrf;
		int ncr=npr/rf; 
		
		System.out.println("C("+n+","+r+") = "+ ncr);

		return ncr;
	}
	
	public static int fact(int n)
	{
		if(n == 0)
			return 1;
		else
			return n * fact(n-1);
	}
	

	public static void print(int[] combination, Object[] elements, int number){

		String[] skills = new String [number];
		for(int z = 0 ; z < combination.length;z++){
			skills[z] = (String) elements[combination[z]];
		}
		
		List<String> wordList = Arrays.asList(skills);
		System.out.println(wordList);
		iteration (wordList);
}
    
    /* Main */
    public static void main(String[] args) {
    	    	
        Object[] elements = new Object[] {"C", "C++", "Python", "R", "Julia", "Ruby", "Java", "Javascript", "SQL", "Scala", "Perl"};
        combination(elements,1);
        combination(elements,2);
        combination(elements,3);
        combination(elements,4);
        combination(elements,5);
    	
    }
    
    
}