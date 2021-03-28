import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;

public class RuteoVehiculosElectricos {

    int n, m, u, breaks;
    double r, speed, Tmax, Smax, st_customer, Q;
    Digraph mapa;
    short tipoEstacion[];
    float pendienteFuncionCarga[];
    String filename;
    Pair<Float, Float>[] coordenadas;

    double tiempoSolucion;

    public RuteoVehiculosElectricos(String filename) {
        this.filename = filename;
        
        String linea;
        String lineaPartida[];
        try (BufferedReader lector = new BufferedReader(new FileReader(filename))){
            
            double[] valores = new double[10];
            for (int i = 0; i < 10; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                valores[i] = Float.parseFloat(lineaPartida[2]);
            }

            n = (int) valores[0];
            m = (int) valores[1];
            u = (int) valores[2];
            breaks = (int) valores[3];
            r = valores[4];
            speed = valores[5];
            Tmax = valores[6];
            Smax = valores[7];
            st_customer = valores[8];
            Q = valores[9];

            lector.readLine();
            lector.readLine();
            lector.readLine();

            coordenadas = new Pair[n];
            mapa = new DigraphAM(n);
            for (int i = 0; i <= m; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3]));
            }
            tipoEstacion = new short[u];
            for (int i = 0; i < u; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3]));
                tipoEstacion[i] = Short.parseShort(lineaPartida[5]);
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    mapa.addArc(i, j, Math.sqrt(
                            Math.pow(coordenadas[i].first - coordenadas[j].first,
                                    2)
                            + Math.pow(coordenadas[i].second - coordenadas[j].second, 2)
                    )
                    );
                }
            }

            pendienteFuncionCarga = new float[3];
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                pendienteFuncionCarga[i] = Float.parseFloat(lineaPartida[3]);
            }
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                pendienteFuncionCarga[i] = Float.parseFloat(lineaPartida[3]) / pendienteFuncionCarga[i];
            }
            tiempoSolucion = Double.MAX_VALUE;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String toString() {
        return "RuteoVehiculosElectricos{" + "r=" + r + ", speed=" + speed + ", Tmax=" + Tmax + ", Smax=" + Smax + ", st_customer=" + st_customer + ", Q=" + Q + ", tiempoSolucion=" + tiempoSolucion + '}';
    }

    public ArrayList<ArrayList<String>> solucionar()
    {
        boolean visitados[] = new boolean[m];
        int ultimaPosicion = 0;
        double tiempoGastado=0;
        ArrayList<ArrayList<String>> todasLasRutas = new ArrayList<ArrayList<String>>();
        ArrayList<String> Ruta = new ArrayList<String>();
        Ruta.add("{0,0.0}");
        while(faltaVisitar(visitados))
        {
            int posicionProxima= encontrarMenorDistancia(ultimaPosicion, visitados);
            tiempoGastado += (mapa.getWeight(ultimaPosicion, posicionProxima)/speed)+st_customer;
            double tiempoADeposito= mapa.getWeight(posicionProxima, 0)/speed;
            if(tiempoADeposito+tiempoGastado<=10)
            {
                ultimaPosicion=posicionProxima;
                visitados[posicionProxima-1]=true;
                Ruta.add("{"+ultimaPosicion+",0.5}");
            }
            else
            {
                Ruta.add("{0,0.0}");
                ArrayList<String> RutaTemp= new ArrayList<>(Ruta);
                todasLasRutas.add(RutaTemp);
                ultimaPosicion=0;
                tiempoGastado=0;
                Ruta.clear();
                Ruta.add("{0,0.0}");
            }
        }
        Ruta.add("{0,0.0}");
        todasLasRutas.add(Ruta);
        return todasLasRutas;
        


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

    public int encontrarMenorDistancia(int posicion, boolean[] visitados)
    {
        ArrayList<Integer> sucesores = mapa.getSuccessors(posicion);
        double menorDistancia= Double.MAX_VALUE;
        int posicionConMenorDistancia= 0;
        for(int sucesor : sucesores)
        {
            double distanciaActual= mapa.getWeight(posicion, sucesor);
            if(distanciaActual<menorDistancia&&sucesor<=m&&sucesor!=0&&!visitados[sucesor-1])
            {
                menorDistancia= distanciaActual;
                posicionConMenorDistancia= sucesor;
            }
        }
        return posicionConMenorDistancia;

    }
    public static void main(String[] args) {
        RuteoVehiculosElectricos prueba1 = new RuteoVehiculosElectricos("Prueba1.txt");
        ArrayList<ArrayList<String>> resultado = prueba1.solucionar();
        String respuesta="[";
        for(ArrayList<String> Ruta: resultado)
        {
            for(String parada: Ruta)
            {
                respuesta+=parada+",";
            }
            respuesta+="],[";
        }
        respuesta+="]";
        System.out.println(respuesta);
    }
}