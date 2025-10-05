package Demo2.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer { // [cite: 244]
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
}