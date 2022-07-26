package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.AssetSecurityProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssetSecurityProfileRepository extends JpaRepository<AssetSecurityProfile, Long> {

}
