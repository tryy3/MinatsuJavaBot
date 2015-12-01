package us.tryy3.java.minatsuskype.plugins;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class PluginDescription {
    private String name = null;
    private String version = null;
    private String description = null;
    private String authors = null;
    private String website = null;
    private String prefix = null;

    public PluginDescription(String name, String version) {
        this.setName(name);
        this.setVersion(version);
    }
    public PluginDescription(String name, String version, String description) {
        this.setName(name);
        this.setVersion(version);
        this.setDescription(description);
    }
    public PluginDescription(String name, String version, String description, String authors){
        this.setName(name);
        this.setVersion(version);
        this.setDescription(description);
        this.setAuthors(authors);
    }
    public PluginDescription(String name, String version, String description, String authors, String website) {
        this.setName(name);
        this.setVersion(version);
        this.setDescription(description);
        this.setAuthors(authors);
        this.setWebsite(website);
    }
    public PluginDescription(String name, String version, String description, String authors, String website, String prefix) {
        this.setName(name);
        this.setVersion(version);
        this.setDescription(description);
        this.setAuthors(authors);
        this.setWebsite(website);
        this.setPrefix(prefix);
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getWebsite() {
        return website;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String toString() {
        String s = "";
        s += (this.name != null) ? this.name + " - " : "";
        s += (this.version != null) ? this.version + " - " : "";
        s += (this.description != null) ? this.description + " - " : "";
        s += (this.authors != null) ? this.authors + " - " : "";
        s += (this.website != null) ? this.website + " - " : "";
        s = s.substring(0, s.length() - 3);
        return s;
    }
}
