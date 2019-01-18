[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/beryx-gist/badass-runtime-example-kotlin-tornadofx/blob/master/LICENSE)
[![Build Status](https://img.shields.io/travis/beryx-gist/badass-runtime-example-kotlin-tornadofx/master.svg?label=Build)](https://travis-ci.org/beryx-gist/badass-runtime-example-kotlin-tornadofx)

## Example of using the badass-runtime plugin with Kotlin and tornadofx ##

This project demonstrates the capabilities of the [Badass Runtime Plugin](https://github.com/beryx/badass-runtime-plugin/)
by creating a custom runtime image of a Kotlin application using [tornadofx](https://github.com/edvin/tornadofx).

### Quick start
From the [releases page](https://github.com/beryx-gist/badass-runtime-example-kotlin-tornadofx/releases) download the archived custom runtime image for your operating system.
Unpack the archive and execute the `hello` script found in the `image/bin` directory.  

### Creating a custom runtime image

Gradle must use Java 11 in order to be able to build the project.
To create the custom runtime image execute:

```
./gradlew runtimeZip
```

This command creates the runtime image in the `build/image` directory and a zip file of it in `build/image-zip`.

The start scripts are found in the `build/image/bin` directory.
