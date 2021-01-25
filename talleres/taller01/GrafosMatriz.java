/**
 * Implementacion de un grafo dirigido usando matrices de adyacencia
 *
 * @author Jose Manuel Fonseca, Santiago Puerta
 */
import java.util.ArrayList;
public class GrafosMatriz extends Grafo{

	/**
	* Constructor para el grafo dirigido
	* @param size el numero de vertices que tendra el grafo dirigido
	*
	*/
	int[][] mat;
	public GrafosMatriz(int size) {
        super(size);
        
		mat = new int[size+1][size+1];
		for(int i=1; i<=size; i++){
            
            mat[i][0] = i;
            mat[0][i] = i;
        }
	}

	/**
	* Metodo para añadir un arco nuevo, donde se representa cada nodo con un entero
	* y se le asigna un peso a la longitud entre un nodo fuente y uno destino	
	* @param source desde donde se hara el arco
	* @param destination hacia donde va el arco
	* @param weight el peso de la longitud entre source y destination
	*/
	public void addArc(int source, int destination, int weight) {
		mat[source][destination] = weight;
	}

	/**
	* Metodo para obtener una lista de hijos desde un nodo, es decir todos los nodos
	* asociados al nodo pasado como argumento
	* @param vertex nodo al cual se le busca los asociados o hijos
	* @return todos los asociados o hijos del nodo vertex, listados en una ArrayList
	*/
	public ArrayList<Integer> getSuccessors(int vertex) {
		ArrayList<Integer> np= new ArrayList<>();
     	for(int i=1; i<=size; i++){
        	if(mat[vertex][i]!=  0 ){
         		np.add(mat[0][i]);
        	}
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
		return mat[source][destination];
	}

}
