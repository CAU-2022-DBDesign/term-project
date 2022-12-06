package cau.dbd.entity.complaint;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RefundAndExchangeDTO {

    private Long orderId;

    private String itemName;

    private LocalDateTime orderCreateAt;

    private LocalDateTime rneCreateAt;

    private RefundAndExchangeReason reason;

    private String reasonDetail;

    private int quantity;

    private RefundAndExchangeStatus status;
}
