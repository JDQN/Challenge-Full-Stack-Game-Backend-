package org.example.cardgame.usecase.gateway.model;

public class CartaMaestra {
    private String id;
    private String nombre;
    private String uri;
    private Integer poder;

	public CartaMaestra(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getPoder() {
        return poder;
    }

    public void setPoder(Integer poder) {
        this.poder = poder;
    }
}
