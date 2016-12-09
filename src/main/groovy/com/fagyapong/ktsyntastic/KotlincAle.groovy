package com.fagyapong.ktsyntastic

import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

class KotlincAle extends DefaultTask {

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

	String getKotlincModuleXml() {
		def xmlOut = new  StringBuilder()
		def moduleName = project.name
		def outputDir = project.kotlincAle.prefKotlincOutput

		xmlOut.append('<?xml version="1.0" encoding="UTF-8"?>\n')
		xmlOut.append('<modules>\n')
		xmlOut.append(indent(1))
		xmlOut.append("<module name=\"${moduleName}\" type=\"module\" outputDir=\"${outputDir}\">\n")

		def srcFiles = sourceSets.filter({f -> f.exists()}).files
		def classpathFiles = classpath.filter({f -> f.exists()}).files
		
		// source paths
		for (srcP in srcFiles) {
			xmlOut.append(indent(2))
			xmlOut.append("<sources path=\"${srcP}\"/>\n")
		}
		xmlOut.append("\n")


		// classpaths
		for (classP in classpathFiles) {
			xmlOut.append(indent(2))
			xmlOut.append("<classpath path=\"${classP}\"/>\n")
		}
		xmlOut.append(indent(1))
		xmlOut.append('</module>\n')

		xmlOut.append('</modules>\n')

		return xmlOut.toString()
	}

	String getVimConfig() {
		def cpStr = classpath.filter({f -> f.exists()}).files.join(File.pathSeparator)
		def srcStr = sourceSets.filter({f -> f.exists()}).files.join(" ")
		"let g:ale_kotlin_kotlinc_use_module_file = 1\n" +
		"let g:ale_kotlin_kotlinc_module_filename = \"${project.kotlincAle.prefModuleOutput}\"\n\n" +
		"let g:ale_kotlin_kotlinc_classpath = \"$cpStr\"\n\n" +
		"let g:ale_kotlin_kotlinc_sourcepath = \"$srcStr\""
	}

	void setClasspathResolver(Closure closure) {
		classpathResolver = closure
	}

	void setSourceSetResolver(Closure closure) {
		sourceSetResolver = closure
	}

	@OutputFiles
	FileCollection outputs() {
		project.files(project.kotlincAle.prefVimOutput, project.kotlincAle.prefModuleOutput)
	}

	@TaskAction
	void generate() {
		project.file(project.kotlincAle.prefVimOutput).withWriter {
			it.write "${vimConfig}\n"
		}
		project.file(project.kotlincAle.prefModuleOutput).withWriter {
			it.write "${kotlincModuleXml}\n"
		}
	}

	private String indent(int level) {
		if (level < 1) {
			return ""
		}
		def indent = new StringBuilder()
		for (i in 1..level) {
			indent.append("\t")
		}
		return indent.toString()
	}
}


