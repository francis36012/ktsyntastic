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

	private Closure resolver

	@InputFiles
	@SkipWhenEmpty
	FileCollection getClasspath() {
		resolver.call()
	}

	String getValue() {
		classpath.files.join File.pathSeparator
	}

	void setResolver(Closure closure) {
		resolver = closure
	}

	@OutputFile
	File output = project.file(project.kotlincSyntastic.prefOutput)

	@TaskAction
	void generate() {
		output.withWriter {
			it.write "let g:syntastic_kotlin_kotlinc_classpath = \"${value}\"\n"
		}
	}
}

