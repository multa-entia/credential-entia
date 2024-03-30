package ru.multa.entia.credential.impl.data.manager.item;

import ru.multa.entia.credential.api.data.manager.item.ManagerDatum;
import ru.multa.entia.results.api.repository.CodeRepository;
import ru.multa.entia.results.api.result.Result;
import ru.multa.entia.results.impl.repository.DefaultCodeRepository;
import ru.multa.entia.results.impl.result.DefaultResultBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultManagerDatum implements ManagerDatum {
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

    private DefaultManagerDatum(final Map<String, Object> data) {
        this.data = Collections.unmodifiableMap(data);
    }

    @Override
    public <T> Result<T> get(final String property, final Class<T> type) {
        AtomicReference<T> holder = new AtomicReference<>();
        return DefaultResultBuilder.<T>computeFromCodes(
                holder::get,
                () -> {return !data.containsKey(property) ? CR.get(Code.PROPERTY_IS_ABSENCE) : null;},
                () -> {
                    Object gotten = data.get(property);
                    if (!gotten.getClass().equals(type)) {
                        return CR.get(Code.PROPERTY_HAS_BAD_TYPE);
                    }
                    holder.set(type.cast(gotten));
                    return null;
                }
        );
    }

    // TODO: refact + test
    @Override
    public Result<Object> get(final String property) {
        return data.containsKey(property)
                ? DefaultResultBuilder.<Object>ok(data.get(property))
                : DefaultResultBuilder.<Object>fail(CR.get(Code.PROPERTY_IS_ABSENCE));
    }

    public static class Builder {
        private final Map<String, Object> data = new HashMap<>();

        public Builder property(final String property, final Object value) {
            this.data.put(property, value);
            return this;
        }

        public DefaultManagerDatum build() {
            return new DefaultManagerDatum(data);
        }
    }
}
