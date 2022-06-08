package pe.todotic.mitiendaapi_s3.model;

import java.util.Scanner;

public class Examen {

    public static void main(String args[]){

/*
        for(int i = 0; i<=12; i++){
            System.out.println("12 * " + i + " = " + 12 * i + "\n");
        }

*/


        Scanner teclado = new Scanner(System.in);
        int n, empnum=0, numemp, i;
        float sue, suerec = 0;
        System.out.println("mummero de empleados: ");
        n= teclado.nextInt();
        for(i=1; i<=n;++i)
        {
            System.out.println("numero de empleado: ");
            numemp = teclado.nextInt();
            System.out.println("sueldo del empleado: ");
            sue = teclado.nextInt();;
            if(sue > suerec){
                suerec = sue;
                empnum = numemp;
            }
        }
        System.out.println("\n el empleado con mayor sueldo fue: \n numero:" + empnum + "\n sueldo:" + suerec);



    }
}
