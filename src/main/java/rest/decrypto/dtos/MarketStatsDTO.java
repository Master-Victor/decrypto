package rest.decrypto.dtos;

import lombok.Data;
import java.util.Map;

@Data
public class MarketStatsDTO {
    private Map<String, PercentageDTO> marketStats;
}