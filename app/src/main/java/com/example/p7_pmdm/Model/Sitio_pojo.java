package com.example.p7_pmdm.Model;


public class Sitio_pojo
{
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private String comentarios;
    private Float valoracion;
    private String ciudad;

    public Sitio_pojo() {
        this.id = null;
        this.nombre = "";
        this.latitud = null;
        this.longitud = null;
        this.comentarios="";
        this.valoracion=null;
        this.ciudad="";
    }

    public Sitio_pojo(String nombre, Double latitud, Double longitud, String comentarios, Float valoracion, String ciudad) {
        this.id = null;
        this.nombre = nombre;
        this.latitud=latitud;
        this.longitud=longitud;
        this.comentarios=comentarios;
        this.valoracion=valoracion;
        this.ciudad=ciudad;
    }

    public Sitio_pojo(Long id, String nombre, Double latitud, Double longitud, String comentarios, Float valoracion, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.latitud=latitud;
        this.longitud=longitud;
        this.comentarios=comentarios;
        this.valoracion=valoracion;
        this.ciudad=ciudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getLatitud() {
        return latitud;
    }
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return longitud;
    }
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    public Float getValoracion() {
        return valoracion;
    }
    public void setValoracion(Float valoracion) {
        this.valoracion = valoracion;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    @Override
    public String toString() {
        return "Lugar {" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", comentarios=" + comentarios +
                ", valoracion=" + valoracion +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
