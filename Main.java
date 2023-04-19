import java.io.*;

import java.util.Objects;
/*
*Data de Creació:07/04/23
*@Khaoula.Ikkene
* Programa: Simulació d'una estadística d'un vídeo joc massiu.
 */

public class Main {
    String Jugador;

     final String file = "jugadors.dat";

  final Menu menu = new Menu();
    LT tec = new LT();

    Equip equip;
    final Integer[] opcions = {0,1,2,3,4,5,6};
    final Integer[] OrdreEquip = {1,2,3,4};
    Jugador j = new Jugador();
   private final fitxer f = new fitxer();

    public void Start() throws IOException, ClassNotFoundException {
        f.JugadorsAleatoris();// generalitza jugadors aleatoris
       menu.MenuPrincipal();
        boolean sortir = false;
        while (!sortir) {
            System.out.println("Opció?: ");
            int opcio = LlegirOpcio();
            switch (opcio) {
                case 0:
                    System.exit(0); // 0 indica que el programa s'ha executat sense errors
                    sortir = true;
                     case 1 :
                    if (!(f.NumObjects(file)>200)){// maxim 200 jugadors
                        Inserir();
                    }else{
                   System.out.println("Has arribat al máxim numero de jugadors. No pots afegir més!!");
                    }
                    break;
                    case 2 :
                            f.Mostrarcontinugt(file);
                            break;
                    case 3 :
                            Estadistiques(file);
                            break;
                    case 4 :
                            f.SepararJug();
                            break;
                        case 5 :
                            f.CotingutPerEquip();
                            break;
                case 6:
                    for(Equip e:Equip.values()){
                        Estadistiques(e.name()+".dat");
                        System.out.println("-----------------");
                        System.out.println();
                    }
                    break;
            }

        }

    }

    public void Estadistiques(String fichero){
        System.out.println("Nom del fitxer: " + fichero);
        System.out.println("Nombre d'objectes del fitxer:" +f.NumObjects(fichero));
        if(f.NumObjects(fichero)!=0) {
            System.out.println("Nombre total de trofeus: " + f.SumatoriTrofeus(fichero));
            System.out.println("----");
            f.MitjanaArit(fichero);
            System.out.println("----");
            System.out.println("la desviació estandard és : " + DesvEstad(fichero));
            System.out.println("----");
            MillorsJugs(fichero);
        }
    }


    public String InserirJugador(){
        System.out.println("Nom del jugador: ");
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Jugador = br.readLine();
            while (!f.ExisteixJugador(Jugador)){
                System.out.println("el jugador no existex en el llistat de noms");
                System.out.print("Nom del jugador: ");
                Jugador = br.readLine();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return Jugador;

    }



    public void Inserir() {//insereix les dates del jugador en el fitxer final
        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jugadors.dat",true)); // escritura
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("jugadors.dat"));
            Jugador[] ArrayObjetos = new Jugador[f.NumObjects("jugadors.dat")];
            for (int i = 0; i < ArrayObjetos.length; i++){
                ArrayObjetos[i]=(Jugador) ois.readObject(); // guardar els jugadors en [] sense sentinella
            }
            ObjectOutputStream oost = new ObjectOutputStream(new FileOutputStream("jugadors.dat")); // sobreescritura
            for (int i = 0; i<ArrayObjetos.length; i++){
                oost.writeObject(ArrayObjetos[i]); //copiar els jugadors en el fitxer jugadors sense sentinella
            }

            System.out.print("Quants jugadors vols afegir?: ");
            int n = LlegirNum();
                for (int i = 1; i <= n; i++) {
                    Jugador jug = new Jugador(InserirJugador(), InserirEquip(), InserirTrofeus());
                    oos.writeObject(jug);
                }

                oos.writeObject(j.Centinela);
                oos.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


        //Metodes de verificacions de les opcions/entrades de l'usuari----------------
    public boolean ExistexOpcio( Integer a, Integer[] array) {// verifica les opcions del menu
        for (Integer integer : array) {
            if ((Objects.equals(integer, a))) {
                return true;
            }
        }
        return false;
    }


    public int LlegirOpcio() {
        Integer x = tec.llegirSencer();
        while (x== null || !ExistexOpcio(x, opcions)){
            System.out.println("Error");
            System.out.print("Opció:");
            x = tec.llegirSencer();
        }
        return x;
    }

    public int LlegirNum(){//verifica el num de trofeus
       Integer x = tec.llegirSencer();
       while (x == null || x<=0){
           System.out.println("Error");
           System.out.print("Opció: ");
           x = tec.llegirSencer();
       }
       return x;
    }

    public int Vequip(){
        System.out.print("Equip:");
        Integer x = tec.llegirSencer();
        while (x == null || !ExistexOpcio(x, OrdreEquip)){
            System.out.println("Error");
            System.out.print("Equip:");
            x = tec.llegirSencer();
        }
        return x;
    }
        public Equip InserirEquip() {
            System.out.println("1.BASTOS \n2.COPES \n3.ESPASES \n4.OROS");
            int NumEquip=Vequip();
            switch (NumEquip) {
                case 1 -> equip = Equip.BASTOS;
                case 2 -> equip = Equip.COPES;
                case 3 -> equip = Equip.ESPASES;
                case 4 -> equip = Equip.OROS;

            }

        return equip;
    }

    public double DesvEstad(String fichero) {
        double desviacio = 0;
        double Sumatorio = 0;
        int[] Trofeus = f.TotalTrofeus(fichero); //Els trofeus estan guardats en [] Trofeus
        double totalTrofeus = f.SumatoriTrofeus(fichero); // Sumatori de tots els trofeus
        double N = Trofeus.length;
        double mitjana = totalTrofeus / N;
            for (int i = 0; i < Trofeus.length; i++) {
                Sumatorio += Math.pow((Trofeus[i] - mitjana), 2);
            }
             desviacio = Math.sqrt(Sumatorio / N);
        return desviacio;
    }

    public int InserirTrofeus(){
        System.out.print("Trofeus: ");
        Integer x = tec.llegirSencer();
        while (x == null || x<0){
            System.out.println("Error");
            System.out.print("Trofeus: ");
            x = tec.llegirSencer();
        }
        return x;

    }
    public void MillorsJugs(String fichero) {
        Jugador[] array = new Jugador[f.NumObjects(fichero)];

            try {
                FileInputStream fis = new FileInputStream(fichero);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Jugador jug = (Jugador) ois.readObject();
                while (!jug.EsCentinela()) {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = jug;
                        jug = (Jugador) ois.readObject();
                    }
                }
                for (int i = 0; i < array.length - 1; i++) {//ordena els jugadors de forma ascendent
                    for (int j = 0; j < array.length - 1; j++) {
                        if (array[j].getTrofeus() > array[j + 1].getTrofeus()) {
                            Jugador temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;

                        }
                    }
                }
                while(array.length>=3){// si no hi ha com a mínim 3 jugadors no es pot calcular els tres millors jugadors
                System.out.println("El primer millor jugador és:" + array[array.length - 1]);
                System.out.println("El segon millor jugador és:" + array[array.length - 2]);
                System.out.println("El tercer millor jugador és:" + array[array.length - 3]);
                break;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }


    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        (new Main()).Start();
    }
}