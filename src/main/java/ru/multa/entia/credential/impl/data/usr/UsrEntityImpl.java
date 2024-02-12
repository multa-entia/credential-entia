package ru.multa.entia.credential.impl.data.usr;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import ru.multa.entia.credential.api.data.right.RightEntity;
import ru.multa.entia.credential.api.data.usr.UsrEntity;

import java.util.Set;

@Document
public class UsrEntityImpl implements UsrEntity {
    @Id
    private ObjectId id;
    private String firstName;
    private String paterName;
    private String surname;
    private String email;
    @DocumentReference
    private Set<RightEntity> rights;

    public UsrEntityImpl() {
    }

    public UsrEntityImpl(final ObjectId id,
                         final String firstName,
                         final String paterName,
                         final String surname,
                         final String email,
                         final Set<RightEntity> rights) {
        this.id = id;
        this.firstName = firstName;
        this.paterName = paterName;
        this.surname = surname;
        this.email = email;
        this.rights = rights;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getPaterName() {
        return this.paterName;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<RightEntity> getRights() {
        return this.rights;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPaterName(String paterName) {
        this.paterName = paterName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRights(Set<RightEntity> rights) {
        this.rights = rights;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UsrEntityImpl)) return false;
        final UsrEntityImpl other = (UsrEntityImpl) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$paterName = this.getPaterName();
        final Object other$paterName = other.getPaterName();
        if (this$paterName == null ? other$paterName != null : !this$paterName.equals(other$paterName)) return false;
        final Object this$surname = this.getSurname();
        final Object other$surname = other.getSurname();
        if (this$surname == null ? other$surname != null : !this$surname.equals(other$surname)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$rights = this.getRights();
        final Object other$rights = other.getRights();
        if (this$rights == null ? other$rights != null : !this$rights.equals(other$rights)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UsrEntityImpl;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $paterName = this.getPaterName();
        result = result * PRIME + ($paterName == null ? 43 : $paterName.hashCode());
        final Object $surname = this.getSurname();
        result = result * PRIME + ($surname == null ? 43 : $surname.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $rights = this.getRights();
        result = result * PRIME + ($rights == null ? 43 : $rights.hashCode());
        return result;
    }

    public String toString() {
        return "UsrEntityImpl(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", paterName=" + this.getPaterName() + ", surname=" + this.getSurname() + ", email=" + this.getEmail() + ", rights=" + this.getRights() + ")";
    }
}
