import java.io.*;
import java.util.Random;

public class fitxer {
    Random rn = new Random();


    private static ObjectOutputStream oos[] = new ObjectOutputStream[Equip.values().length];

    public fitxer(){

    }

    public boolean ExisteixJugador(String player) {
        try {
            FileReader file = new FileReader("LlistatNoms.txt");
            BufferedReader br = new BufferedReader(file);
            String s = br.readLine();
            while (br.ready()){
                if (!s.equals(player)) {//cerca el nom del jugador en el fitxer
                    s = br.readLine();
                }
                if (s.equals(player)){
                    return true;
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return false;
    }

//-------------------------------------------------------------------------------------------------------
                                                    // Aquests metodes s'han emprat per generar jugadors incials aleatoris
    public void JugadorsAleatoris() {// s'ha usat per generar jugadors aleatoris
        try{
            FileOutputStream fis = new FileOutputStream("jugadors.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fis);

           for (int i = 1; i<23; i++){
                Jugador j = new Jugador(JugadorAleatori(), Equipaleatori(), Trofeusaltearoi());
                oos.writeObject(j);
            }
            oos.writeObject(Jugador.Centinela);
            oos.close();
            fis.close();
        }catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Equip Equipaleatori(){
        int number = rn.nextInt(4);
        for(Equip e:Equip.values()){
            return  Equip.values()[number];
        }
        return null;
    }

    public int Trofeusaltearoi(){
      return rn.nextInt(11);
    }
        public String JugadorAleatori(){
            String s = "";
            try {
                FileReader file = new FileReader("LlistatNoms.txt");
                BufferedReader br = new BufferedReader(file);
                FileInputStream fis = new FileInputStream("jugadors.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                int number = rn.nextInt(1,511);
                int posicion=1;
                s = br.readLine();
                while(br.ready()) {
                    s = br.readLine();
                    posicion++;
                    if (posicion == number){
                        return s;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return s;

    }

//-------------------------------------------------------------------------------------------------------
    public void Mostrarcontinugt(String fichero)  {
        String s = "";
        try {
            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Jugador j = (Jugador) ois.readObject();
            while (!j.EsCentinela()){
                s += j;
                s += "\n";
                j= (Jugador) ois.readObject();
            }
            System.out.println(s);
            ois.close();
            fis.close();

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void CotingutPerEquip(){
        for(Equip e:Equip.values()){
            System.out.println(e.name() + ":" );
            Mostrarcontinugt(e.name()+".dat");
        }
    }

    public int NumObjects(String fichero){
        int x = 0;
        try {

            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Jugador j = (Jugador) ois.readObject();
            while (!j.EsCentinela()){
                j= (Jugador) ois.readObject();
                x++;
            }
            ois.close();
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return x;
    }

    public int SumatoriTrofeus(String fichero){
        int TrofeusTotal=0;

        try {
            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Jugador j = (Jugador) ois.readObject();
            while (!j.EsCentinela()){
                TrofeusTotal += j.getTrofeus(); // suma els trofeus i els guarda en TrofeusTotal
                 j = (Jugador) ois.readObject();
            }
            ois.close();
            fis.close();
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return TrofeusTotal;
    }


    public int[] TotalTrofeus(String fichero){//guarda en Trofeus[] els trofeus dels jugadors
        int Trofeus[] = new int[NumObjects(fichero)];
        try {

            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Jugador j = (Jugador) ois.readObject();
            while (!j.EsCentinela()){
                for(int i = 0; i<Trofeus.length; i++){
                    Trofeus[i] = j.getTrofeus();
                    j = (Jugador) ois.readObject();
                }
                break;
            }
            ois.close();
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Trofeus;
    }


    public void MitjanaArit(String fichero){
        double N = SumatoriTrofeus(fichero);
        try {
            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Jugador j = (Jugador) ois.readObject();
            while (!j.EsCentinela()){
                double temp = j.getTrofeus()/N;
                System.out.println("La mitjana aritmetica del "+ j + " és : " + temp);
                j= (Jugador) ois.readObject();
            }
            ois.close();
        }catch(ClassNotFoundException | IOException e){
            System.out.println(e.getMessage());

        }

    }


    public void SepararJug() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("jugadors.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        for(Equip e:Equip.values()){
            FileOutputStream fos = new FileOutputStream(e.name() + ".dat");//crear 4 fitxers amb els noms dels equips
            oos[e.ordinal()]=new ObjectOutputStream(fos);
        }
        Jugador jug = (Jugador) ois.readObject();
        while(!jug.EsCentinela()){
            Equip e = jug.GetEquip(); //llegir el nom de l'equip
            oos[e.ordinal()].writeObject(jug);// escriure el jugador en el fitxer corresponent
            jug = (Jugador) ois.readObject();

        }
        tancaFitxer();
    }

    public void tancaFitxer() throws IOException {
        Jugador j = new Jugador();
        for (Equip e: Equip.values()) {
            oos[e.ordinal()].writeObject(j.Centinela);
            oos[e.ordinal()].close();
        }
    }

}
