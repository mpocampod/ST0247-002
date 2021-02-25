package Punto2;
import java.lang.reflect.Array;
import java.util.*;
public class Punto2lab
{
    
    public static boolean biColoring(Grafo g){
        return biColoring( g, 1, new int[g.size()], 2);
    } 
    
        // Puede pintar el grafo, iniciando en 0, con un arreglo de colores 
        // del tamaño de los vértices del grafo, y con m colore
    public static boolean biColoring(Grafo g,int n, int[] array, int m)
    {
        
        if (n > array.length)
        {
            return true;
        }
        
        else{
            for(int i =0;i<m;i++)
            {
                if(isSafe(g, n, array,i))
                {
                    array[n-1]=i;
                    return biColoring(g,n+1,array, m);
                }
            }
            return false;
            
        }
        
    }
    private static boolean isSafe(Grafo g, int v, int[] colors, int c) {
        
        for(int i=1;i<v;i++)
        {
            ArrayList<Integer> sucesores= g.getSuccessors(i);
            if(sucesores.contains(v)&&colors[i-1]==c)
            {
                return false;
            }

        }
        return true;
	}

    public static void main(String[] args) {
        GrafosMatriz g = new GrafosMatriz(9);
        g.addArc(1, 2, 1);
        g.addArc(2, 1, 1);
        g.addArc(1, 3, 1);
        g.addArc(3, 1, 1);
        g.addArc(1, 4, 1);
        g.addArc(4, 1, 1);
        g.addArc(1, 5, 1);
        g.addArc(5, 1, 1);
        g.addArc(6, 1, 1);
        g.addArc(1, 6, 1);
        g.addArc(1, 7, 1);
        g.addArc(7, 1, 1);
        g.addArc(1, 8, 1);
        g.addArc(8, 1, 1);
        g.addArc(1, 9, 1);
        g.addArc(1, 9, 1);
        
        
        
        boolean x =biColoring(g);
        if(x)
        {
            System.out.println("BICOLORABLE");

        }
        else{
            System.out.println("NOT BICOLORABLE");
        }
    }
}