import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import java.util.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

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
        double bateriaRestante = Q;
        Ruta.add("{0,0.0}");
        int elementosFaltantes =m;
        int numeroCarros=0;
        while(elementosFaltantes>0)
        {
            int posicionProxima= encontrarMenorDistancia(ultimaPosicion, visitados);
            double tiempoADeposito= mapa.getWeight(posicionProxima, 0)/speed;
            double tiempoHipotetico = tiempoGastado +(mapa.getWeight(ultimaPosicion, posicionProxima)/speed)+st_customer;
            double bateriaParaLlegarADeposito = (mapa.getWeight(posicionProxima, 0)*(r));
            int proximaEstacionCarga = encontrarMenorDistanciaEstacionCarga(ultimaPosicion);
            

            
            if(tiempoADeposito+tiempoHipotetico<=Tmax&&bateriaRestante>bateriaParaLlegarADeposito)
            {
                tiempoGastado += (mapa.getWeight(ultimaPosicion, posicionProxima)/speed)+st_customer;
                bateriaRestante = bateriaRestante - (mapa.getWeight(ultimaPosicion, posicionProxima)*(r));
                ultimaPosicion=posicionProxima;
                visitados[posicionProxima-1]=true;
                elementosFaltantes--;
                Ruta.add("{"+ultimaPosicion+",0.5}");
    
            }
            else if(bateriaRestante<bateriaParaLlegarADeposito)
            {

                    Pair<Double,Double> parejaTiempoCargaYNuevaCarga = cargarBateria(proximaEstacionCarga,Tmax-tiempoGastado,bateriaRestante);
                    Ruta.add("{"+ultimaPosicion+","+parejaTiempoCargaYNuevaCarga.first+"}");
                    bateriaRestante = parejaTiempoCargaYNuevaCarga.second;
                    tiempoGastado+=parejaTiempoCargaYNuevaCarga.first;
                    ultimaPosicion= proximaEstacionCarga;
                
            }
            else
            {
                Ruta.add("{0,0.0}");
                ArrayList<String> RutaTemp= new ArrayList<>(Ruta);
                todasLasRutas.add(RutaTemp);
                ultimaPosicion=0;
                tiempoGastado=0;
                bateriaRestante = Q;
                Ruta.clear();
                Ruta.add("{0,0.0}");
                numeroCarros++;
            }
        }
        Ruta.add("{0,0.0}");
        System.out.println(numeroCarros);
        todasLasRutas.add(Ruta);
        return todasLasRutas;
        


    }

    public Pair<Double,Double> cargarBateria(int estacion, double tiempoRestante,double bateriaRestante)
    {
        double tiempoDeCarga =0;
        short tipoEstacionActual = tipoEstacion[estacion-m-1];
        switch (tipoEstacionActual){
            case 0: 
            if(bateriaRestante>Q*0.4)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.6)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.6;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.7;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[0];
                    bateriaRestante = Q;
                }

            }
            else if(bateriaRestante>Q*0.25)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.6)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.6;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.7;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.9)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.9;
                }

            }
            else if(bateriaRestante>Q*0.1)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.5)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.5;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.6)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.65;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.8)/pendienteFuncionCarga[0];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[0];
                    bateriaRestante = Q*0.8;
                }

            }
            break;
            case 1:
            if(bateriaRestante>Q*0.4)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.7;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.72)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.72;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.95)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.95;
                }
            }
            else if(bateriaRestante>Q*0.25)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.6)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.6;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.7;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.87)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.87;
                }

            }
            else if(bateriaRestante>Q*0.1)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.7;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.72)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*0.72;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.85)/pendienteFuncionCarga[1];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[1];
                    bateriaRestante = Q*85;
                }

            }
            break;

            case 2:
            if(bateriaRestante>Q*0.4)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.65)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.65;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.7;
                }
            

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.8)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.4)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.8;
                }
            }
            else if(bateriaRestante>Q*0.25)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.65)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.65;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*0.7)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.7;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.75)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.25)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.75;
                }

            }
            else if(bateriaRestante>Q*0.1)
            {
                if(tiempoRestante<=3)
                {
                    tiempoDeCarga = (Q*0.62)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.62;
                }

                else if(tiempoRestante<=5)
                {
                    tiempoDeCarga = (Q*7)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.7;
                }
                

                else if (tiempoRestante<=8)
                {
                    tiempoDeCarga = (Q*0.72)/pendienteFuncionCarga[2];
                    tiempoDeCarga = tiempoDeCarga - (Q*0.1)/pendienteFuncionCarga[2];
                    bateriaRestante = Q*0.72;
                }

            }
                break;

            default:
            tiempoDeCarga=0;
            break;
        }
        Pair<Double,Double> parejaTiempoCargaYNuevaCarga = new Pair<Double,Double>(tiempoDeCarga, bateriaRestante);
        return parejaTiempoCargaYNuevaCarga;
    }
    
    public int encontrarMenorDistanciaEstacionCarga(int ultimaPosicion)
    {
        int estacionDeCargaConMenorDistancia= Integer.MAX_VALUE;
        ArrayList<Integer> sucesores = mapa.getSuccessors(ultimaPosicion);
        double menorDistancia= Double.MAX_VALUE;
        
        for(int sucesor : sucesores)
        {
            double distanciaActual= mapa.getWeight(ultimaPosicion, sucesor);
            if(distanciaActual<menorDistancia&&sucesor>m&&sucesor!=0)
            {
                menorDistancia= distanciaActual;
                estacionDeCargaConMenorDistancia= sucesor;
            }
        }
        return estacionDeCargaConMenorDistancia;
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
        
            RuteoVehiculosElectricos prueba1 = new RuteoVehiculosElectricos("DataSet4.txt");
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
            
        System.gc();
        System.out.println("Total memory usage: "+(double)((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024)/1024+" MB");
        

    }
}