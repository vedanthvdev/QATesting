package automation.context;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class ContextManager {

    private static final ThreadLocal<Map<Class<?>, Object>> contextHolder = new ThreadLocal<>();

    public <T> T getRequiredContext(Class<T> contextType) {
        return getContext(contextType).orElseThrow(() -> new IllegalStateException("Could not find required context"));
    }

    private <T> Optional<T> getContext(Class<T> type) {
        return Optional.ofNullable((T) contextHolder.get().get(type));
    }

    public <T> T getOrCreateContext(Class<T> type, Supplier<T> creator) {
        return (T) contextHolder.get().computeIfAbsent(type, x -> creator.get());
    }

    public <T> void setContext(Class<T> type, T value) {
        contextHolder.get().put(type, value);
    }

    public void removeContext() {
        contextHolder.remove();
    }

    public void resetContext() {
        contextHolder.set(new HashMap<>());
    }
}
