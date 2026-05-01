import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat FORMATO_NUMERO = new DecimalFormat("#,###");
    private static final Scanner TECLADO = new Scanner(System.in);

    /**
     * Punto de inicio del programa.
     *
     * @param args argumentos de consola
     */
    public static void main(String[] args) {
        PatioCargue patio = new PatioCargue();
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            ejecutarOpcion(opcion, patio);
        } while (opcion != 0);
    }

    /**
     * Muestra el menu principal.
     */
    public static void mostrarMenu() {
        System.out.println();
        System.out.println("========== COOPARROZ ==========");
        System.out.println("1. Registrar ingreso");
        System.out.println("2. Despachar siguiente");
        System.out.println("3. Consultar siguiente en salir");
        System.out.println("4. Listar patio completo");
        System.out.println("5. Capacidad y ocupacion");
        System.out.println("6. Reporte de despachos");
        System.out.println("7. Ejecutar escenario de prueba");
        System.out.println("0. Salir");
        System.out.println("================================");
    }

    private static void ejecutarOpcion(int opcion, PatioCargue patio) {
        switch (opcion) {
            case 1:
                registrarIngreso(patio);
                break;
            case 2:
                despacharCamion(patio);
                break;
            case 3:
                consultarSiguiente(patio);
                break;
            case 4:
                listarPatio(patio);
                break;
            case 5:
                mostrarCapacidad(patio);
                break;
            case 6:
                mostrarReporte(patio);
                break;
            case 7:
                ejecutarEscenarioDePrueba();
                break;
            case 0:
                System.out.println("Programa finalizado.");
                break;
            default:
                System.out.println("Opcion no valida.");
                break;
        }
    }

    private static void registrarIngreso(PatioCargue patio) {
        System.out.println();
        System.out.println("Registro de ingreso");

        String conductor = leerTexto("Conductor: ");
        String placa = leerTexto("Placa: ").toUpperCase();
        int bultos = leerEntero("Bultos: ");
        String destino = leerTexto("Destino: ");

        Camion camion = new Camion(conductor, placa, bultos, destino);

        try {
            patio.registrarIngreso(camion);
            System.out.println("Camion registrado correctamente.");
            mostrarCamion(camion);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void despacharCamion(PatioCargue patio) {
        try {
            Camion camion = patio.despachar();
            System.out.println();
            System.out.println("Camion despachado:");
            mostrarCamion(camion);
            System.out.println("Hora de salida: " + camion.getHoraSalida().format(FORMATO_FECHA));
            System.out.println("Tiempo en patio: " + camion.tiempoEnPatio());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void consultarSiguiente(PatioCargue patio) {
        try {
            Camion camion = patio.consultarSiguiente();
            System.out.println();
            System.out.println("Siguiente camion en salir:");
            mostrarCamion(camion);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarPatio(PatioCargue patio) {
        List<Camion> camiones = patio.listarPatio();

        System.out.println();
        System.out.println("Patio completo");

        if (camiones.isEmpty()) {
            System.out.println("No hay camiones en el patio.");
            return;
        }

        for (int i = 0; i < camiones.size(); i++) {
            System.out.println((i + 1) + ". " + camiones.get(i).getResumen());
        }
    }

    private static void mostrarCapacidad(PatioCargue patio) {
        System.out.println();
        System.out.println("Capacidad maxima: " + patio.getCapacidadMaxima());
        System.out.println("Camiones en el patio: " + patio.getOcupacion());
        System.out.println("Espacios disponibles: " + patio.getEspaciosDisponibles());
    }

    private static void mostrarReporte(PatioCargue patio) {
        List<Camion> despachados = patio.getDespachados();
        Map<String, Integer> totales = patio.getTotalesPorDestino();

        System.out.println();
        System.out.println("Reporte de despachos");

        if (despachados.isEmpty()) {
            System.out.println("Todavia no hay camiones despachados.");
        } else {
            for (int i = 0; i < despachados.size(); i++) {
                Camion camion = despachados.get(i);
                System.out.println((i + 1) + ". " + camion.getResumen());
                System.out.println("   Hora ingreso: " + camion.getHoraIngreso().format(FORMATO_FECHA));
                System.out.println("   Hora salida: " + camion.getHoraSalida().format(FORMATO_FECHA));
                System.out.println("   Tiempo en patio: " + camion.tiempoEnPatio());
            }
        }

        System.out.println("Total despachados: " + despachados.size());

        for (String destino : totales.keySet()) {
            System.out.println("Total de bultos despachados a " + destino + ": " + totales.get(destino));
        }
    }

    private static void ejecutarEscenarioDePrueba() {
        PatioCargue patio = new PatioCargue();

        System.out.println();
        System.out.println("Escenario de prueba obligatorio");

        registrarEscenario(patio, "Pedro Nino", "WXY458", 650, "Bogota");
        registrarEscenario(patio, "Maria Salcedo", "ABC123", 580, "Bucaramanga");
        registrarEscenario(patio, "Luis Quintero", "DEF789", 700, "Villavicencio");

        Camion primerDespacho = patio.despachar();
        System.out.println("Debe salir DEF789: " + primerDespacho.getPlaca());

        registrarEscenario(patio, "Carmen Rojas", "GHI456", 620, "Bogota");
        registrarEscenario(patio, "Jaime Correa", "JKL012", 540, "Bogota");

        Camion siguiente = patio.consultarSiguiente();
        System.out.println("Debe mostrar JKL012: " + siguiente.getPlaca());

        Camion segundoDespacho = patio.despachar();
        System.out.println("Debe salir JKL012: " + segundoDespacho.getPlaca());

        Camion tercerDespacho = patio.despachar();
        System.out.println("Debe salir GHI456: " + tercerDespacho.getPlaca());

        List<Camion> camionesRestantes = patio.listarPatio();
        System.out.println("Quedan " + camionesRestantes.size() + " camiones en el patio.");
        listarPatio(patio);
        mostrarReporte(patio);
        System.out.println();
        System.out.println("Verificacion final:");
        System.out.println("Camiones aun en el patio: " + camionesRestantes.size()
                + " (" + camionesRestantes.get(0).getPlaca() + " en el tope, "
                + camionesRestantes.get(camionesRestantes.size() - 1).getPlaca() + " en el fondo).");
        System.out.println("Camiones despachados durante la sesion: " + patio.getDespachados().size() + ".");
        System.out.println("Total de bultos despachados con destino Bogota: "
                + formatearNumero(patio.getTotalesPorDestino().get("Bogota")) + ".");
        System.out.println("Total de bultos despachados con destino Villavicencio: "
                + formatearNumero(patio.getTotalesPorDestino().get("Villavicencio")) + ".");
        System.out.println("Total de bultos despachados con destino Bucaramanga: "
                + formatearNumero(patio.getTotalesPorDestino().get("Bucaramanga")) + ".");
    }

    private static void registrarEscenario(PatioCargue patio, String conductor, String placa, int bultos, String destino) {
        Camion camion = new Camion(conductor, placa, bultos, destino);
        patio.registrarIngreso(camion);
    }

    private static void mostrarCamion(Camion camion) {
        System.out.println(camion.getResumen());

        if (camion.getHoraIngreso() != null) {
            System.out.println("Hora de ingreso: " + camion.getHoraIngreso().format(FORMATO_FECHA));
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return TECLADO.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = TECLADO.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero.");
            }
        }
    }

    private static String formatearNumero(int numero) {
        return FORMATO_NUMERO.format(numero).replace(',', '.');
    }
}
