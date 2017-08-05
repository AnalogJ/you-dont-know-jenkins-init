import jenkins.model.*
import hudson.security.*

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Project matrix auth strategy"
println "///////////////////////////////////////////////////////////////////////////"
def instance = Jenkins.getInstance()

//set Project Matrix auth strategy
def strategy = new hudson.security.ProjectMatrixAuthorizationStrategy()
strategy.add(Permission.fromId('hudson.model.Hudson.Administer'),"admin-user")
//strategy.add(Permission.fromId('hudson.model.Hudson.Read'),'anonymous')
//strategy.add(Permission.fromId('hudson.model.Item.Read'),'anonymous')
//strategy.add(Permission.fromId('hudson.model.Item.Build'),'anonymous')


instance.setAuthorizationStrategy(strategy)
instance.save()