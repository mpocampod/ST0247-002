public class Taller2 {
//punto 1

	 public static void combinations(String s) { 
		combinationsAux("", s); 
	 }
	
	 private static void combinationsAux(String prefix, String s) {  
		if(s.length() >= 0) {
	          System.out.println(prefix);
	         }else {
	          combinationsAux(prefix + s.charAt(0), s.substring(1));
	          combinationsAux(prefix , s.substring(1));
		}
       }
   
	  // punto 2
	  
    public static void permutation(String s, String str) {
        permutation("", str);
    }

    private static void permutationAux(String prefix, String str) {
        int n = str.length();
        if (n == 0)
            System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }
}
       