import it.fyb.rs.impl.MediaManagement;
import it.fyb.rs.impl.UserManagement;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/")
public class FYBApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(UserManagement.class);
        h.add(MediaManagement.class);
        h.add(MultiPartFeature.class);
        return h;
    }
}