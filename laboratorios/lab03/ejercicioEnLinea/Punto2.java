public class Punto2 {


    public static boolean hayCamino(Grafo mapa, boolean[] visitados, int nodoInicial, int nodoObjetivo)
    {
        visitados[nodoInicial-1]= true; 

        if(nodoInicial==nodoObjetivo)
        {
            return true;
        }

        int i = nodoInicial;
        for (int child :mapa.getSuccessors(i))
        {
            return hayCamino(mapa, visitados, child, nodoObjetivo);
        }
        return false;   
    }

    public static void solucionPunto2(Grafo m)
    {

        boolean[] visitados= new boolean[m.size()];
        if(hayCamino(m, visitados, 1, m.size()))
        {
            Punto1 objetoParaFunciPunto1= new Punto1();
            String solucion = objetoParaFunciPunto1.shortestPath(1, m.size(), m);
            System.out.println(solucion); 
        }
        else{
            System.out.println(-1);
        }
    }

   
    public static void main(String[] args) {
        Grafo graph = new GrafosListas(6);
        graph.addArc(1, 2, 2);
        graph.addArc(2, 5, 5);
        graph.addArc(2, 3, 4);
        graph.addArc(1, 4, 1);
        graph.addArc(4, 3, 3);
        graph.addArc(3, 5, 1);
        graph.addArc(3, 5, 1);
        solucionPunto2(graph);
    }


}