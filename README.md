# ktsyntastic

This is a gradle plugin for generating configuration file for
a syntastic checker (vim) for the [kotlin programming language](https://kotlinlang.org)

A kotlin plugin for vim that has the syntastic checker can be found
[here](https://github.com/francis36012/kotlin-vim)

By default the plugin generates a file named `.syntastic_kotlinc_config`.
This can be overridden in the build file by setting the property,
`project.kotlincSyntastic.prefOutput`, to the preferred filename.

### Usage
- Apply the [kotlin gradle plugin](https://kotlinlang.org/docs/reference/using-gradle.html) 
- Apply this plugin:
	```groovy
	plugins {
		id "ktsyntastic" version "0.1.0"
	}
	```
In order for the syntastic checker to make use of the generated file, the 
`g:syntastic_kotlin_kotlinc_config_file_enabled` variable must be set to a
non-zero value.

The configuration file is generated by running `gradle kotlincSyntastic`

## Acknowledgements
[gradle-syntastic-plugin](https://github.com/Scuilion/gradle-syntastic-plugin)