package com.hipocrates.hipocratesapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "registros_salud")
public class RegistroSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pacienteNombre;

    @Column(nullable = false)
    private String tipoRegistro; // "FISICO" o "EMOCIONAL"

    @Column(nullable = false)
    private String sintomaOEmocion;

    private Integer intensidad; // Escala del 1 al 10

    private Double temperatura;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @ElementCollection
    @CollectionTable(name = "registro_etiquetas", joinColumns = @JoinColumn(name = "registro_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas;

    public RegistroSalud() {}

    public RegistroSalud(Long id, String pacienteNombre, String tipoRegistro, String sintomaOEmocion, Integer intensidad, Double temperatura, LocalDate fechaRegistro, List<String> etiquetas) {
        this.id = id;
        this.pacienteNombre = pacienteNombre;
        this.tipoRegistro = tipoRegistro;
        this.sintomaOEmocion = sintomaOEmocion;
        this.intensidad = intensidad;
        this.temperatura = temperatura;
        this.fechaRegistro = fechaRegistro;
        this.etiquetas = etiquetas;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }
    public String getTipoRegistro() { return tipoRegistro; }
    public void setTipoRegistro(String tipoRegistro) { this.tipoRegistro = tipoRegistro; }
    public String getSintomaOEmocion() { return sintomaOEmocion; }
    public void setSintomaOEmocion(String sintomaOEmocion) { this.sintomaOEmocion = sintomaOEmocion; }
    public Integer getIntensidad() { return intensidad; }
    public void setIntensidad(Integer intensidad) { this.intensidad = intensidad; }
    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }
}