import java.util.ArrayList;
public class Punto1{
    public ArrayList<String> solucionar(int cantidadDeClientes, Grafo mapa)
    {
        boolean visitados[] = new boolean[cantidadDeClientes];
        ArrayList<String> Ruta = new ArrayList<String>();
        Ruta.add("0");
        int ultimaPosicion = 0;
        while(faltaVisitar(visitados))
        {
            int posicionProxima= encontrarMenorDistancia(ultimaPosicion, visitados, mapa);

            ultimaPosicion=posicionProxima;
            visitados[posicionProxima-1]=true;
            Ruta.add(Integer.toString(ultimaPosicion));

        }
        Ruta.add("0");

        return Ruta;
    }
    public boolean faltaVisitar(boolean[] visitados)
    {
        for(int i =0; i<visitados.length;i++)
        {
            if(!visitados[i])
            {
                return true;
            }
        }
        return false;
    }
    public int encontrarMenorDistancia(int posicion, boolean[] visitados, Grafo mapa)
    {
        ArrayList<Integer> sucesores = mapa.getSuccessors(posicion);
        double menorDistancia= Double.MAX_VALUE;
        int posicionConMenorDistancia= 0;
        for(int sucesor : sucesores)
        {
            double distanciaActual= mapa.getWeight(posicion, sucesor);
            if(distanciaActual<menorDistancia&&sucesor!=0&&!visitados[sucesor-1])
            {
                menorDistancia= distanciaActual;
                posicionConMenorDistancia= sucesor;
            }
        }
        return posicionConMenorDistancia;

    }

}