import jenkins.model.*;
import org.jenkinsci.main.modules.sshd.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Configure executors, system message, SSH daemon, remember me"
println "///////////////////////////////////////////////////////////////////////////"

def instance = Jenkins.instance
instance.setDisableRememberMe(true)
instance.setNumExecutors(10)
instance.setSystemMessage('Jenkins Server - Managed by Chef Cookbook Version v1.0.0 - Converged on Aug 12, 2017')
instance.setRawBuildsDir()
instance.save()

def sshd = SSHD.get()
sshd.setPort(56789)
sshd.save()