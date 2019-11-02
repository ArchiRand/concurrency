package xml;

import com.google.common.io.Resources;
import j2html.tags.ContainerTag;
import one.util.streamex.StreamEx;
import xml.schema.ObjectFactory;
import xml.schema.Payload;
import xml.schema.Project;
import xml.schema.User;
import xml.utils.jaxb.JaxbParser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class MainXML {

    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    public List<User> getUsersByProject(String projectName) throws Exception {
        Payload payload = JAXB_PARSER.unmarshal(Resources.getResource("payload.xml").openStream());
        List<Project.Group> groups = getGroups(payload, projectName);
        return StreamEx.of(payload.getUsers().getUser())
                .filter(user -> !Collections.disjoint(user.getGroup(), groups))
                .sorted(Comparator.comparing(User::getValue).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    public String toHtml(List<User> users, String projectName) {
        final ContainerTag table = table().with(
                tr().with(th("FullName"), th("email")))
                .attr("border", "1")
                .attr("cellpadding", "8")
                .attr("cellspacing", "0");

        users.forEach(u -> table.with(tr().with(td(u.getValue()), td(u.getEmail()))));

        return html().with(
                head().with(title(projectName + " users")),
                body().with(h1(projectName + " users"), table)
        ).render();
    }

    private List<Project.Group> getGroups(Payload payload, String projectName) throws Exception {
        return StreamEx.of(payload.getProjects().getProject())
                .filter(project -> project.getName().equals(projectName))
                .flatCollection(Project::getGroup)
                .toList();
    }

}
