import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;

import hudson.triggers.TimerTrigger
import hudson.model.*
import jenkins.model.*
import hudson.slaves.*
import javaposse.jobdsl.plugin.*
import hudson.plugins.git.*
import java.util.Collections
import java.util.List
import hudson.plugins.git.extensions.GitSCMExtension
import hudson.plugins.git.extensions.impl.*;

def bootstrap_job_name = 'Bootstrap-DSL'
def bootstrap_job_git_remote = "git@github.com:owner/repo.git"
def ssh_key_credential_id = "123-456"


// jenkins seems to happily overwrite existing jobs, no need to delete an existing one
def job = new FreeStyleProject(Jenkins.instance, bootstrap_job_name)
job.setDescription('Bootstraps the Jenkins Server (installs all jobs using the jobs-dsl plugin)')

//set build steps (shell copy)
def shellStep = new hudson.tasks.Shell('cp -f $HUDSON_HOME/chef-config.json $WORKSPACE/resources/chef-config.json')
job.buildersList.add(shellStep)

//set build trigger cron to run daily
job.addTrigger(new TimerTrigger("H H * * *"))

//set post build steps
def publishersList = job.getPublishersList()
publishersList.add(new hudson.tasks.BuildTrigger("Downstream-Job-Name", false))

//set SCM
List<BranchSpec> branches = Collections.singletonList(new BranchSpec("*/master"));
List<SubmoduleConfig> submoduleCnf = Collections.<SubmoduleConfig>emptyList();
// We are using predefined user id jenkins. You change it in the global config
List<UserRemoteConfig> usersconfig = Collections.singletonList(
        new UserRemoteConfig(bootstrap_job_git_remote, '', '', ssh_key_credential_id)
)
List<GitSCMExtension> gitScmExt = new ArrayList<GitSCMExtension>();
def scm = new GitSCM(usersconfig, branches, false, submoduleCnf, null, null, gitScmExt)
job.setScm(scm)

builder = new javaposse.jobdsl.plugin.ExecuteDslScripts(null)
builder.setUseScriptText(false)
builder.setTargets("""
jobs/default_jobs_dsl.groovy
jobs/default_jobs_utilities_dsl.groovy
jobs/default_views_dsl.groovy
""")
builder.setRemovedJobAction(javaposse.jobdsl.plugin.RemovedJobAction.DELETE)
builder.setRemovedViewAction(javaposse.jobdsl.plugin.RemovedViewAction.DELETE)
builder.setAdditionalClasspath('src/main/groovy/')


job.buildersList.add(builder)
job.save()

Jenkins.instance.restart()