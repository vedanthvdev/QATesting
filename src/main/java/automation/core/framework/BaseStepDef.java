package automation.core.framework;

import automation.context.ContextManager;

public class BaseStepDef {

    private final ContextManager contextManager;

    public BaseStepDef(ContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public ContextManager getContextManager() {
        return contextManager;
    }
}
