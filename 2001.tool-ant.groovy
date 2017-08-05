import jenkins.plugins.git.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Tool installation, ant"
println "///////////////////////////////////////////////////////////////////////////"

def antPluginExtension = Jenkins.instance.getExtensionList(hudson.tasks.Ant.DescriptorImpl.class)[0]
def antInstallations = (antPluginExtension.installations as List)
if(!antInstallations.any { it.getName() == '(Default)' }){
    antInstallations.add(new hudson.tasks.Ant.AntInstallation('(Default)', '', [
            new hudson.tools.InstallSourceProperty([new hudson.tasks.Ant.AntInstaller("1.9.4")])
    ]))
    antPluginExtension.installations = antInstallations
    antPluginExtension.save()
}