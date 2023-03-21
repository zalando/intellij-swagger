# Swagger Plugin Zalando Extension
Zalando's architecture centers around microservices, and each API is documented with a Swagger specification. Zalando has its own [API Guidelines](https://opensource.zalando.com/restful-api-guidelines) that extends the standard Swagger specification. This extension example illustrates how the Swagger Plugin can be extended to support Zalando specific guidelines for REST APIs.

![Zalando Extension (x-audience)](https://github.com/zalando/intellij-swagger/blob/master/docs/extensions-zalando.png?raw=true)

## Development

### Running the IDE
To start IntelliJ IDEA with the plugin installed:
```sh
./gradlew runIde
```

### Plugin verification
To verify the plugin against different IDEA versions run:
```sh
./gradlew runPluginVerifier
```
This will output information of compatibility and also create reports under `./build/reports/pluginVerifier/`.

Generic verification:
```sh
./gradlew check
```

### Releasing
See `./release.sh`

###
Check dependency updates (preferably replaced with Dependabot):

```sh
./gradlew dependencyUpdates
```

## License
The MIT License (MIT) Copyright © 2018 Zalando SE, https://tech.zalando.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
