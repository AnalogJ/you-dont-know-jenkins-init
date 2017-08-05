import jenkins.plugins.git.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Tool installation, git"
println "///////////////////////////////////////////////////////////////////////////"

def gitPluginExtension = Jenkins.instance.getExtensionList(hudson.plugins.git.GitTool.DescriptorImpl.class)[0]
def gitInstallations = (gitPluginExtension.installations as List)
if(!gitInstallations.any { it.getName() == 'Default' }){
    gitInstallations.add(new hudson.plugins.git.GitTool('Default', 'git',[]))
    gitPluginExtension.installations = gitInstallations
    gitPluginExtension.save()
}
