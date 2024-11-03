package rest.decrypto.dtos;
import lombok.Data;

@Data
public class PercentageDTO {
    private String percentage;
    public PercentageDTO(String percentage) {
        this.percentage = percentage;
    }
}