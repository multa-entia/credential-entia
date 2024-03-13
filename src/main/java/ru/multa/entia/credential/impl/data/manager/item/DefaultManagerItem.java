package ru.multa.entia.credential.impl.data.manager.item;

import ru.multa.entia.credential.api.data.manager.item.ManagerItem;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;

import java.util.HashMap;
import java.util.Map;

public class DefaultManagerItem implements ManagerItem {
    public enum Code {
        PROPERTY_IS_ABSENCE,
        PROPERTY_HAS_BAD_TYPE
    }

    private static final CodeRepository CR = DefaultCodeRepository.getDefaultInstance();
    static {
        CR.update(Code.PROPERTY_IS_ABSENCE, "credential:manager-item.default:property-is-absence");
        CR.update(Code.PROPERTY_HAS_BAD_TYPE, "credential:manager-item.default:property-has-bad-type");
    }

    private final Map<String, Object> data;

    public static Builder builder() {
        return new Builder();
    }

    private DefaultManagerItem(final Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public <T> Result<T> get(final String property, final Class<T> type) {
        // TODO: !!!
        throw new RuntimeException("");
    }

    public static class Builder {
        private final Map<String, Object> data = new HashMap<>();

        public Builder property(final String property, final Object value) {
            this.data.put(property, value);
            return this;
        }

        public DefaultManagerItem build() {
            return new DefaultManagerItem(data);
        }
    }
}
