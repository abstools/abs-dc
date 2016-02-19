# ABS Deployment Component

This module set provide a minimalistic approach to support different type of 
Deployment Components backed by different technologies. The current supported
types are:

* AWS OpsWorks, `abs-dc-aws`, that supports launching and terminating instances through AWS OpsWorks.
* docker, `abs-dc-docker`, that supports launching and terminating resources through docker containers.

To use this module:

* Prepare the technology to be used. This might involve preparing templates in AWS OpsWorks or Docker files.
* Include the necessary module in your code; for example:
```xml
<dependency>
    <groupId>org.abs-api</groupId>
    <artifactId>abs-dc-docker</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```  
* Use the API to launch/terminate Deployment Components:
```java
Map<String, String> context = new HashMap<>();
context.put("docker_image", "abs/abs-java");
Resources resources = new Resources();
DeploymentComponent dc1 = resources.launch(context);
```


