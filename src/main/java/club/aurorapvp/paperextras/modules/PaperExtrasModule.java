package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import org.bukkit.event.HandlerList;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.*;

public interface PaperExtrasModule {

    Reflections reflections = new Reflections("club.aurorapvp.paperextras.modules");

    /**
     * Enables the feature, registers the listeners.
     */
    void enable();

    /**
     * @return true if the feature should be enabled
     */
    boolean shouldEnable();

    static void reloadModules() {

        HandlerList.unregisterAll(PaperExtras.getInstance());

        Set<Class<?>> subTypes = reflections.get(Scanners.SubTypes.of(PaperExtrasModule.class).asClass());

        subTypes.forEach(clazz -> {
            try {
                PaperExtrasModule module = (PaperExtrasModule) clazz.getDeclaredConstructor().newInstance();
                if (module.shouldEnable()) {
                    module.enable();
                }
            } catch (Exception e) {
                PaperExtras.getInstance().getSLF4JLogger().warn("Failed to load module " + clazz.getSimpleName());
            }
        });

    }

}
