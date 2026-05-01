import java.time.Duration;
import java.time.LocalDateTime;

public class Camion {
    private String conductor;
    private String placa;
    private int bultos;
    private String destino;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;

    /**
     * Crea un camion con sus datos basicos.
     *
     * @param conductor nombre del conductor
     * @param placa placa del camion
     * @param bultos cantidad de bultos
     * @param destino ciudad de destino
     */
    public Camion(String conductor, String placa, int bultos, String destino) {
        this.conductor = conductor;
        this.placa = placa;
        this.bultos = bultos;
        this.destino = destino;
    }

    /**
     * Devuelve el nombre del conductor.
     *
     * @return nombre del conductor
     */
    public String getConductor() {
        return conductor;
    }

    /**
     * Cambia el nombre del conductor.
     *
     * @param conductor nuevo nombre del conductor
     */
    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    /**
     * Devuelve la placa del camion.
     *
     * @return placa del camion
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Cambia la placa del camion.
     *
     * @param placa nueva placa del camion
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Devuelve la cantidad de bultos.
     *
     * @return cantidad de bultos
     */
    public int getBultos() {
        return bultos;
    }

    /**
     * Cambia la cantidad de bultos.
     *
     * @param bultos nueva cantidad de bultos
     */
    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    /**
     * Devuelve el destino del camion.
     *
     * @return destino del camion
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Cambia el destino del camion.
     *
     * @param destino nuevo destino
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Devuelve la hora de ingreso.
     *
     * @return hora de ingreso
     */
    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    /**
     * Guarda la hora de ingreso.
     *
     * @param horaIngreso hora de ingreso
     */
    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    /**
     * Devuelve la hora de salida.
     *
     * @return hora de salida
     */
    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    /**
     * Guarda la hora de salida.
     *
     * @param horaSalida hora de salida
     */
    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    /**
     * Calcula el tiempo total que el camion estuvo en el patio.
     *
     * @return tiempo en formato HH:MM:SS
     * @throws IllegalStateException si falta la hora de ingreso o de salida
     */
    public String tiempoEnPatio() {
        if (horaIngreso == null || horaSalida == null) {
            throw new IllegalStateException("No se puede calcular el tiempo en patio.");
        }

        Duration duracion = Duration.between(horaIngreso, horaSalida);
        long segundos = duracion.getSeconds();
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long restoSegundos = segundos % 60;

        return String.format("%02d:%02d:%02d", horas, minutos, restoSegundos);
    }

    /**
     * Devuelve una descripcion corta del camion.
     *
     * @return texto con los datos principales del camion
     */
    public String getResumen() {
        return "Placa: " + placa
                + ", Conductor: " + conductor
                + ", Bultos: " + bultos
                + ", Destino: " + destino;
    }
}
