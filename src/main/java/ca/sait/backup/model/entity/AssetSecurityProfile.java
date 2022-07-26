package ca.sait.backup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@Table(name = "asset_security_profile")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AssetSecurityProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationships
    @OneToMany(mappedBy = "asset_security_profile", cascade = CascadeType.ALL)
    private List<AssetSecurityApproval> approvalList;

    // Security settings
    @Enumerated(EnumType.STRING)
    private AssetSecurityProfileTypeEnum securityType;

    private String securityConfiguration;

}
