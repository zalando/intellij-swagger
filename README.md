# Swagger Plugin for IntelliJ IDEA
Swagger Plugin makes it easy to edit Swagger specification files inside IntelliJ IDEA. It supports YAML and JSON formats.

Features:
- Field auto completion

## Installation

Swagger Plugin can be found at https://plugins.jetbrains.com/plugin/8347.

## Usage

Swagger Plugin identifies YAML and JSON specifications with the following rules:
- **YAML:** File is named swagger.yaml or swagger.yml or "swagger" field exists in the YAML document
- **JSON:** File is named swagger.json or "swagger" field exists in the JSON root object

## Development

Developing Swagger Plugin is easy, there are only two steps:

1. Change YAML plugin JAR paths to match your environment in *gradle.build*.

2. Execute the following command:

```./gradlew runIdea```

This will start IntelliJ IDEA with the plugin installed.

## How to Contribute
If you want to contribute, please fork and create a pull request.

### Bug Fixes
If you find a bug, it would be awesome if you created an issue about it. Please include a clear description of the problem so that we can fix it!

## Contact:
sebastian.monte@zalando.de

## License
The MIT License (MIT) Copyright © 2016 Zalando SE, https://tech.zalando.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.