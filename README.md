[![Build Plugin](https://github.com/zalando/intellij-swagger/actions/workflows/build.yml/badge.svg)](https://github.com/zalando/intellij-swagger/actions/workflows/build.yml)

# Zalando OpenAPI Editor
Zalando OpenAPI Editor makes it easy to edit OpenAPI and Swagger specification files inside IntelliJ IDEA. You can find it on JetBrains' [plugin page](https://plugins.jetbrains.com/plugin/8347).

![Zalando OpenAPI Editor features](https://github.com/zalando/intellij-swagger/blob/master/docs/features.gif?raw=true)

## Usage

Open an OpenAPI or Swagger specification file, that's it.

## Custom Extensions

You can extend the auto completion in order to provide custom keys and and values. The plugin provides the following extension points for this:

```xml
<extensionPoints>
    <extensionPoint qualifiedName="org.zalando.intellij.swagger.customFieldFactory" interface="org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomFieldCompletionFactory"/>
    <extensionPoint qualifiedName="org.zalando.intellij.swagger.customValueFactory" interface="org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomValueCompletionFactory"/>
</extensionPoints>
```

See the [Zalando extension example](https://github.com/zalando/intellij-swagger/blob/master/examples/extensions-zalando) for details.

## Development

Developing the Zalando OpenAPI Editor is easy, just execute the following command:

```./gradlew runIde```

This will start IntelliJ IDEA with the plugin installed.

## How to Contribute
To contribute, please fork and create a pull request.

### Bug Fixes
If you find a bug, it would be awesome if you created an issue about it. Please include a clear description of the problem so that we can fix it!

## License
The MIT License (MIT) Copyright © 2017 Zalando SE, https://tech.zalando.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
