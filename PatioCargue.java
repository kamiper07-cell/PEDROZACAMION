import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PatioCargue {
    private static final int CAPACIDAD_MAXIMA = 8;

    private Pila<Camion> patio;
    private List<Camion> despachados;

    /**
     * Crea un patio de cargue vacio.
     */
    public PatioCargue() {
        patio = new Pila<Camion>(CAPACIDAD_MAXIMA);
        despachados = new ArrayList<Camion>();
    }

    /**
     * Registra el ingreso de un camion al patio.
     *
     * @param camion camion que va a ingresar
     * @throws IllegalArgumentException si el camion es invalido o la placa ya existe
     * @throws IllegalStateException si el patio esta lleno
     */
    public void registrarIngreso(Camion camion) {
        validarCamion(camion);

        if (patio.size() == CAPACIDAD_MAXIMA) {
            throw new IllegalStateException(
                    "Patio lleno: el camion " + camion.getPlaca() + " debe esperar fuera del patio.");
        }

        if (placaExiste(camion.getPlaca())) {
            throw new IllegalArgumentException("La placa " + camion.getPlaca() + " ya esta dentro del patio.");
        }

        camion.setHoraIngreso(LocalDateTime.now());
        patio.push(camion);
    }

    /**
     * Despacha el camion que esta en el tope.
     *
     * @return camion despachado
     * @throws IllegalStateException si no hay camiones para despachar
     */
    public Camion despachar() {
        try {
            Camion camion = patio.pop();
            camion.setHoraSalida(LocalDateTime.now());
            despachados.add(camion);
            return camion;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No hay camiones para despachar.");
        }
    }

    /**
     * Consulta el camion que saldria a continuacion.
     *
     * @return camion del tope
     * @throws IllegalStateException si el patio esta vacio
     */
    public Camion consultarSiguiente() {
        try {
            return patio.peek();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No hay camiones en el patio.");
        }
    }

    /**
     * Devuelve los camiones del patio desde el tope hasta el fondo.
     *
     * @return lista de camiones en el patio
     */
    public List<Camion> listarPatio() {
        return patio.toList();
    }

    /**
     * Devuelve los camiones despachados durante la sesion.
     *
     * @return lista cronologica de camiones despachados
     */
    public List<Camion> getDespachados() {
        return new ArrayList<Camion>(despachados);
    }

    /**
     * Devuelve la cantidad de camiones en el patio.
     *
     * @return numero de camiones en el patio
     */
    public int getOcupacion() {
        return patio.size();
    }

    /**
     * Devuelve la cantidad de espacios libres.
     *
     * @return espacios disponibles
     */
    public int getEspaciosDisponibles() {
        return CAPACIDAD_MAXIMA - patio.size();
    }

    /**
     * Devuelve la capacidad maxima del patio.
     *
     * @return capacidad maxima
     */
    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }

    /**
     * Cuenta los bultos despachados agrupados por destino.
     *
     * @return mapa con destino y total de bultos
     */
    public Map<String, Integer> getTotalesPorDestino() {
        Map<String, Integer> totales = new LinkedHashMap<String, Integer>();
        totales.put("Bogota", 0);
        totales.put("Bucaramanga", 0);
        totales.put("Villavicencio", 0);

        for (Camion camion : despachados) {
            String destino = normalizarDestino(camion.getDestino());
            int acumulado = 0;

            if (totales.containsKey(destino)) {
                acumulado = totales.get(destino);
            }

            totales.put(destino, acumulado + camion.getBultos());
        }

        return totales;
    }

    private void validarCamion(Camion camion) {
        if (camion == null) {
            throw new IllegalArgumentException("El camion no puede ser nulo.");
        }

        if (estaVacio(camion.getConductor()) || estaVacio(camion.getPlaca()) || estaVacio(camion.getDestino())) {
            throw new IllegalArgumentException("Todos los datos del camion son obligatorios.");
        }

        if (camion.getBultos() < 1 || camion.getBultos() > 700) {
            throw new IllegalArgumentException("Los bultos deben estar entre 1 y 700.");
        }
    }

    private boolean placaExiste(String placa) {
        List<Camion> camiones = patio.toList();

        for (Camion camion : camiones) {
            if (camion.getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }

        return false;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private String normalizarDestino(String destino) {
        String texto = destino.trim().toLowerCase();

        if (texto.equals("bogotá") || texto.equals("bogota")) {
            return "Bogota";
        }

        if (texto.equals("bucaramanga")) {
            return "Bucaramanga";
        }

        if (texto.equals("villavicencio")) {
            return "Villavicencio";
        }

        return destino.trim();
    }
}
