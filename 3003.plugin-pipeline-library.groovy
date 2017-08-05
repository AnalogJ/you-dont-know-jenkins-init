import jenkins.model.Jenkins;
import jenkins.model.*;
import hudson.tasks.*;
import jenkins.model.*;
import org.jenkinsci.plugins.workflow.libs.*;
import jenkins.plugins.git.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Global Pipeline Library plugin"
println "///////////////////////////////////////////////////////////////////////////"

def pipeline_library_remote = "git@github.com:owner/repo.git"
def pipeline_library_name = "repo"
def ssh_key_credential_id = "123-456"

def globalPipelineLibraries = GlobalLibraries.get()
//setup Git SCM
def scmSharedLibrary = new SCMSourceRetriever(new GitSCMSource(
        null, //SCM id, automatically created.
        pipeline_library_remote, //remote
        ssh_key_credential_id, //credentialsId
        '*', //includes
        '', //excludes
        true //ignoreOnPushNotifications
))

def esLibraryConfiguration = new LibraryConfiguration(pipeline_library_name, scmSharedLibrary)
esLibraryConfiguration.setDefaultVersion('master')
esLibraryConfiguration.setImplicit(true)
globalPipelineLibraries.setLibraries([esLibraryConfiguration])
globalPipelineLibraries.save()