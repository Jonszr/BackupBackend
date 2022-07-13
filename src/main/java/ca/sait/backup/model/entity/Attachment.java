package ca.sait.backup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Builder
@Data
@Table(name = "attachment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Enumerated(EnumType.ORDINAL)
    private AttachmentTypeEnum type;
    private String url_location;
}
