# ktsyntastic

This is a gradle plugin for generating configuration file for
a syntastic and ALE checker (vim) for the [kotlin programming language](https://kotlinlang.org)

A kotlin plugin for vim that has the syntastic checker can be found
[here](https://github.com/francis36012/kotlin-vim). The ALE vim plugin
can be also be found [here](https://github.com/w0rp/ale)

## Syntastic
By default the plugin generates a file named `.syntastic_kotlinc_config` for the
`kotlincSyntastic` task.  These can be overridden in the build file by setting the property,
`project.kotlincSyntastic.prefOutput`, to the preferred filename.

In order for the syntastic checker to make use of the generated file, the 
`g:syntastic_kotlin_kotlinc_config_file_enabled` variable must be set to a
non-zero value.

## Kotlinc
By default the plugin generates a file named `.ale_kotlinc_config` for the
`kotlincAle` task. This can be changed by setting `project.kotlincAle.prefVimOutput`
to your preferred filename.

The `kotlincAle` also generates a module file (used by the kotlin compiler). By
default the name of this file is `module.xml`. This can be changed by setting
`proj.kotlincAle.prefModuleOutput` to the preferred filename.

The output directory for the module can also be changed (`bin` by default) by
setting `project.kotlincAle.prefKotlincOutput`

## Usage
- Apply the [kotlin gradle plugin](https://kotlinlang.org/docs/reference/using-gradle.html) 
- Apply this plugin:
```groovy
plugins {
	id "com.fagyapong.ktsyntastic" version "0.3.1"
}
```

## Acknowledgements
[gradle-syntastic-plugin](https://github.com/Scuilion/gradle-syntastic-plugin)
