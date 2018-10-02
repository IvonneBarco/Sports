package com.practicavolley.ennovic.sportscontrol.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Entreno implements Parcelable {

    private String id;
    private String fecha;
    private String hinicio;
    private String hfin;
    private String gps;
    private String gpsrp;
    private String gpsfin;
    private String entrenop;
    private String descripcion;
    private String imagen;
    private String ruta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHinicio() {
        return hinicio;
    }

    public void setHinicio(String hinicio) {
        this.hinicio = hinicio;
    }

    public String getHfin() {
        return hfin;
    }

    public void setHfin(String hfin) {
        this.hfin = hfin;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getGpsrp() {
        return gpsrp;
    }

    public void setGpsrp(String gpsrp) {
        this.gpsrp = gpsrp;
    }

    public String getGpsfin() {
        return gpsfin;
    }

    public void setGpsfin(String gpsfin) {
        this.gpsfin = gpsfin;
    }

    public String getEntrenop() {
        return entrenop;
    }

    public void setEntrenop(String entrenop) {
        this.entrenop = entrenop;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public static Creator<Entreno> getCREATOR() {
        return CREATOR;
    }

    public Entreno() {

    }

    protected Entreno(Parcel in) {

        setFecha(in.readString());
        setHinicio(in.readString());
        setHfin(in.readString());
        setGps(in.readString());
        setGpsrp(in.readString());
        setGpsfin(in.readString());
        setEntrenop(in.readString());
        setDescripcion(in.readString());
        setImagen(in.readString());
        setRuta(in.readString());
    }

    public static final Creator<Entreno> CREATOR = new Creator<Entreno>() {
        @Override
        public Entreno createFromParcel(Parcel in) {
            return new Entreno(in);
        }

        @Override
        public Entreno[] newArray(int size) {
            return new Entreno[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getId());
        dest.writeString(getFecha());
        dest.writeString(getHinicio());
        dest.writeString(getHfin());
        dest.writeString(getGps());
        dest.writeString(getGpsrp());
        dest.writeString(getGpsfin());
        dest.writeString(getEntrenop());
        dest.writeString(getDescripcion());
        dest.writeString(getImagen());
        dest.writeString(getRuta());
    }
}
