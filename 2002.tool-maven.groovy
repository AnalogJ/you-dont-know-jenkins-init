import jenkins.plugins.git.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Tool installation, maven"
println "///////////////////////////////////////////////////////////////////////////"

def mavenPluginExtension = Jenkins.instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0]
def mavenInstallations = (mavenPluginExtension.installations as List)
if(!mavenInstallations.any { it.getName() == 'maven 3.3.9' }){
    mavenInstallations.add(new hudson.tasks.Maven.MavenInstallation('maven 3.3.9', null,
            [new hudson.tools.InstallSourceProperty([new hudson.tasks.Maven.MavenInstaller("3.3.9")])]
    ))
}
if(!mavenInstallations.any { it.getName() == 'maven 2.2.1' }){
    mavenInstallations.add(new hudson.tasks.Maven.MavenInstallation('maven 2.2.1', null,
            [new hudson.tools.InstallSourceProperty([new hudson.tasks.Maven.MavenInstaller("2.2.1")])]
    ))
}
mavenPluginExtension.installations = mavenInstallations
mavenPluginExtension.save()