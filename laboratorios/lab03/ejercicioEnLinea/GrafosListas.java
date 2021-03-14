/**
 * Implementacion de un grafo dirigido usando matrices de adyacencia
 *
 * @author Santiago Puerta, Jose Manuel Fonseca
 */
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
public class GrafosListas extends Grafo{

	/**
	* Constructor para el grafo dirigido
	* @param size el numero de vertices que tendra el grafo dirigido
	*
	*/
	ArrayList<LinkedList<Pair<Integer,Integer>>> Lista = new ArrayList<LinkedList<Pair<Integer,Integer>>>();
	public GrafosListas(int size) {
        super(size);

        for (int i = 0; i < size + 1; i++) {
            Lista.add(new LinkedList<Pair<Integer,Integer>>());
        }
    }

	/*
	* Metodo para añadir un arco nuevo, donde se representa cada nodo con un entero
	* y se le asigna un peso a la longitud entre un nodo fuente y uno destino	
	* @param source desde donde se hara el arco
	* @param destination hacia donde va el arco
	* @param weight el peso de la longitud entre source y destination
	*/
	public void addArc(int source, int destination, int weight) {
        Lista.get(source).add(new Pair<Integer,Integer>(destination,weight));
    }
	

	/**
	* Metodo para obtener una lista de hijos desde un nodo, es decir todos los nodos
	* asociados al nodo pasado como argumento
	* @param vertex nodo al cual se le busca los asociados o hijos
	* @return todos los asociados o hijos del nodo vertex, listados en una ArrayList
	*/
	public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList<Integer> np= new ArrayList<>();
		for(Pair<Integer,Integer> i : Lista.get(vertex))
		{
			np.add(i.getElement0());
		}
        return np;
	}

	/**
	* Metodo para obtener el peso o longitud entre dos nodos
	* 
	* @param source desde donde inicia el arco
	* @param destination  donde termina el arco
	* @return un entero con dicho peso
	*/	
	public int getWeight(int source, int destination) {
        int result = 0;
        for(int i=0; i<=Lista.get(source).size();i++)
        {
            if(Lista.get(source).get(i).getElement0()==destination)
            {
                return result = Lista.get(source).get(i).getElement1();
            }
            
        }
        return 0;
		
	}

}
