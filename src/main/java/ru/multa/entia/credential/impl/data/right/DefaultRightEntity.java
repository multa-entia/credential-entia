package ru.multa.entia.credential.impl.data.right;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.multa.entia.credential.api.data.right.RightEntity;

@Document
@Data
public class DefaultRightEntity implements RightEntity {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String value;

    public DefaultRightEntity() {
    }

    public DefaultRightEntity(final ObjectId id, final String value) {
        this.id = id;
        this.value = value;
    }
}
