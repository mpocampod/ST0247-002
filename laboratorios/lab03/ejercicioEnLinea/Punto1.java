import java.util.ArrayList;
public class Punto1 {
    public static String shortestPath(int inicio, int fin, Grafo graph)
    {
        return shortestPathAux(inicio, fin, ""+inicio,Integer.MAX_VALUE,graph,"");
        
        
    }
    private static String shortestPathAux(int inicio, int fin, String temporal,int caminoMenor, Grafo graph,String solucion)
    {
        if(inicio==fin)
        {
            solucion=temporal;
            caminoMenor=distanciaHastaAhora(temporal, graph);
        }
        ArrayList<Integer> sucesores = graph.getSuccessors(inicio);
        
        if(sucesores.isEmpty()&&inicio!=fin)
        {
            return "";
        }
        for(int x: sucesores)
        {
            if(x!=inicio)
            {

                if(distanciaHastaAhora(temporal+x, graph)<caminoMenor)
                {
                    String solucionActual= shortestPathAux(x, fin,temporal+x,caminoMenor, graph,solucion);
                
                    if(distanciaHastaAhora(solucionActual,graph)<caminoMenor)
                    {
                        solucion=solucionActual;
                        if(!solucion.equals(""))
                        {
                            caminoMenor= distanciaHastaAhora(solucion, graph);
                        }
                    
                    }
                }
            }
            
        }
        return solucion;
    }
    public static int distanciaHastaAhora(String solucion,Grafo graph)
    {
        int distanciaHastaAhora=0;
        for(int i =0; i< solucion.length()-1;i++)
        {
            distanciaHastaAhora+=graph.getWeight(Character.getNumericValue(solucion.charAt(i)),Character.getNumericValue(solucion.charAt(i+1)));
        }   
        return distanciaHastaAhora;
    }
    public static void main(String[] args) {
        Grafo graph = new GrafosListas(8);
        graph.addArc(1, 2, 1);
        graph.addArc(1, 3, 2);
        graph.addArc(1, 4, 5);
        graph.addArc(2, 5, 4);
        graph.addArc(2, 6, 11);
        graph.addArc(3, 5, 9);
        graph.addArc(3, 6, 5);
        graph.addArc(3, 7, 16);
        graph.addArc(4, 7, 2);
        graph.addArc(5, 8, 18);
        graph.addArc(6, 8, 13);
        graph.addArc(7, 8, 2);
        String solucion= shortestPath(1, 8, graph);
        System.out.print(solucion);
    }
}
