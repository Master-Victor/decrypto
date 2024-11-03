package rest.decrypto.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import rest.decrypto.models.Comitente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Schema(description = "DTO para transferir datos de las estadísticas de un país")
public class StatsDTO {
    private String country;
    private List<MarketStatsDTO> market;

    public static List<StatsDTO> calculateMarketStats(List<Comitente> comitentes) {
        // Agrupar los comitentes por país del mercado asociado
        Map<String, Map<String, Long>> comitentesByCountryAndMarket = comitentes.stream()
                .flatMap(c -> c.getMercados().stream())
                .collect(Collectors.groupingBy(
                        cm -> cm.getMercado().getPais().getNombre(), // Agrupar por país
                        Collectors.groupingBy(
                                cm -> cm.getMercado().getCodigo(), // Agrupar por código de mercado dentro de cada país
                                Collectors.counting() // Contar la cantidad de comitentes en cada mercado
                        )
                ));
        System.out.println(comitentesByCountryAndMarket.toString());
        int activeMercados = comitentesByCountryAndMarket.values().stream()
                .flatMap(map -> map.values().stream()) // Extraer los valores de cada mapa interno
                .mapToInt(Long::intValue) // Convertir cada valor a int
                .sum();

        System.out.println("Mercados activos: " + activeMercados);
        List<StatsDTO> countryStats = new ArrayList<>();

        // Iterar sobre cada país y sus mercados
        for (Map.Entry<String, Map<String, Long>> countryEntry : comitentesByCountryAndMarket.entrySet()) {
            String country = countryEntry.getKey();
            Map<String, Long> markets = countryEntry.getValue();
            System.out.println("Mercados: " + markets.toString());
            // Lista para almacenar los datos de cada mercado en el país
            List<MarketStatsDTO> marketStatsList = new ArrayList<>();

            for (Map.Entry<String, Long> marketEntry : markets.entrySet()) {
                String marketCode = marketEntry.getKey();
                long marketCount = marketEntry.getValue();

                // Calcular el porcentaje basado en el total global de comitentes
                double percentage = ((double) marketCount / activeMercados) * 100;
                System.out.println("Porcentaje: " + percentage);
                // Crear MarketStatsDTO para cada mercado
                MarketStatsDTO marketStatsDTO = new MarketStatsDTO();
                PercentageDTO percentageDTO = new PercentageDTO(String.format("%.2f", percentage));
                marketStatsDTO.setMarketStats(Map.of(marketCode, percentageDTO));

                marketStatsList.add(marketStatsDTO);
            }

            // Crear StatsDTO para el país actual y agregarlo a la lista
            StatsDTO countryStatsDTO = new StatsDTO();
            countryStatsDTO.setCountry(country);
            countryStatsDTO.setMarket(marketStatsList);

            countryStats.add(countryStatsDTO);
        }

        return countryStats;
    }

}