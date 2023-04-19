import java.io.Serializable;

public class Jugador implements Serializable {
    private String Nom;
    private int Trofeus;
    Equip equip;

    public Jugador() {

    }





    public Jugador(String nom, Equip equip,  int trofeus) {
        Nom = nom;
        this.equip = equip;
        Trofeus = trofeus;
    }
        //Getters
    public String getNom() {
        return Nom;
    }

    public int getTrofeus() {
        return Trofeus;
    }

    public Equip GetEquip() {
        return equip;
    }


    //Setters


    public void setNom(String nom) {
        Nom = nom;
    }

    public void setTrofeus(int trofeus) {
        Trofeus = trofeus;
    }

    public void SetEquip(Equip equip) {
        this.equip = equip;
    }
    @Override
    public String toString() {
        return "Jugador { " + "Nom= " + Nom + ','+ "Equip= " +equip+
                ", Trofeus=" + Trofeus +
                '}';
    }

    public static final Jugador Centinela = new Jugador("abc",Equip.OROS, 99999);


    public boolean EsCentinela(){
        return Nom.equals(Centinela.Nom);

    }


}

