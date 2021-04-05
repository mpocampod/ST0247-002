public class Punto2 {
    public static void solucionBus(int n, int d, int r, int[] HorasDia, int[] HorasNoche)
    {
        int horasContadas[][] = new int[n][2];
        boolean visitadosDia[] = new boolean[n];
        boolean visitadosNoche[] = new boolean[n];
        for(int i=0;i<HorasDia.length;i++)
        {
            int horaMasCortaDia= horaMasCorta(HorasDia,visitadosDia);
            horasContadas[i][0]=horaMasCortaDia;
        }
        for(int i=0;i<HorasNoche.length;i++)
        {
            int horaMasCortaDia= horaMasCorta(HorasNoche,visitadosNoche);
            horasContadas[i][1]=horaMasCortaDia;
        }
        int pago = calcularPago(horasContadas,d,r);
        System.out.println(pago);
    }

    public static int calcularPago(int[][] horasContadas, int d, int r)
    {
        int pagoMinimo=0;
        for(int i=0;i<horasContadas.length;i++)
        {
            int horasTotalesBusero= horasContadas[i][0]+horasContadas[i][1];
            if(horasTotalesBusero>d)
            {
                pagoMinimo+=(horasTotalesBusero-d)*r;
            }
            
        }
        return pagoMinimo;
    }
    public static  int horaMasCorta(int[]Horario,boolean[]visitados)
    {
        int horaMasCorta=Integer.MAX_VALUE;
        int seleccionado = 0;
        for(int i =0; i<Horario.length;i++)
        {
            if(horaMasCorta>Horario[i]&&!visitados[i])
            {
                horaMasCorta=Horario[i];
                seleccionado=i;
            }
        }
        visitados[seleccionado]=true;
        return horaMasCorta;
    }
    public static void main(String[] args) {
        int horasDia[]= {10,10};
        int horasNoche[]= {10,10};
        solucionBus(2,20,5,horasDia,horasNoche);
    }
}
