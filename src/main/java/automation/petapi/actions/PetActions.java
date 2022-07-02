package automation.petapi.actions;

import automation.context.ContextManager;
import automation.petapi.PetServiceContext;
import automation.petapi.enums.Status;
import io.cucumber.datatable.DataTable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Component
public class PetActions {
    private final ContextManager contextManager;

    public PetActions(ContextManager contextManager) {
        this.contextManager = contextManager;
    }

    public void updatePet(DataTable dataTable) {
        PetServiceContext petServiceContext = contextManager.getRequiredContext(PetServiceContext.class);
        List<Map<String, String>> entry = dataTable.asMaps();
        ofNullable(entry.get(0).get("name")).ifPresent(name -> petServiceContext.getPet().setName(name));
        ofNullable(entry.get(0).get("status")).ifPresent(status -> petServiceContext.getPet().setStatus(Status.valueOf(status)));
        ofNullable(entry.get(0).get("id")).ifPresent(id -> petServiceContext.getPet().setId(Integer.parseInt(id)));

    }
}
