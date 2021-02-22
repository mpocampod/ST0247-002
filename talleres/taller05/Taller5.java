import java.lang.reflect.Array;
import java.util.*;
public class Taller5
{
    
    public static boolean mColoring(Grafo g, int m){
        return mColoring( g, 1, new int[g.size()], m);
    } 
    
        // Puede pintar el grafo, iniciando en 0, con un arreglo de colores 
        // del tamaño de los vértices del grafo, y con m colore
    public static boolean mColoring(Grafo g,int n, int[] array, int m)
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
                    return mColoring(g,n+1,array, m);
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
        GrafosMatriz g = new GrafosMatriz(8);
        g.addArc(1, 2, 1);
        g.addArc(1, 7, 1);
        g.addArc(2, 1, 1);
        g.addArc(2, 7, 1);
        g.addArc(2, 6, 1);
        g.addArc(2, 4, 1);
        g.addArc(2, 8, 1);
        g.addArc(3, 4, 1);
        g.addArc(3, 6, 1);
        g.addArc(3, 7, 1);
        g.addArc(3, 8, 1);
        g.addArc(4, 3, 1);
        g.addArc(4, 2, 1);
        g.addArc(4, 5, 1);
        g.addArc(4, 8, 1);
        g.addArc(4, 7, 1);
        g.addArc(5, 4, 1);
        g.addArc(5, 6, 1);
        g.addArc(5, 8, 1);
        g.addArc(6, 3, 1);
        g.addArc(6, 2, 1);
        g.addArc(6, 5, 1);
        g.addArc(6, 7, 1);
        g.addArc(7, 1, 1);
        g.addArc(7, 6, 1);
        g.addArc(7, 4, 1);
        g.addArc(7, 3, 1);
        g.addArc(7, 2, 1);
        g.addArc(8, 2, 1);
        g.addArc(8, 4, 1);
        g.addArc(8, 3, 1);
        g.addArc(8, 5, 1);
        g.imprimirGrafo();
        
        boolean x = mColoring(g, 3);
        if(x)
        {
            System.out.println("funciona");

        }
        else{
            System.out.println("Fallo");
        }
    }
}