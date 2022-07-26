package ca.sait.backup.model.entity;

import lombok.*;

import javax.mail.Folder;
import javax.persistence.*;

@Builder
@Data
@Table(name = "asset")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "assetfolder_id")
    private AssetFolder parent;

    @OneToOne
    @JoinColumn(name = "asset_security_profile_id")
    private AssetSecurityProfile securityProfile;

    private String assetName;
    private String assetType;
    private String assetValue;

}
