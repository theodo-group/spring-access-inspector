# Spring Access Inspector

This tool generates a table report to verify access control on your Spring Boot routes. It scans for the [`@PreAuthorize`, `@Secured`, or `@RolesAllowed`](https://www.baeldung.com/spring-security-method-security) annotations from `spring-security-config` to create a `table.html` file with an easy-to-read list of all your routes and their access control annotations.

![List of your routes with preauthorize annotation](preauthorize-table.png)

## Quickstart

A plugin has been uploaded to [Maven Central](https://central.sonatype.com/). You can quickly use it by adding this plugin to the `<plugins>` section of your project's `pom.xml`.

<details>
<summary>Java 17</summary>

```xml
<build>
  <pluginManagement>
    <plugins>
      <!-- ...existing plugins... -->
      <plugin>
        <groupId>com.theodo</groupId>
        <artifactId>spring-access-inspector-plugin</artifactId>
        <version>1.0.3</version>
        <configuration>
          <projectBaseDir>${project.basedir}</projectBaseDir>
          <htmlOutputFile>./table.html</htmlOutputFile>
        </configuration>
      </plugin>
      <!-- ...existing plugins... -->
    </plugins>
  </pluginManagement>
</build>
```

</details>

<details>
<summary>Java 21</summary>

```xml
<build>
  <pluginManagement>
    <plugins>
      <!-- ...existing plugins... -->
      <plugin>
        <groupId>com.theodo</groupId>
        <artifactId>spring-access-inspector-plugin</artifactId>
        <version>2.0.3</version>
        <configuration>
          <projectBaseDir>${project.basedir}</projectBaseDir>
          <htmlOutputFile>./table.html</htmlOutputFile>
        </configuration>
      </plugin>
      <!-- ...existing plugins... -->
    </plugins>
  </pluginManagement>
</build>
```

</details>

## The Project

This project is composed of two parts:

1. **The Inspector**: The core tool that performs the analysis.
2. **The Maven Plugin**: A wrapper plugin that simplifies using the inspector in any project.

### Inspector

The inspector uses Java 21. A Java 17 version is available on the branch [`v1-java-17`](https://github.com/theodo-group/spring-access-inspector/tree/v1-java-17).

To use the inspector locally without the plugin, follow these steps:

1. Clone the repository:

   ```bash
   git clone git@github.com:theodo-group/spring-access-inspector.git
   ```

2. Navigate to the inspector folder:

   ```bash
   cd spring-access-inspector/inspector
   ```

3. Compile the code:

   ```bash
   mvn compile exec:java -Dexec.mainClass=com.theodo.inspector.SpringAccessInspector
   ```

4. Run the code (using the Maven exec plugin) and provide the path to the `pom.xml` files you want to analyze:

   ```bash
   mvn exec:java -Dexec.mainClass=com.theodo.inspector.SpringAccessInspector -Dexec.args="/path/to/poms"
   ```

   **Note**: You may need to compile your code beforehand:

   ```bash
   mvn clean install -DskipTests
   ```

### Maven Plugin

The Maven plugin simplifies launching the inspector by adding it to the `pom.xml` of the project you want to inspect. It is available on [Maven Central](https://central.sonatype.com/), but you can also use it locally.

1. Navigate to the plugin folder:

   ```bash
   cd spring-access-inspector/inspector-maven-plugin
   ```

2. Compile the plugin:

   ```bash
   mvn clean install
   ```

3. Add the plugin to the `build/pluginManagement` section of your project's `pom.xml`:

   ```xml
   <build>
     <!-- ...existing build configuration... -->
     <pluginManagement>
       <plugins>
         <plugin>
           <groupId>com.theodo</groupId>
           <artifactId>spring-access-inspector-plugin</artifactId>
           <version>2.0.3</version>
           <configuration>
             <projectBaseDir>${project.basedir}</projectBaseDir>
             <htmlOutputFile>./table.html</htmlOutputFile>
           </configuration>
         </plugin>
       </plugins>
     </pluginManagement>
   </build>
   ```

4. Run the analysis in your shell or CI:

   ```bash
   mvn inspector:inspect
   ```

   **Note**: You may need to compile the inspector code beforehand (see above).

## How to Contribute

### Upgrade the Version

When upgrading the version, update the following:

- The version in the three `pom.xml` files (inspector, plugin, and aggregate).
- This README file.

### Deployment

To deploy the project:

1. Add the username and password for the "public" server to your root `.m2/settings.xml`:

   ```xml
   <server>
     <id>public</id>
     <username>thesonatypetokenusername</username>
     <password>thesonatypetokenpassword</password>
   </server>
   ```

2. Add your GPG key passphrase to your root `.m2/settings.xml`:

   ```xml
   <server>
     <id>gpg</id>
     <passphrase>yourgpgkeypassphrase</passphrase>
   </server>
   ```

3. Run the following command to deploy only the plugin and the inspector:

   ```bash
   mvn clean deploy --projects inspector,inspector-maven-plugin
   ```
