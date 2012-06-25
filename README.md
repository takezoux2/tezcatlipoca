# tezcatlipoca

tezcatlipoca is scala object mirroring library like <a href="http://commons.apache.org/beanutils/">Apache Commons BeanUtils.</a><br />

## Functions

* Assign same name properties from A object to B object
* Assign same name properties from Map[String,String] to any object.
* Convert any object to Map[String,String]
* Pick up only one type fields and convert any object to Map[String,A]

## Getting started

### Add dependency

sbt

    resolvers += "takezoux2@github" at "http://takezoux2.github.com/maven"

    dependencies += "com.geishatokyo" %% "tezcatlipoca" % "0.0.1-SNAPSHOT"



maven

    <repository>
      <id>github.takezoux2</id>
      <name>takezoux2@github</name>
      <url>http://takezoux2.github.com/maven</url>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </repository>

    <dependency>
      <groupId>com.geishatokyo</groupId>
      <artifactId>tezcatlipoca_${scala.version}</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>

### Use ReflectionMirror


    import com.geishatokyo.tezcatlipoca.reflect.ReflectionMirror

    class User(var name : String, var age : Int)
    class OtherUser(var name : String)

    val mirror = ReflectionMirror
    val a = new User("Kudryavka",18)

    // assign same property name
    mirror.reflect(new OtherUser("Rin"),a); a.name must_== "Rin"
    // assign same property name from Map
    mirror.reflect(Map("name" -> "Kudryavka",a); a.name must_== "Kudryavka"
    // convert to map
    mirror.reflect(a) must_== Map("name" -> "Kudryavka","age" -> "18")
    // convert to map which include only one type
    mirror.reflectOnly[Int](a) must_== Map("age" -> 18)

ReflectionMirror uses Java Reflection internally.


### Modify object <-> String template

When initializing

    import com.geishatokyo.tezcatlipoca.reflect.ReflectionMirror
    import com.geishatokyo.tezcatlipoca.reflect.template.TemplateRegistry
    import com.geishatokyo.tezcatlipoca.reflect.template.Template
    import java.util.Date

    class DateAsLongTemplate extends Template[Date]{

      def toString( obj : Date) : String = obj.getTime.toString

      def fromString( s : String) : Date = new Date(s.toLong)
    }

     val registry = TemplateRegistry.createDefault()
     registry.register(classOf[Date],new DateAsLongTemplate())

     val mirror = new ReflectionMirror(registry,true)
     // then you can use Date field when you create Map or assign by Map.


### Limimtation

This library targets coping simple scala object.
If you want to treat complex objects, you should use other serialization library.

