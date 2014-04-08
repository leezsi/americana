package ar.edu.unq.americana.events.keyboard;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.components.utils.ComponentUtils;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.constants.MouseButton;
import ar.edu.unq.americana.events.annotations.KeyboarEvents;

public class KeyReleaseEvent extends KeybordEvent {

	public KeyReleaseEvent(final GameComponent<?> component,
			final Method method, final Key key) {
		super(component, method, key);
	}

	@SuppressWarnings("rawtypes")
	public static List<? extends KeybordEvent> getEvents(
			final GameComponent<?> component) {
		final Class<? extends GameComponent> clazz = component.getClass();
		final List<KeybordEvent> result = new ArrayList<KeybordEvent>();
		final Method[] mehtods = ComponentUtils.filterMethodsByAnnotation(
				clazz, KeyboarEvents.Release.class);
		for (final Method method : mehtods) {
			final Key key = method.getAnnotation(KeyboarEvents.Release.class)
					.value();
			result.add(new KeyReleaseEvent(component, method, key));
		}
		return result;
	}

	@Override
	public void apply(final DeltaState state) {
		if (state.isKeyReleased(key)) {
			if (key.equals(MouseButton.ANY)) {
				ComponentUtils.invoke(component, method, state, key);
			} else {
				ComponentUtils.invoke(component, method, state);
			}
		}
	}

}
