package com.fagyapong.ktsyntastic

import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

class KotlincSynstastic extends DefaultTask {

	private Closure classpathResolver
	private Closure sourceSetResolver

	@InputFiles
	@SkipWhenEmpty
	FileCollection getClasspath() {
		classpathResolver.call()
	}

	@InputFiles
	FileCollection getSourceSets() {
		sourceSetResolver.call()
	}

	String getValue() {
		def cpStr = classpath.filter({f -> f.exists()}).files.join(File.pathSeparator)
		def srcStr = sourceSets.filter({f -> f.exists()}).files.join(" ")
		"let g:syntastic_kotlin_kotlinc_classpath = \"$cpStr\"\n" +
		"let g:syntastic_kotlin_kotlinc_sourcepath = \"$srcStr\""
	}

	void setClasspathResolver(Closure closure) {
		classpathResolver = closure
	}

	void setSourceSetResolver(Closure closure) {
		sourceSetResolver = closure
	}

	@OutputFile
	File output = project.file(project.kotlincSyntastic.prefOutput)

	@TaskAction
	void generate() {
		output.withWriter {
			it.write "${value}\n"
		}
	}
}

