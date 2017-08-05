import jenkins.model.Jenkins;
import jenkins.model.*;
import org.jenkinsci.plugins.perforce.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Tool installation, perforce"
println "///////////////////////////////////////////////////////////////////////////"

def perforcePluginExtension = Jenkins.instance.getExtensionList(hudson.plugins.perforce.PerforceToolInstallation.DescriptorImpl.class)[0]
perforcePluginExtension.setInstallations(
        new hudson.plugins.perforce.PerforceToolInstallation('/usr/local/bin/p4', '/usr/local/bin/p4', []),
        new hudson.plugins.perforce.PerforceToolInstallation('p4-windows', 'p4', [])
)
perforcePluginExtension.save()