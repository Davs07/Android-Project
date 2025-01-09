package models;


import android.os.Parcel;
import android.os.Parcelable;

public class Juego implements Parcelable {
    private int numeroJuego;
    private int paresAcertados;

    public Juego(int numeroJuego, int paresAcertados) {
        this.numeroJuego = numeroJuego;
        this.paresAcertados = paresAcertados;
    }

    protected Juego(Parcel in) {
        numeroJuego = in.readInt();
        paresAcertados = in.readInt();
    }

    public static final Creator<Juego> CREATOR = new Creator<Juego>() {
        @Override
        public Juego createFromParcel(Parcel in) {
            return new Juego(in);
        }

        @Override
        public Juego[] newArray(int size) {
            return new Juego[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numeroJuego);
        dest.writeInt(paresAcertados);
    }

    @Override
    public String toString() {
        return "Juego " + numeroJuego + ": " + paresAcertados + " pares acertados";
    }
}