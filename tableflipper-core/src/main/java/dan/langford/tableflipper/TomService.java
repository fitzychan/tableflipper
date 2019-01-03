package dan.langford.tableflipper;

import dan.langford.tableflipper.tom.TableObjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * someday this will need to do more to allow at run time the reading in of a new Table Object Model and merging it with existing entries
 */
public class TomService {

    private final Logger log = LoggerFactory.getLogger(getClass());


    private TableObjectModel model;

    public TomService() {
        String dir = "tables";
        BufferedReader br = new BufferedReader(new InputStreamReader(requireNonNull(getClass().getClassLoader().getResourceAsStream(dir))));
        br.lines()
                .filter(l -> l.toLowerCase().endsWith(".yml") || l.toLowerCase().endsWith(".yaml"))
                .forEach(y->this.load(new InputStreamReader(requireNonNull(getClass().getResourceAsStream("/"+dir+"/"+y)))));
    }

    public void unload(){
        this.model=null;
    }

    public void load(Reader io){
        if(model==null) {
            model = new Yaml(new Constructor(TableObjectModel.class)).load(io);
        } else {
            putAll(new Yaml(new Constructor(TableObjectModel.class)).load(io));
        }
    }

    public void putAll(TableObjectModel altModel) {
        Map<String, TableObjectModel.Table> tempTab = new HashMap<>();
        tempTab.putAll(model.getTables());
        tempTab.putAll(altModel.getTables());
        model.setTables(tempTab);

        Map<String,String> tempDesc = new HashMap<>();
        tempDesc.putAll(model.getDescriptions());
        tempDesc.putAll(altModel.getDescriptions());
        model.setDescriptions(tempDesc);
    }

    public TableObjectModel getModel() {
        if(this.model==null){
            throw new IllegalStateException("model is not loaded");
        }
        return this.model;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TomService)) return false;
        final TomService other = (TomService) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$model = this.getModel();
        final Object other$model = other.getModel();
        if (this$model == null ? other$model != null : !this$model.equals(other$model)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TomService;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $model = this.getModel();
        result = result * PRIME + ($model == null ? 43 : $model.hashCode());
        return result;
    }

    public String toString() {
        return "TomService(model=" + this.getModel() + ")";
    }
}
