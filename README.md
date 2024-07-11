# Fabric-DiscordRPC

<h2>SIMPLE DISCORD RPC CODE FOR FABRIC-MODDING</h2>

### Informations:

##You need to add the DiscordSDK4J modImplementation OTHERWISE, IT **WONT** work!

<h2>FOR GRADLE</h2>

**gradle: dependency**
```gradle
dependencies {
	modImplementation 'com.github.JnCrMx:discord-game-sdk4j:master-SNAPSHOT'
	include 'com.github.JnCrMx:discord-game-sdk4j:master-SNAPSHOT'
}
```

**GRADLE repositories**
```gradle:
repositories {
	mavenCentral()
	maven { url 'https://jitpack.io/' }
}
```

<h2>FOR MAVEN</h2>

**maven dependency**:
```xml
    <dependency>
        <groupId>com.github.JnCrMx</groupId>
        <artifactId>discord-game-sdk4j</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
```
**maven repositories**:
```xml
    <repository>
        <id>sonatype-snapshots</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
```
