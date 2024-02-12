package ru.multa.entia.credential.impl.data.right;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.api.data.usr.UsrEntity;
import ru.multa.entia.credential.impl.data.usr.UsrEntityImpl;

import java.util.Set;

@Document
@Data
public class RightEntityImpl implements RightEntity {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String value;
    @DocumentReference
    private Set<UsrEntity> users;

    public RightEntityImpl() {
    }

    public RightEntityImpl(final ObjectId id, final String value, final Set<UsrEntity> users) {
        this.id = id;
        this.value = value;
        this.users = users;
    }
}
